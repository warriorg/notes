# Quick Start

## Configure Liquibase

create liquibase.properties file

```properties
changeLogFile:dbchangelog.xml  
url:  jdbc:postgresql://localhost:5432/mydatabase
username:  postgres  
password:  password 
classpath:  postgresql-42.2.8.jar
# liquibaseProLicenseKey:  licensekey
# liquibase.hub.ApiKey:  APIkey
```

## 生成现有数据库快照

```bash
liquibase --changeLogFile=mydatabase_changelog.xml generateChangeLog
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
