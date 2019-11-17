## Install

### Linux


### macos
brew 安装nginx
```
brew tap homebrew/nginx  #拉取nginx
brew install nginx-full --with-rtmp-module  #安装 rtmp 模块, 看情况添加
brew info nginx-full #打印信息
```

### 查看 nginx 安装信息

`nginx -V`

nginx配置文件的位置：

```
nginx -s 重新加载配置|重启|停止|退出
nginx -s reload|reopen|stop|quit

nginx -V 查看版本，以及配置文件地址
nginx -v 查看版本
nginx -c filename 指定配置文件
nginx -h 帮助
nginx -t 测试配置是否有语法错误
```



## 配置

```bash
#user  nobody;
# worker角色的工作进程的个数，master进程是接收并分配请求给worker处理。这个数值简单一点可以设置为cpu的核数grep ^processor /proc/cpuinfo | wc -l，也是 auto 值，如果开启了ssl和gzip更应该设置成与逻辑CPU数量一样甚至为2倍，可以减少I/O操作。如果nginx服务器还有其它服务，可以考虑适当减少。
worker_processes  1;

events {
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    server {
        listen       8080;
        server_name  localhost;
        #charset koi8-r;
        #access_log  logs/host.access.log  main;

        location / {
            root   html;
            index  index.html index.htm;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        location ~ /jg {
			
        }  
    }
    include servers/*;
}
```



### 测试 rtmp 推流

`ffmpeg -f avfoundation -framerate 30 -i "0" -c:v libx264 -an -f flv rtmp://localhost/live/hello`



## 基本概念

### 应用场景

* 静态资源服务
* 反向代理
  * Nginx 强大的性能
  * 缓存
  * 负载均衡
* API服务 OpenRestry

### 优点

1. 高并发，高性能

2. 可扩展性好

3. 高可可靠性

4. 热部署
5. BSD许可证

### 主要组成部分

1. Nginx 二进制可执行文件
2. Nginx.conf 配置文件
3. access.log 访问日志
4. error.log 错误日志

### 编译



### 配置文件的通用语法介绍









## Nginx 架构基础

## 详解HTTP模块

## 反向代理与负载均衡

## Nginx的系统层性能优化

##  Nginx 与 OpenResty

