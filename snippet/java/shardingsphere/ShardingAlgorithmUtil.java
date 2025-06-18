package org.springblade.core.log.sharding;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import cn.hutool.extra.spring.SpringUtil;
import io.vertx.core.impl.ConcurrentHashSet;
import javax.sql.DataSource;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
import org.apache.shardingsphere.infra.config.RuleConfiguration;
import org.apache.shardingsphere.infra.metadata.ShardingSphereMetaData;
import org.apache.shardingsphere.mode.manager.ContextManager;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

/**
 * sharding algorithm tool
 * @author gao shiyong
 * @since 2024-11-28
 */
public class ShardingAlgorithmUtil {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * 分片时间格式
     */
    public static final DateTimeFormatter TABLE_SHARD_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

    /**
     * database table split symbol
     */
    public static final String TABLE_SPLIT_SYMBOL = "_";

    /**
     * table name cache
     */
    public static final ConcurrentHashMap<String, Set<String>> TABLE_NAME_CACHE = new ConcurrentHashMap<>(4);

    /**
     * database connection properties
     */
    private static final Environment ENV = SpringUtil.getApplicationContext().getEnvironment();
    private static final String DATASOURCE_URL = ENV.getProperty("spring.shardingsphere.datasource.ds0.url");
    private static final String DATASOURCE_USERNAME = ENV.getProperty("spring.shardingsphere.datasource.ds0.username");
    private static final String DATASOURCE_PASSWORD = ENV.getProperty("spring.shardingsphere.datasource.ds0.password");

    private ShardingAlgorithmUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * reload all actual table
     */
    public static void tableNameCacheReloadAll() {
        Arrays.asList("blade_log_api", "blade_log_error", "blade_log_sap", "blade_log_usual")
                .forEach(logicTableName -> {
                    Set<String> actualTableNames = getAllTableNameBySchema(logicTableName);
                    if (ObjectUtils.isEmpty(actualTableNames)) {
                        TABLE_NAME_CACHE.put(logicTableName, new ConcurrentHashSet<>());
                        TABLE_NAME_CACHE.get(logicTableName).add(logicTableName);
                    } else {
                        TABLE_NAME_CACHE.put(logicTableName, actualTableNames);
                    }
                    actualDataNodesRefresh(logicTableName, TABLE_NAME_CACHE.get(logicTableName));
                });
    }

    /**
     * 根据分片字段值生成目标表名
     *
     * @param logicTableName 逻辑表名
     * @return 目标物理表名
     */
    public static String generateTableName(String logicTableName) {
        return  logicTableName + TABLE_SPLIT_SYMBOL + YearMonth.now().format(TABLE_SHARD_TIME_FORMATTER);

    }

    /**
     * 获取所有表名
     * @return 表名集合
     * @param logicTableName 逻辑表
     */
    public static Set<String> getAllTableNameBySchema(String logicTableName) {
        Set<String> tableNames = new ConcurrentHashSet<>();
        if (!validateConnectionProperties()) {
            return tableNames;
        }
        try (Connection conn = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
                Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery("show TABLES like '" + logicTableName + TABLE_SPLIT_SYMBOL + "%'")) {
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    if (tableName != null && tableName.matches(String.format("^(%s\\d{6})$", logicTableName + TABLE_SPLIT_SYMBOL))) {
                        tableNames.add(rs.getString(1));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("数据库连接失败，请稍后重试，原因：{}", e.getMessage(), e);
            throw new IllegalArgumentException("数据库连接失败，请稍后重试");
        }
        return tableNames;
    }

    /**
     * 创建分表2
     * @param logicTableName  逻辑表
     * @param actualTableName 真实表名，例：t_user_202201
     * @return 创建结果（true创建成功，false未创建）
     */
    public static boolean createShardingTable(String logicTableName, String actualTableName) {
        // 根据日期判断，当前月份之后分表不提前创建
        String month = actualTableName.replace(logicTableName + TABLE_SPLIT_SYMBOL, "");
        YearMonth shardingMonth = YearMonth.parse(month, TABLE_SHARD_TIME_FORMATTER);
        if (shardingMonth.isAfter(YearMonth.now())) {
            return false;
        }
        executeSql(Collections.singletonList("CREATE TABLE IF NOT EXISTS " + actualTableName + " LIKE " + logicTableName + ";"));
        TABLE_NAME_CACHE.get(logicTableName).add(actualTableName);
        actualDataNodesRefresh(logicTableName, TABLE_NAME_CACHE.get(logicTableName));
        return true;
    }




    /**
     * 动态更新配置 actualDataNodes
     *
     * @param logicTableName  逻辑表名
     */
    public static void actualDataNodesRefresh(String logicTableName, Set<String> actualTableNames)  {
        try {
            if (CollectionUtils.isEmpty(actualTableNames)) {
                return;
            }
            // 获取数据分片节点
            String dbName = "ds0";
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("更新分表配置，logicTableName:{}，tableNamesCache:{}", logicTableName, actualTableNames);
            }

            // generate actualDataNodes
            String newActualDataNodes = actualTableNames.stream().map(o -> String.format("%s.%s", dbName, o)).collect(
                    Collectors.joining(","));
            ShardingSphereDataSource shardingSphereDataSource = (ShardingSphereDataSource) ((Advised) SpringUtil.getBean(DataSource.class)).getTargetSource().getTarget();
            updateShardRuleActualDataNodes(shardingSphereDataSource, logicTableName, newActualDataNodes);
        }catch (Exception e){
            LOGGER.error("初始化 动态表单失败，原因：{}", e.getMessage(), e);
        }
    }


