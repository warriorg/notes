# ShardingSphere-Proxy 

## Install

1. 通过[下载页面](https://shardingsphere.apache.org/document/current/cn/downloads/)获取 ShardingSphere-Proxy 安装包。
2. 解压缩后修改 `conf/server.yaml` 和以 `config-` 前缀开头的文件，如：`conf/config-xxx.yaml` 文件，进行分片规则、读写分离规则配置。配置方式请参考[配置手册](https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-proxy/yaml-config/)。
3. Linux 操作系统请运行 `bin/start.sh`，Windows 操作系统请运行 `bin/start.bat` 启动 ShardingSphere-Proxy。







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



