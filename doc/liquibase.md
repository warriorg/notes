# Quick Start

## Configure Liquibase

下载 [Liquibase](https://liquibase.org/download)，并将其解压。可以在环境变量中指定安装目录。

create liquibase.properties file

```properties
changeLogFile:dbchangelog.xml
url:  jdbc:postgresql://localhost:5432/mydatabase
username:  postgres
password:  password
# classpath:  postgresql-42.2.8.jar
# liquibaseProLicenseKey:  licensekey
# liquibase.hub.ApiKey:  APIkey
```

或者 Copy 数据库驱动到Liquibase的lib目录下,这样就不需要指定classpath

## 生成现有数据库快照

```bash
# 生成 ddl 语句
liquibase --changeLogFile=mydatabase_changelog.xml generateChangeLog

# 生成dml语句 --changelog-file
liquibase generateChangelog --changelog-file=common.postgresql.sql --diffTypes="data"

# docker 方式
docker run --rm -v $(pwd)/db:/liquibase/changelog liquibase:v4.8 generateChangelog --changelog-file=xxx.xml
```

## 更新数据库

```bash
liquibase update

# 为数据库生成tag 方便后面回滚
liquibase tag version_1.0
```

## 回滚

```bash
# Rollback changes made to the database based on the specific tag
liquibase rollback version_1.0
# Rollback the specified number of changes made to the database
liquibase rollbackCount 2
```

# Changelogs

## SQL Format

### SQL

格式化的SQL文件使用注释向Liquibase提供元数据。每个SQL文件必须以以下注释开头：

```sql
--liquibase formatted sql
```

#### changeset

格式化SQL文件中的每个更改集都必须以以下注释开头：

```sql
--changeset author:id attribute1:value1 attribute2:value2 [...]
```

##### changeset attributes

#### Preconditions

可以为每个更改集指定先决条件。目前只支持SQL检查前提条件。

```sql
--preconditions onFail:HALT onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM my_table
```

#### Rollback

变更集可能包括回滚变更集时要应用的语句。回滚语句的注释格式如下

```sql
--rollback SQL STATEMENT
```
