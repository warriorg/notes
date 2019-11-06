# MYSQL

## Install
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
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456';
FLUSH PRIVILEGES;
# MySQL 8.0.4前，执行：SET PASSWORD=PASSWORD('[新密码]');
# MySQL 8.0.4开始，MySQL的密码认证插件由“mysql_native_password”改为“caching_sha2_password”。
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
yum install mysql-community-server			# 安装数据库
systemctl start mysqld.service				# 启动数据库服务
grep 'temporary password' /var/log/mysqld.log		# 查看数据库默认密码
mysql -uroot -p										# 登录数据库
ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyNewPass4!';	# 修改默认密码
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





