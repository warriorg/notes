package org.springblade.core.log.sharding;

import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * monthly sharding algorithm
 * @author gao shiyong
 * @since 2024-11-28
 */
public class TimeShardingAlgorithm implements StandardShardingAlgorithm<Date> {
    /**
     * logger
     */
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * 日期格式化
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");

    /**
     * 完整时间格式
     */
    private static final SimpleDateFormat DATE_TIME_FORMATTER_SIMPLE = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Date类型的分片时间格式
     */
    private static final SimpleDateFormat TABLE_SHARD_DATE_FORMATTER = new SimpleDateFormat("yyyyMM");


    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        String logicTableName = preciseShardingValue.getLogicTableName();
        if (logger.isInfoEnabled()) {
            logger.info("logicTableName: {}", logicTableName);
        }
        String actualTableName = ShardingAlgorithmUtil.generateTableName(logicTableName);
        return getShardingTableAndCreate(logicTableName, actualTableName, collection);
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
        String logicTableName = rangeShardingValue.getLogicTableName();
        if (logger.isInfoEnabled()) {
            logger.info("范围分片，节点配置表名: {}", logicTableName);
        }

        // between and 的起始值
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        boolean hasLowerBound = valueRange.hasLowerBound();
        boolean hasUpperBound = valueRange.hasUpperBound();

        // 获取最大值和最小值
        String min = hasLowerBound ? String.valueOf(valueRange.lowerEndpoint()) : getLowerEndpoint(collection);
        String max = hasUpperBound ? String.valueOf(valueRange.upperEndpoint()) : getUpperEndpoint(collection);

        // 循环计算分表范围
        Set<String> actualTableNames = new LinkedHashSet<>();
        try {
            Date minDate = DATE_TIME_FORMATTER_SIMPLE.parse(min);
            Date maxDate = DATE_TIME_FORMATTER_SIMPLE.parse(max);
            Calendar calendar = Calendar.getInstance();
            while (minDate.before(maxDate) || minDate.equals(maxDate)) {
                String tableName = logicTableName
                        + ShardingAlgorithmUtil.TABLE_SPLIT_SYMBOL
                        + TABLE_SHARD_DATE_FORMATTER.format(minDate);
                actualTableNames.add(tableName);
                calendar.setTime(minDate);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                minDate = calendar.getTime();
            }
        } catch (ParseException e) {
            logger.error("时间格式化失败，请稍后重试，原因：{}", e.getMessage(), e);
            throw new IllegalArgumentException("时间格式化失败，请稍后重试");
        }

        return getShardingTablesAndCreate(logicTableName, actualTableNames, collection);
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void init(Properties properties) {

    }

    /**
     * 获取 最小分片值
     * @param tableNames 表名集合
     * @return 最小分片值
     */
    private String getLowerEndpoint(Collection<String> tableNames) {
        Optional<LocalDateTime> optional = tableNames.stream()
                .map(o -> LocalDateTime.parse(o.replace(ShardingAlgorithmUtil.TABLE_SPLIT_SYMBOL, "") + "01 00:00:00", DATE_TIME_FORMATTER))
                .min(Comparator.comparing(Function.identity()));
        if (optional.isPresent()) {
            ZonedDateTime zonedDateTime = optional.get().atZone(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            return String.valueOf(Date.from(instant));
        } else {
            logger.error("获取数据最小分表失败，请稍后重试，tableName：{}", tableNames);
            throw new IllegalArgumentException("获取数据最小分表失败，请稍后重试");
        }
    }

    /**
     * 获取 最大分片值
     * @param tableNames 表名集合
     * @return 最大分片值
     */
    private String getUpperEndpoint(Collection<String> tableNames) {
        Optional<LocalDateTime> optional = tableNames.stream()
                .map(o -> LocalDateTime.parse(o.replace(ShardingAlgorithmUtil.TABLE_SPLIT_SYMBOL, "") + "01 00:00:00", DATE_TIME_FORMATTER))
                .max(Comparator.comparing(Function.identity()));
        if (optional.isPresent()) {
            ZonedDateTime zonedDateTime = optional.get().atZone(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            return String.valueOf(Date.from(instant));
        } else {
            logger.error("获取数据最大分表失败，请稍后重试，tableName：{}", tableNames);
            throw new IllegalArgumentException("获取数据最大分表失败，请稍后重试");
        }
    }

    /**
     * 检查分表获取的表名是否存在，不存在则自动建表
     *
     * @param logicTableName        逻辑表
     * @param actualTableNames     真实表名
     * @param collection 可用的数据库表名
     * @return 存在于数据库中的真实表名集合
     */
    public Set<String> getShardingTablesAndCreate(String logicTableName, Collection<String> actualTableNames, Collection<String> collection) {
        return actualTableNames.stream().map(o -> getShardingTableAndCreate(logicTableName, o, collection)).collect(Collectors.toSet());
    }

    /**
     * 检查分表获取的表名是否存在，不存在则自动建表
     * @param logicTableName   逻辑表
     * @param actualTableName 真实表名
     * @return 确认存在于数据库中的真实表名
     */
    private String getShardingTableAndCreate(String logicTableName, String actualTableName, Collection<String> collection) {
        // 缓存中有此表则返回，没有则判断创建
        if (ShardingAlgorithmUtil.TABLE_NAME_CACHE.get(logicTableName).contains(actualTableName)) {
            return actualTableName;
        } else {
            // 检查分表获取的表名不存在，需要自动建表
            boolean isSuccess = ShardingAlgorithmUtil.createShardingTable(logicTableName, actualTableName);
            if (isSuccess) {
                collection.add(actualTableName);
                return actualTableName;
            } else {
                return logicTableName;
            }
        }
    }
}
