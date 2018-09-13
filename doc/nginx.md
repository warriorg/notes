## macos

brew 安装nginx


```
brew tap homebrew/nginx  #拉取nginx
brew install nginx-full --with-rtmp-module  #安装 rtmp 模块, 看情况添加
brew info nginx-full #打印信息
```

## 查看 nginx 安装信息
`nginx -V`

nginx配置文件的位置：

`/usr/local/etc/nginx/nginx.conf`

```
nginx -s 重新加载配置|重启|停止|退出
nginx -s reload|reopen|stop|quit

nginx -V 查看版本，以及配置文件地址
nginx -v 查看版本
nginx -c filename 指定配置文件
nginx -h 帮助
nginx -t 测试配置是否有语法错误
```

### 测试 rtmp 推流
`ffmpeg -f avfoundation -framerate 30 -i "0" -c:v libx264 -an -f flv rtmp://localhost/live/hello`
