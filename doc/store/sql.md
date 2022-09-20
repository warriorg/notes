# 安装



# SQL语言

## 创建表

## 查询表

## 表关联

## 聚合函数

## 新增数据

## 修改数据

## 删除



# 高级特性







## MSSQL

```sql
-- 创建表
CREATE TABLE PROPERTY_PATROL (
   PATROL_SID           NVARCHAR(50)         NOT NULL,
   APARTMENT_SID        NVARCHAR(50)         NULL,
   PATROL_NAME          NVARCHAR(50)         NULL,
   PATROL_MAC           NVARCHAR(50)         NULL,
   PATROL_PWD           NVARCHAR(50)         NULL,
   PATROL_OPEN          NVARCHAR(50)         NULL,
   PATROL_CLOSE         NVARCHAR(50)         NULL,
   PATROL_ENABLED       INT                  NULL,
   PATROL_SUPPLIER      NVARCHAR(50)         NULL,
   REMARK               NVARCHAR(200)        NULL,
   CREATED_ON           DATETIME             NULL,
   CREATEDBY            NVARCHAR(50)         NULL,
   MODIFIED_ON          DATETIME             NULL,
   MODIFIEDBY           NVARCHAR(50)         NULL,
   CONSTRAINT PK_PROPERTY_PATROL_KEY PRIMARY KEY (PATROL_SID)
)
go
```



### 性能优化 

#### 思路

* 使用慢查询功能

* explain命令查看有问题的SQL的执行计划

* 使用show profile[s]产看有问题的SQL的性能使用情况


全值匹配我最爱，最左前缀要准守 带头大哥不能少，中间兄弟不能断 索引列上少计算，范围之后全失效 like百分写最右，覆盖索引不写星 空值少用还有or，字符类型需匹配

#### explain 查询计划

show global status like 'innodb_buffer_pool_pages_%'



mysql proxy 读写分离



## 常用sql

### 替换OR, 使用exists

```sql
select * from write_off_accounting_bom t1 where EXISTS ( 
	select 1 from ( 
	SELECT 'xxxx' material_no, 'yyyy' unit_consumption_version
	union 
	SELECT 'xxxxx' material_no, 'yyyy' unit_consumption_version
) t2 where t1.export_material_no = t2.material_no and t1.unit_consumption_version = t2.unit_consumption_version)
```

###  分组后取出最新的记录

```sql
select t.head_id, t.note from (
	select row_number() over (partition by head_id order by insert_time desc) num, 	
  head_id, 
  note, 
  insert_time from t_pre_dec_erp_approve t) t
where t.num = 1
```

https://stackoverflow.com/questions/3800551/select-first-row-in-each-group-by-group

# TERM

![sql_dml](../assets/images/sql_dml.png)

## DDL (Data Definition Language)

用于定义数据库的三级结构，包括外模式、概念模式、内模式及其相互之间的映像，定义数据的完整性、安全控制等约束

DDL不需要commit.

* CREATE
* ALTER
* DROP
* TRUNCATE
* COMMENT
* RENAME

## DML(Data Manipulation Language)

由DBMS提供，用于让用户或程序员使用，实现对数据库中数据的操作。DML分成交互型DML和嵌入型DML两类。依据语言的级别，DML又可分成过程性DML和非过程性DML两种。
需要commit.

* SELECT
* INSERT
* UPDATE
* DELETE
* MERGE
* CALL
* EXPLAIN PLAN
* LOCK TABLE

## DCL (Data Control Language)

授权，角色控制等

* GRANT 授权
* REVOKE 取消授权

## TCL (Transaction Control Language

事务控制语言

* SAVEPOINT 设置保存点
* ROLLBACK 回滚
* SET TRANSACTION

## DQL (Data Query Language)

