package org.springblade.core.log.sharding;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * Application startup initialize the sharding tables
 * @author gao shiyong
 * @since 2024-11-28
 */
@Order(value = 1)
@Component
public class ShardingTablesLoadRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ShardingAlgorithmUtil.tableNameCacheReloadAll();
    }
}
