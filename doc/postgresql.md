# Install
## Centos8


```bash
# 设置安装源
sudo dnf install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-8-x86_64/pgdg-redhat-repo-latest.noarch.rpm
sudo dnf -qy module disable postgresql
sudo dnf install -y postgresql13-server

# 初始化数据库并自启动
sudo /usr/pgsql-13/bin/postgresql-13-setup initdb
sudo systemctl enable postgresql-13
sudo systemctl start postgresql-13


# 设置防火墙
firewall-cmd --permanent --add-port=5432/tcp  
firewall-cmd --permanent --add-port=80/tcp  
firewall-cmd --reload

# 设置密码
passwd postgres
su - postgres
psql -U postgres
ALTER USER postgres WITH PASSWORD 'NewPassword';
\q

# 开启远程访问
vim /var/lib/pgsql/13/data/postgresql.conf
# listen_addresses = 'localhost'
listen_addresses = '*'

# 信任远程连接
vim /var/lib/pgsql/13/data/pg_hba.conf
# IPv4 local connections:
host    all            all      127.0.0.1/32      trust
host    all            all      192.168.0.1/24    trust  # 需要连接的服务器IP地址

# 重启
systemctl restart postgresql-13
# 设置自启动服务
systemctl enable postgresql-13   

# 新建数据库
createdb mydb
psql mydb
select version();

# 删除数据库
dropdb mydb

```



# SQL Language

## Populating a Table With Rows

```sql
COPY weather FROM '/home/user/weather.txt';
```



## Indexes

```sql
CREATE INDEX name ON table USING HASH (column);		-- 默认创建B-tree, 可以使用USING制定索引的类型
```

### Index Type

#### B-Tree

#### Hash

#### GiST

#### SP-GiST

#### GIN

#### BRIN



## 性能

### EXPLAIN



### EXPLAIN ANALYZE 

EXPLAIN实际执行查询，然后显示每个计划节点中积累的真实行数和真实运行时间，以及普通EXPLAIN所显示的相同估算值


# Server Administration


# 数据库管理

## 查看

```sql
-- 命令行执行sql文件
pssql -U postgres -f xxx.sql

-- 连接数据库
psql -U postgres 
-- 查看所有数据库
\l
SELECT datname FROM pg_database;
-- 选择数据库
\c demo
-- 显示所有表
\d
-- 显示表的结构
\d tablename
```

