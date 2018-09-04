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

