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

## MySql

> 尚硅谷的mysql确实讲的不错

mysql 配置文件`my.cnf`
```bash

```

> 1. 一主多存
> 2. 一从一主

### 性能优化 

#### 思路

* 使用慢查询功能

* explain命令查看有问题的SQL的执行计划

* 使用show profile[s]产看有问题的SQL的性能使用情况


全值匹配我最爱，最左前缀要准守 带头大哥不能少，中间兄弟不能断 索引列上少计算，范围之后全失效 like百分写最右，覆盖索引不写星 空值少用还有or，字符类型需匹配

#### explain 查询计划



show global status like 'innodb_buffer_pool_pages_%'



mysql proxy 读写分离



