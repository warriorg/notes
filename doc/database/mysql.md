# MySQL

## Install

### 主从

#### 环境

* ubuntu
* mysql 8

#### 安装数据库

```bash
wget https://repo.mysql.com/mysql-apt-config_0.8.22-1_all.deb
sudo dpkg -i mysql-apt-config_0.8.22-1_all.deb
sudo apt-get update
sudo apt-get install mysql-server
# stop mysql service
sudo service mysql stop
# delete the mysql data directory
sudo rm -rf /var/lib/mysql
# recreate the mysql data directory
sudo mkdir /var/lib/mysql
sudo chown mysql:mysql /var/lib/mysql
sudo chmod 700 /var/lib/mys
# re-initialize 
sudo mysqld --defaults-file=/etc/mysql/my.cnf --initialize --lower_case_table_names=1 --user=mysql --console
```

#### 主服务器设置

`/etc/mysql/mysql.conf.d/mysqld.cnf`

```bash
# 设置服务器编码
character-set-server=utf8
# 忽略表名大小写
lower_case_table_names = 1
# 主服务器唯一ID
server-id=1
# 启用二进制日志
log-bin=mysql-bin
# 设置不要复制的数据库
binlog-ignore-db=sys
binlog-ignore-db=mysql
binlog-ignore-db=information_schema
binlog-ignore-db=performance_schema
```

#### 从服务器设置

```bash
# 设置服务器编码
character-set-server=utf8
# 忽略表名大小写
lower_case_table_names = 1
# 从服务器唯一ID
server-id=2
# 启用中继日志
relay-log=mysql-relay
```

#### 启动数据库

```bash
sudo service mysql start
# Retrieve the new generated password for MySQL user `root`
sudo grep 'temporary password' /var/log/mysql/error.log

sudo mysql -u root -p
ALTER USER 'root'@'localhost' IDENTIFIED BY '12345678';
update user set host ='%' where user='root';
FLUSH PRIVILEGES;
```

#### 主服务器设置

```bash
mysql -uroot -p  # 登陆

# 查看正在使用的 Binlog 文件
show master status\G;
# 执行 flush logs 操作，生成新的 BINLOG, 方便从服务器从当前开始同步
flush logs;
```

#### 从服务器设置

```bash
mysql -uroot -p # 登陆
# 设置主数据库参数
change master to master_host='192.168.0.202',master_port=3306,master_user='root',master_password='password',master_log_file='mysql-bin.000002',master_log_pos=1;
# 开始同步
start slave;

# 若出现错误，则停止同步，重置后再次启动
stop slave;
reset slave;
start slave;

# 查询Slave状态
show slave status\G
```

### macos下安装

```bash
brew install mysql
brew link mysql 
# link 错误的时候使用 /usr/local/share/man 为具体没有权限的路径
# sudo chown -R $(whoami) /usr/local/share/man
# 使用 brew services 管理mysql run, start, stop，restart
# run 运行，启动的时候不自动加载
# brew services run mysql

# 设置mysql密码
mysql -uroot
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '12345678';
FLUSH PRIVILEGES;
# MySQL 8.0.4前，执行：SET PASSWORD=PASSWORD('[新密码]');
# MySQL 8.0.4开始，MySQL的密码认证插件由“mysql_native_password”改为“caching_sha2_password”
```

### Debain

