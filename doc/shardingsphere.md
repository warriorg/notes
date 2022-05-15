# ShardingSphere-Proxy 

## Install

1. 通过[下载页面](https://shardingsphere.apache.org/document/current/cn/downloads/)获取 ShardingSphere-Proxy 安装包。
2. 解压缩后修改 `conf/server.yaml` 和以 `config-` 前缀开头的文件，如：`conf/config-xxx.yaml` 文件，进行分片规则、读写分离规则配置。配置方式请参考[配置手册](https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-proxy/yaml-config/)。
3. Linux 操作系统请运行 `bin/start.sh`，Windows 操作系统请运行 `bin/start.bat` 启动 ShardingSphere-Proxy。





使用配置文件

server.yaml

```yaml
mode:
  type: memory
rules:
  - !AUTHORITY
    users:
      - root@%:root
    #      - sharding@:sharding
    provider:
      type: ALL_PRIVILEGES_PERMITTED
  - !SQL_PARSER
    sqlCommentParseEnabled: true
    sqlStatementCache:
      initialCapacity: 2000
      maximumSize: 65535
      concurrencyLevel: 4
    parseTreeCache:
      initialCapacity: 128
      maximumSize: 1024
      concurrencyLevel: 4

props:
  max-connections-size-per-query: 1
  kernel-executor-size: 16  # Infinite by default.
  proxy-frontend-flush-threshold: 128  # The default value is 128.
  proxy-hint-enabled: false
  sql-show: true
```



config-sharding.yaml

```yaml
schemaName: sharding_db
dataSources:
  ds3306:
    url: jdbc:mysql://127.0.0.1:3306/db3306?serverTimezone=UTC&useSSL=false
    username: root
    password: 12345678
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50
    minPoolSize: 1
  ds3307:
    url: jdbc:mysql://127.0.0.1:3306/db3307?serverTimezone=UTC&useSSL=false
    username: root
    password: 12345678
    connectionTimeoutMilliseconds: 30000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 50
    minPoolSize: 1


rules:
- !SHARDING
  defaultDatabaseStrategy:
    standard:
      shardingColumn: site
      shardingAlgorithmName: databaseInline
  defaultTableStrategy:
    none:
  
  shardingAlgorithms:
    databaseInline:
      type: INLINE
      props:
        algorithm-expression: ds${site}
  tables:
    NEMS_TR_HEAD:
      logicTable: NEMS_TR_HEAD
    NEMS_EMS_HEAD:
      logicTable: NEMS_EMS_HEAD
```





首先建立一个分库的schema， 

```sql

create database sharding_db;
use sharding_db;

SHOW SCHEMA RESOURCES FROM sharding_db;

ADD RESOURCE nems (
    HOST=192.168.0.201,
    PORT=3306,
    DB=nems,
    USER=root,
    PASSWORD=Longnows@888
);


CREATE SHARDING DATABASE RULE (
	DATABASE_STRATEGY(
		TYPE = standard,
		SHARDING_COLUMN=site,
		SHARDING_ALGORITHM=(TYPE(NAME=INLINE,PROPERTIES("algorithm-expression"="ds->${site}")))
	)
);
```