    /**
     * 执行SQL
     * @param sqlList SQL集合
     */
    private static void executeSql(List<String> sqlList) {
        if (!validateConnectionProperties()) {
            return;
        }
        try (Connection conn = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
                Statement st = conn.createStatement()) {
                conn.setAutoCommit(true);
                for (String sql : sqlList) {
                    st.addBatch(sql);
                }
                st.executeBatch();
        } catch (Exception e) {
            LOGGER.error(">>>>>>>>>> 【ERROR】数据表创建执行失败，请稍后重试，原因：{}", e.getMessage(), e);
            throw new IllegalArgumentException("数据表创建执行失败，请稍后重试");
        }
    }

    private static boolean validateConnectionProperties() {
        if (ObjectUtils.isEmpty(DATASOURCE_URL) || ObjectUtils.isEmpty(DATASOURCE_USERNAME) || ObjectUtils.isEmpty(DATASOURCE_PASSWORD)) {
            LOGGER.error(">>>>>>>>>> 【ERROR】数据库连接配置有误，请稍后重试，URL:{}, username:{}, password:{}", DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
            return false;
        }
        return true;
    }

    /**
     * 刷新ActualDataNodes
     */
    private static void updateShardRuleActualDataNodes(ShardingSphereDataSource dataSource, String logicTableName, String newActualDataNodes) {
        // Context manager.
        Field contextManagerField = ReflectionUtils.findField(dataSource.getClass(), "contextManager");
        if (ObjectUtils.isEmpty(contextManagerField)) {
            LOGGER.error("获取contextManager Field失败，请稍后重试");
            return;
        }
        ReflectionUtils.makeAccessible(contextManagerField);
        ContextManager contextManager = (ContextManager) ReflectionUtils.getField(contextManagerField, dataSource);
        if (ObjectUtils.isEmpty(contextManager)) {
            LOGGER.error("获取contextManager失败，请稍后重试");
            return;
        }

        // Rule configuration.
        ShardingSphereMetaData shardingSphereMetaData = contextManager
                .getMetaDataContexts()
                .getMetaData();
        Collection<RuleConfiguration> newRuleConfigList = new LinkedList<>();

        // update context
        String schemaName = "logic_db";
        Collection<RuleConfiguration> oldRuleConfigList = shardingSphereMetaData
                .getDatabases()
                .get(schemaName)
                .getRuleMetaData()
                .getConfigurations();

        for (RuleConfiguration oldRuleConfig : oldRuleConfigList) {
            if (oldRuleConfig instanceof ShardingRuleConfiguration) {
                // Algorithm provided sharding rule configuration
                ShardingRuleConfiguration oldAlgorithmConfig = (ShardingRuleConfiguration) oldRuleConfig;
                // Sharding table rule configuration Collection
                Collection<ShardingTableRuleConfiguration> oldTableRuleConfigList = oldAlgorithmConfig.getTables();
                Collection<ShardingTableRuleConfiguration> newTableRuleConfigList =oldTableRuleConfigList.stream().map(oldTableRuleConfig -> {
                    if (logicTableName.equals(oldTableRuleConfig.getLogicTable())) {
                        ShardingTableRuleConfiguration newTableRuleConfig = new ShardingTableRuleConfiguration(oldTableRuleConfig.getLogicTable(), newActualDataNodes);
                        newTableRuleConfig.setTableShardingStrategy(oldTableRuleConfig.getTableShardingStrategy());
                        newTableRuleConfig.setDatabaseShardingStrategy(oldTableRuleConfig.getDatabaseShardingStrategy());
                        newTableRuleConfig.setKeyGenerateStrategy(oldTableRuleConfig.getKeyGenerateStrategy());
                        return newTableRuleConfig;
                    } else {
                        return oldTableRuleConfig;
                    }
                }).collect(Collectors.toList());

                ShardingRuleConfiguration newAlgorithmConfig = new ShardingRuleConfiguration();
                newAlgorithmConfig.setTables(newTableRuleConfigList);
                newAlgorithmConfig.setAutoTables(oldAlgorithmConfig.getAutoTables());
                newAlgorithmConfig.setBindingTableGroups(oldAlgorithmConfig.getBindingTableGroups());
                newAlgorithmConfig.setBroadcastTables(oldAlgorithmConfig.getBroadcastTables());
                newAlgorithmConfig.setDefaultDatabaseShardingStrategy(oldAlgorithmConfig.getDefaultDatabaseShardingStrategy());
                newAlgorithmConfig.setDefaultTableShardingStrategy(oldAlgorithmConfig.getDefaultTableShardingStrategy());
                newAlgorithmConfig.setDefaultKeyGenerateStrategy(oldAlgorithmConfig.getDefaultKeyGenerateStrategy());
                newAlgorithmConfig.setDefaultShardingColumn(oldAlgorithmConfig.getDefaultShardingColumn());
                newAlgorithmConfig.setShardingAlgorithms(oldAlgorithmConfig.getShardingAlgorithms());
                newAlgorithmConfig.setKeyGenerators(oldAlgorithmConfig.getKeyGenerators());

                newRuleConfigList.add(newAlgorithmConfig);
            }
        }

        contextManager.alterRuleConfiguration(schemaName, newRuleConfigList);
    }

}
