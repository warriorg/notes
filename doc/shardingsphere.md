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
create database demo;
use demo;

SHOW SCHEMA RESOURCES FROM demo;
DROP RESOURCE demo1, demo2;

ADD RESOURCE demo1 (
    HOST=192.168.0.201,
    PORT=3306,
    DB=demo1,
    USER=root,
    PASSWORD=Longnows@888,
		PROPERTIES("maximumPoolSize"=10,"idleTimeout"="30000")
);

ADD RESOURCE demo2 (
    HOST=192.168.0.201,
    PORT=3306,
    DB=demo2,
    USER=root,
    PASSWORD=Longnows@888,
		PROPERTIES("maximumPoolSize"=10,"idleTimeout"="30000")
);

SHOW SHARDING ALGORITHMS FROM demo;

DROP DEFAULT SHARDING DATABASE STRATEGY;
DROP SHARDING ALGORITHM database_inline;
CREATE SHARDING ALGORITHM database_inline (
	TYPE(NAME=inline,PROPERTIES("algorithm-expression"="demo${site}"))
);
CREATE DEFAULT SHARDING DATABASE STRATEGY (
	TYPE = standard,SHARDING_COLUMN=site,SHARDING_ALGORITHM=database_inline
);

SHOW SHARDING TABLE RULES;
DROP SHARDING TABLE RULE student_info;
CREATE SHARDING TABLE RULE student_info (
	DATANODES("demo1.student_info", "demo2.student_info")
)

SELECT * FROM student_info where site = 1;

SHOW SINGLE TABLES student_info;
SHOW DEFAULT SHARDING STRATEGY;
SHOW SHARDING TABLE NODES;
```

## 核心概念

### 表

表是透明化数据分片的关键概念。

#### 逻辑表

相同结构的水平拆分数据库（表）的逻辑名称，是 SQL 中表的逻辑标识。 例：订单数据根据主键尾数拆分为 10 张表，分别是 `t_order_0` 到 `t_order_9`，他们的逻辑表名为 `t_order`

#### 真实表

在水平拆分的数据库中真实存在的物理表。 即上个示例中的 `t_order_0` 到 `t_order_`

#### 绑定表

指分片规则一致的一组分片表。 使用绑定表进行多表关联查询时，必须使用分片键进行关联，否则会出现笛卡尔积关联或跨库关联，从而影响查询效率。 例如：`t_order` 表和 `t_order_item` 表，均按照 `order_id` 分片，并且使用 `order_id` 进行关联，则此两张表互为绑定表关系。 绑定表之间的多表关联查询不会出现笛卡尔积关联，关联查询效率将大大提升



#### 广播表

指所有的分片数据源中都存在的表，表结构及其数据在每个数据库中均完全一致。 适用于数据量不大且需要与海量数据的表进行关联查询的场景，例如：字典表。

#### 单表

指所有的分片数据源中仅唯一存在的表。 适用于数据量不大且无需分片的表。



### 数据节点

数据分片的最小单元，由数据源名称和真实表组成。 例：`ds_0.t_order_0`。

逻辑表与真实表的映射关系，可分为均匀分布和自定义分布两种形式。

​	

#### 均匀分布

指数据表在每个数据源内呈现均匀分布的态势， 例如：

```
db0
  ├── t_order0
  └── t_order1
db1
  ├── t_order0
  └── t_order1
```

#### 自定义分布

指数据表呈现有特定规则的分布， 例如：

```
db0
  ├── t_order0
  └── t_order1
db1
  ├── t_order2
  ├── t_order3
  └── t_order4
```

### 分片

#### 分片键

用于将数据库（表）水平拆分的数据库字段。

#### 分片算法

##### 取模

##### 哈希

##### 范围

##### 时间



