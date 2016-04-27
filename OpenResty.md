#[OpenResty](http://openresty.org/)

##应用场景
* 在lua中混合处理不同nginx模块输出（proxy, drizzle, postgres, redis, memcached等）。
* 在请求真正到达上游服务之前，lua中处理复杂的准入控制和安全检查。
比较随意的控制应答头（通过Lua）。
* 从外部存储中获取后端信息，并用这些信息来实时选择哪一个后端来完成业务访问。
* 在内容handler中随意编写复杂的web应用，同步编写异步访问后端数据库和其他存储。
* 在rewrite阶段，通过Lua完成非常复杂的处理。
* 在Nginx子查询、location调用中，通过Lua实现高级缓存机制。
* 对外暴露强劲的Lua语言，允许使用各种Nginx模块，自由拼合没有任何限制。该模块的脚本有充分的灵活性，同时提供的性能水平与本地C语言程序无论是在CPU时间方面以及内存占用差距非常小。所有这些都要求LuaJIT 2.x是启用的。其他脚本语言实现通常很难满足这一性能水平。



##OpenResty 安装
```bash
brew update
brew install pcre openssl

wget https://openresty.org/download/openresty-1.9.7.4.tar.gz
tar xzvf openresty-1.9.7.4.tar.gz
cd openresty-1.9.7.4

sudo ./configure --prefix=/opt/openresty\
             --with-cc-opt="-I/usr/local/include"\
             --with-luajit\
             --without-http_redis2_module \
             --with-ld-opt="-L/usr/local/lib"

#根据上一步的结果提示输入             
sudo make
sudo make install

```
>配置安装目录及需要激活的组件。使用选项 \-\-prefix=install\_path ，指定其安装目录（默认为/usr/local/openresty）。 使用选项 --with-Components 激活组件， \-\-without 则是禁止组件，你可以根据自己实际需要选择 with 及 without 。 输入如下命令，OpenResty 将配置安装在 /opt/openresty 目录下（注意使用root用户），激活 LuaJIT、HTTP\_iconv\_module 并禁止 http\_redis2\_module 组件。

将 `/opt/openresty/nginx/sbin` 设置进环境变量

[Lua学习](Lua.md)
安装LuaJit `brew install luajit`
验证LuaJit是否安装成功 `luajit -v`

##Nginx 配置

	worker_process			#表示工作进程的数量，一半设置为cpu核数
	worker_connections 		#表示灭一个工作进程最大连接数

###location 匹配规则
语法规则
	
	location[=|~|~*|^~]/uri/{...}

符号|含义
------|---
=|开头表示精确匹配
\^~|开头表示 uri 以某个常规字符串开头，理解为匹配 url 路径即可。nginx 不对 url 做编码，因此请求为/static/20%/aa，可以被规则^~ /static/ /aa匹配到（注意是空格）
~|开头表示区分大小写的正则匹配
~*|开头表示不区分大小写的正则匹配
/|通用匹配，任何请求都会匹配到

多个 location 配置的情况下匹配顺序为（参考资料而来，还未实际验证，试试就知道了，不必拘泥，仅供参考）:

* 首先匹配 =
* 其次匹配 \^~
* 其次是按文件中顺序的正则匹配
* 最后是交给 / 通用匹配
* 当有匹配成功时候，停止匹配，按当前匹配规则处理请求

##OpenResty

###HelloWorld
```bash
mkdir openresty-test openresty-test/logs/ openresty-test/conf
```
#### conf/nginx.conf
```bash
worker_processes 1;
error_log logs/error.log;

events {
    worker_connections 1024;
}

http {
    server {
        listen 6699;
        location / {
            default_type text/html;

            content_by_lua_block {
                ngx.say("HelloWorld")
            }
        }
    }
}
```
启动项目

```bash
 $ sudo nginx -p openresty-test
```
访问 http://127.0.0.1:6699/ 即可看到结果