[APT install Guide](https://dev.mysql.com/doc/mysql-apt-repo-quick-guide/en/)

[MySQL APT repository](https://dev.mysql.com/downloads/repo/apt/)

```bash
wget https://repo.mysql.com/mysql-apt-config_0.8.22-1_all.deb
sudo dpkg -i mysql-apt-config_0.8.22-1_all.deb
sudo apt-get update
sudo apt-get install mysql-server

# The following signatures couldn't be verified because the public key is not available: NO_PUBKEY 467B942D3A79BD29The following signatures couldn't be verified because the public key is not available: NO_PUBKEY 467B942D3A79BD29
# 如果出现上面问题
apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 467B942D3A79BD29apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 467B942D3A79BD29

systemctl status mysql
	
# Improve MySQL Installation Security
sudo mysql_secure_installation

sudo mysql
```

修改root权限，增加远程连接和密码

```mysql
# host = '%' 代表可以从任何地方访问数据库
use mysql
ALTER USER 'root'@'localhost' IDENTIFIED BY '12345678';
update user set host ='%' where user='root';
FLUSH PRIVILEGES;
```



### Centos 7 安装

[Yum 安装](https://dev.mysql.com/doc/mysql-yum-repo-quick-guide/en/#repo-qg-yum-installing)
/etc/yum.repos.d/mysql-community.repo

```bash
[mysql80-community]
name=MySQL 8.0 Community Server
baseurl=http://repo.mysql.com/yum/mysql-8.0-community/el/7/$basearch/
enabled=1
gpgcheck=0
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql
```

```bash
# Enable to use MySQL 5.7
[mysql57-community]
name=MySQL 5.7 Community Server
baseurl=http://repo.mysql.com/yum/mysql-5.7-community/el/7/$basearch/
enabled=1
gpgcheck=0
gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql
```

> GPG key retrieval failed: [Errno 14] curl#37 - "Couldn't open file /etc/pki/rpm-gpg/RPM-GPG-KEY-mysql"
>
> gpgcheck=0  

```bash
yum repolist enabled | grep mysql    # 
yum module disable mysql # Disabling the Default MySQL Module
yum install mysql-community-server	# 安装数据库
systemctl start mysqld.service	# 启动数据库服务
grep 'temporary password' /var/log/mysqld.log	# 查看数据库默认密码
mysql -uroot -p # 登录数据库
ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyNewPass4!';	# 修改密码

# 查看密码规则
select @@validate_password_policy;
SHOW VARIABLES LIKE 'validate_password%';  
# 修改为简单密码规则
set global validate_password_policy=0;
```

访问IP

```bash
# 更改密码
> SET PASSWORD = PASSWORD('pass');
> use mysql;
> update user set host = '%' where user ='root';
> select host, user from user;
> flush privileges;
# 授权所用IP访问
# mysql 5.7
# SELECT user,authentication_string,plugin,host FROM mysql.user;
# ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Current-Root-Password';
# FLUSH PRIVILEGES;
> GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'pass' WITH GRANT OPTION;
> flush privileges;
```



### my.cnf

```bash
[mysqld]
# 设置端口
port=6033
# 忽略table name 大小写
lower_case_table_names=1
datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock
symbolic-links=0

log-error=/var/log/mysqld.log
pid-file=/var/run/mysqld/mysqld.pid
```

### 表名忽略大小写

Add `lower_case_table_names = 1` to the `[mysqld]` section in `/etc/mysql/mysql.conf.d/mysqld.cnf`

```bash
# stop mysql service
sudo service mysql stop
# delete the mysql data directory
sudo rm -rf /var/lib/mysql
# recreate the mysql data directory
sudo mkdir /var/lib/mysql
sudo chown mysql:mysql /var/lib/mysql
sudo chmod 700 /var/lib/mysql

# Add `lower_case_table_names = 1` to the `[mysqld]` section in `/etc/mysql/mysql.conf.d/mysqld.cnf`

# re-initialize 
sudo mysqld --defaults-file=/etc/mysql/my.cnf --initialize --lower_case_table_names=1 --user=mysql --console

sudo service mysql start
# Retrieve the new generated password for MySQL user `root`
sudo grep 'temporary password' /var/log/mysql/error.log
# 修改默认的登录密码，必须修改
sudo mysql -u root -p
ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyNewPa$$w0rd';
# 检查设置
SHOW VARIABLES LIKE 'lower_case_%';
```


## 索引

### 索引类型

#### B-Tree 

适用于全键值、键值范围或键前缀查询。按照顺序存储数据。

##### 有效条件
* 全值匹配
* 匹配最左前缀
* 匹配列前缀
* 匹配范围值
* 精确匹配某一列并范围匹配另外一列
* 只访问索引的查询

##### 限制
* 如果不是按照索引的最左列开始查找，则无法使用索引
* 不能跳过索引中的列
* 如果查询中有某个列的范围查询，则其右边所有列都无法使用索引优化查找

#### 哈希索引

#### R-Tree (空间数据索引)
MyISAM表支持空间索引，可以用作地理数据存储。和BTree索引不同，这类索引无须前缀查询。空间索引会从所有维度来索引数据。

#### 全文索引
#### 其它索引

### 索引的优点

1. 索引大大减少了服务器需要扫描的数据量。
2. 索引可以帮助服务器避免排序和临时表。
3. 索引可以将随机I/O变为顺序I/O。

> ==索引是最好的解决方案吗？==
> 索引并不总是最好的工具。总的来说，只有当索引帮助存储引擎快速查找到记录带来的好处大于其带来的额外工作时，索引才是有效的。对于非常小的表，大部分情况下简单的全表扫描更高效。对于中到大型的表，索引就非常有效。但对于特大型的表，建立和使用索引的代价将随之增长。这种情况下，则需要一种技术可以直接区分出查询需要的一组数据，而不是一条记录一条记录地匹配。例如可以使用分区技术，请参考第7章。如果表的数量特别多，可以建立一个元数据信息表，用来查询需要用到的某些特性。例如执行那些需要聚合多个应用分布在多个表的数据的查询，则需要记录“哪个用户的信息存储在哪个表中”的元数据，这样在查询时就可以直接忽略那些不包含指定用户信息的表。对于大型系统，这是一个常用的技巧。事实上，Infobright就是使用类似的实现。对于TB级别的数据，定位单条记录的意义不大，所以经常会使用块级别元数据技术来替代索引。

### 高性能的索引策略

1. 独立的列
2. 前缀索引和索引选择性
3. 多列索引
4. 选择合适的索引列顺序
5. 聚簇索引
6. 覆盖索引
7. 使用索引扫描来做排序
8. 压缩（前缀压缩）索引
9. 冗余和重复索引
10. 未使用的索引
11. 索引和锁

### 索引案例学习

## 查询



##  架构

![mysql-architecture](../assets/images/mysql-architecture.png)



## Server层对象





## MyIsam



## InnoDB

### 架构

![innodb-architecture](../assets/images/innodb-architecture.png)

### 磁盘文件

### 内存结构



## 事务



## 读写分离



## 分库分表

# 运维

## 导入数据文件

* `mysql -h localhost -u root -p123456 < ./schema.sql`
* 登录进数据库后 `source ./schema.sql`

## 恢复删除的数据

```mysql
# 查看正在使用的 Binlog 文件
show master status\G;
# 执行 flush logs 操作，生成新的 BINLOG
flush logs;

#  根据时间确定位置信息
mysqlbinlog --no-defaults --base64-output=decode-rows -v \
 --start-datetime  "2022-03-22 02:00:00" \
 --database test  binlog.000082 | less

# 根据位置导出 SQL 文件
mysqlbinlog --no-defaults --base64-output=decode-rows -v \
 --start-position "61332401" --stop-position "61332501" \
 --database test_binlog binlog.000082 \
 > /home/mysql_backup/test_binlog_step1.sql
 
mysqlbinlog --no-defaults --base64-output=decode-rows -v \
 --start-position "61332502" --stop-position "613369001" \
 --database test_binlog binlog.000082 \
 > /home/mysql_backup/test_binlog_step2.sql
 
# 使用 mysql 进行恢复
mysql -u cisco -p < /home/mysql_backup/test_binlog_step1.sql
mysql -u cisco -p < /home/mysql_backup/test_binlog_step2.sql
```



## 注意
* mysql 在Linux下默认不区分大小写
* mysql 字符集 ci 的在比较字符串是默认忽略大小写
