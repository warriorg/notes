## my.cnf
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

###macos下安装         

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