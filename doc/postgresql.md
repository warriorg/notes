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




# Server Administration



