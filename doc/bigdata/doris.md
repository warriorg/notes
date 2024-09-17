# Doris

## 数据表设计

### 数据模型

* 当创建表的时候没有指定 Unique、Aggregate 或 Duplicate 时，会默认创建一个 Duplicate 模型的表，并自动按照一定规则选定排序列。没有指定任何数据模型，则建立的是明细模型（Duplicate），排序列系统自动选定了前 3 列。



#### 明细模型

在某些多维分析场景下，数据既没有主键，也没有聚合需求。

在明细数据模型中，数据按照导入文件中的数据进行存储，不会有任何聚合。即使两行数据完全相同，也都会保留。

```sql
-- 没有指定任何数据模型，则建立的是明细模型（Duplicate），排序列系统自动选定了前 3 列。
CREATE TABLE IF NOT EXISTS example_tbl_by_default
(
    `timestamp` DATETIME NOT NULL COMMENT "日志时间",
    `type` INT NOT NULL COMMENT "日志类型",
    `error_code` INT COMMENT "错误码",
    `error_msg` VARCHAR(1024) COMMENT "错误详细信息",
    `op_id` BIGINT COMMENT "负责人id",
    `op_time` DATETIME COMMENT "处理时间"
)
DISTRIBUTED BY HASH(`type`) BUCKETS 1
PROPERTIES (
"replication_allocation" = "tag.location.default: 1"
);

-- 指定排序列
CREATE TABLE IF NOT EXISTS example_tbl_duplicate
(
    `timestamp` DATETIME NOT NULL COMMENT "日志时间",
    `type` INT NOT NULL COMMENT "日志类型",
    `error_code` INT COMMENT "错误码",
    `error_msg` VARCHAR(1024) COMMENT "错误详细信息",
    `op_id` BIGINT COMMENT "负责人id",
    `op_time` DATETIME COMMENT "处理时间"
)
DUPLICATE KEY(`timestamp`, `type`, `error_code`)
DISTRIBUTED BY HASH(`type`) BUCKETS 1
PROPERTIES (
"replication_allocation" = "tag.location.default: 1"
);
```

#### 主键模型

当用户有数据更新需求时，可以选择使用主键数据模型（Unique）。主键模型能够保证 Key（主键）的唯一性，当用户更新一条数据时，新写入的数据会覆盖具有相同 key（主键）的旧数据。

```sql
CREATE TABLE IF NOT EXISTS example_tbl_unique_merge_on_write
(
    `user_id` LARGEINT NOT NULL COMMENT "用户id",
    `username` VARCHAR(50) NOT NULL COMMENT "用户昵称",
    `city` VARCHAR(20) COMMENT "用户所在城市",
    `age` SMALLINT COMMENT "用户年龄",
    `sex` TINYINT COMMENT "用户性别",
    `phone` LARGEINT COMMENT "用户电话",
    `address` VARCHAR(500) COMMENT "用户地址",
    `register_time` DATETIME COMMENT "用户注册时间"
)
UNIQUE KEY(`user_id`, `username`)
DISTRIBUTED BY HASH(`user_id`) BUCKETS 1
PROPERTIES (
"replication_allocation" = "tag.location.default: 1",
"enable_unique_key_merge_on_write" = "true"
);
```

* Unique 表的实现方式只能在建表时确定，无法通过 schema change 进行修改。

#### 聚合模型

```sql
CREATE TABLE IF NOT EXISTS example_tbl_agg1
(
    `user_id` LARGEINT NOT NULL COMMENT "用户id",
    `date` DATE NOT NULL COMMENT "数据灌入日期时间",
    `city` VARCHAR(20) COMMENT "用户所在城市",
    `age` SMALLINT COMMENT "用户年龄",
    `sex` TINYINT COMMENT "用户性别",
    `last_visit_date` DATETIME REPLACE DEFAULT "1970-01-01 00:00:00" COMMENT "用户最后一次访问时间",
    `cost` BIGINT SUM DEFAULT "0" COMMENT "用户总消费",
    `max_dwell_time` INT MAX DEFAULT "0" COMMENT "用户最大停留时间",
    `min_dwell_time` INT MIN DEFAULT "99999" COMMENT "用户最小停留时间"
)
AGGREGATE KEY(`user_id`, `date`, `city`, `age`, `sex`)
DISTRIBUTED BY HASH(`user_id`) BUCKETS 1
PROPERTIES (
"replication_allocation" = "tag.location.default: 1"
);
```

当导入数据时，对于 Key 列相同的行会聚合成一行，而 Value 列会按照设置的 AggregationType 进行聚合。AggregationType 目前有以下几种聚合方式和 agg_state：

- SUM：求和，多行的 Value 进行累加。
- REPLACE：替代，下一批数据中的 Value 会替换之前导入过的行中的 Value。
- MAX：保留最大值。
- MIN：保留最小值。
- REPLACE_IF_NOT_NULL：非空值替换。和 REPLACE 的区别在于对于 null 值，不做替换。
- HLL_UNION：HLL 类型的列的聚合方式，通过 HyperLogLog 算法聚合。
- BITMAP_UNION：BIMTAP 类型的列的聚合方式，进行位图的并集聚合。



#### 注意

1. Key 列必须在所有 Value 列之前。
2. 尽量选择整型类型。因为整型类型的计算和查找效率远高于字符串。
3. 对于不同长度的整型类型的选择原则，遵循够用即可。
4. 对于 VARCHAR 和 STRING 类型的长度，遵循够用即可。



#### 模型选择建议

因为数据模型在建表时就已经确定，且无法修改。所以，选择一个合适的数据模型非常重要。

1. Aggregate 模型可以通过预聚合，极大地降低聚合查询时所需扫描的数据量和查询的计算量，非常适合有固定模式的报表类查询场景。但是该模型对 count(*) 查询很不友好。同时因为固定了 Value 列上的聚合方式，在进行其他类型的聚合查询时，需要考虑语意正确性。
2. Unique 模型针对需要唯一主键约束的场景，可以保证主键唯一性约束。但是无法利用 ROLLUP 等预聚合带来的查询优势。对于聚合查询有较高性能需求的用户，推荐使用自 1.2 版本加入的写时合并实现。
3. Duplicate 适合任意维度的 Ad-hoc 查询。虽然同样无法利用预聚合的特性，但是不受聚合模型的约束，可以发挥列存模型的优势（只读取相关列，而不需要读取所有 Key 列）。
4. 如果有部分列更新的需求，请查阅文档[主键模型部分列更新](https://doris.apache.org/zh-CN/docs/data-operate/update/update-of-unique-model) 与 [聚合模型部份列更新](https://doris.apache.org/zh-CN/docs/data-operate/update/update-of-aggregate-model) 获取相关使用建议。



### 分区分桶



## 数据表设计

```sql
SHOW DATA TYPES;
```

```sql
create table  dwi_sc_goods_rt_f (
	`uid` varchar(50) not null  comment '主键',
	`site` varchar(50) comment 'site',
	goods_name varchar(50) comment 'goods name',
	id varchar(200) comment 'id',
	ssot_time datetime comment 'ssot_time'
)
engine=OLAP
UNIQUE KEY(`uid`)
distributed by hash(`uid`) buckets 32
properties (
	"replication_allocation" = "tag.location.default:1",
	"is_being_synced" = "false",
	"storage_format" = "V2",
	"light_schema_change" = "true",
	"disable_auto_compaction" = "false",
	"enable_single_replica_compaction" = "false"
)
```