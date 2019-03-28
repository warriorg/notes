## FastDFS简介

FastDFS 是一个用C编写额开源的高性能==分布式文件系统（Distributed File System， 简称DFS）==

### FastDFS组成
**tracker server**

跟踪服务器：用来调度来自客户端的请求。且在内存中记录所有存储组和存储服务器的信息状态。

#### **storage server** 

存储服务器：用来存储文件(data)和文件属性(metadata)

#### **client** 

客户端：业务请求发起方，通过专用接口基于TCP协议与tracker以及storage server进行交互

**group**

组，也可称为卷：同组内上的文件是完全相同的

#### **文件标识** 

包括两部分：组名和文件名(包含路径)

#### **meta data** 

文件相关属性：键值对(Key Value Pair)方式

#### fid 

文件标识符： （例如： group1/M00/00/00/wKgCD1yU7_uAaEtmAAADHo8n7u00097846 ）

- group1：存储组的组名；上传完成后，需要客户端自行保存 
- M00：服务器配置的虚拟路径，与磁盘选项store_path#对应 
- 00/00：两级以两位16进制数字命名的目录 
- wKgCD1yU7_uAaEtmAAADHo8n7u00097846：文件名，与原文件名并不相同；由storage server根据特定信息生成。文件名包含：源存储服务器的IP地址、文件创建时间戳、文件大小、随机数和文件扩展名等

### FastDFS同步机制

- 同一组内的storage server之间是对等的，文件上传、删除等操作可以在任意一台storage server上进行； 
- 文件同步只在同组内的storage server之间进行，采用push方式，即源服务器同步给目标服务器； 
- 源头数据才需要同步，备份数据不需要再次同步，否则就构成环路了；  

​    提示：上述第二条规则有个例外，就是新增加一台storage server时，由已有的一台storage server将已有的所有数据（包括源头数据和备份数据）同步给该新增服务器。



## Install

| 名称  | ip           | 备注              |
| ----- | ------------ | ----------------- |
| FS-15 | 192.168.2.15 | Tracker & Storage |
| FS-16 | 192.168.2.16 | Tracker & Storage |

### 安装 **libfastcommon**

```bash
wget https://github.com/happyfish100/libfastcommon/archive/V1.0.39.zip
mv V1.0.39.zip libfastcommon-1.0.39.zip
unzip libfastcommon-1.0.39.zip -d ~
cd /usr/local/libfastcommon-1.0.39/
./make.sh
./make.sh install
```

> 确认make没有错误后，执行安装，64位系统默认会复制到/usr/lib64下



### 安装fastdfs

```bash
wget https://github.com/happyfish100/fastdfs/archive/V5.11.zip
mv V5.11.zip fastdfs-5.11.zip
unzip libfastcommon-1.0.39.zip
cd /usr/local/fastdfs-5.11
./make.sh
./make install 
## 生成的配置文件
ll /etc/fdfs
-rw-r--r--. 1 root root 1461 Mar 22 21:09 client.conf.sample
-rw-r--r--. 1 root root 7927 Mar 22 21:09 storage.conf.sample
-rw-r--r--. 1 root root  105 Mar 22 21:09 storage_ids.conf.sample
-rw-r--r--. 1 root root 7389 Mar 22 21:09 tracker.conf.sample

## 安装好的执行程序
ll /usr/bin/fdfs*
-rwxr-xr-x. 1 root root  304144 Mar 22 21:09 /usr/bin/fdfs_append_file
-rwxr-xr-x. 1 root root  317512 Mar 22 21:09 /usr/bin/fdfs_appender_test
-rwxr-xr-x. 1 root root  317288 Mar 22 21:09 /usr/bin/fdfs_appender_test1
-rwxr-xr-x. 1 root root  303840 Mar 22 21:09 /usr/bin/fdfs_crc32
-rwxr-xr-x. 1 root root  304176 Mar 22 21:09 /usr/bin/fdfs_delete_file
-rwxr-xr-x. 1 root root  304936 Mar 22 21:09 /usr/bin/fdfs_download_file
-rwxr-xr-x. 1 root root  304528 Mar 22 21:09 /usr/bin/fdfs_file_info
-rwxr-xr-x. 1 root root  322432 Mar 22 21:09 /usr/bin/fdfs_monitor
-rwxr-xr-x. 1 root root 1111672 Mar 22 21:09 /usr/bin/fdfs_storaged
-rwxr-xr-x. 1 root root  327416 Mar 22 21:09 /usr/bin/fdfs_test
-rwxr-xr-x. 1 root root  326624 Mar 22 21:09 /usr/bin/fdfs_test1
-rwxr-xr-x. 1 root root  453936 Mar 22 21:09 /usr/bin/fdfs_trackerd
-rwxr-xr-x. 1 root root  305136 Mar 22 21:09 /usr/bin/fdfs_upload_appender
-rwxr-xr-x. 1 root root  306152 Mar 22 21:09 /usr/bin/fdfs_upload_file
```

> 确认make没有错误后，执行安装，默认会安装到/usr/bin中，以及会在/etc/fdfs生成以.sample结尾的4个文件

#### 配置tracker服务器

>  **[配置参考文档](http://bbs.chinaunix.net/thread-1941456-1-1.html)**

```bash
cd /etc/fdfs
cp tracker.conf.sample tracker.conf
```

**tracker.conf**

```bash
bind_addr=    										#绑定服务IP，如果不填则表示所有的
port=22122             						#提供服务的端口
base_path=/data/fastdfs				    #存储日志和数据的根目录
http.server_port=8080      				#HTTP服务端口，默认为8080
```

#### 配置storage服务器

```bash
cp storage.conf.sample  storage.conf
```

**storage.conf**

```bash
group_name=group1												#指定此 storage server 所在组，多个服务器同组同步文件
bind_addr=                              #绑定服务IP，可以不用填写（如果是tracker服务器，也可以配置tracker服务器自身IP地址）
base_path=/data/fastdfs			            #储日志和数据的根目录
store_path0=/data/fastdfs/store					#第1个存储目录
tracker_server=192.168.2.15:22122				#tracker服务器的IP和端口，如果有多个tracker服务器可以写多行
http.server_port=8888                   #HTTP服务端口，默认为8888，你也可以不用改，但是测试访问你要带8080端口访问
```

#### 修改tracker服务器客户端配置文件

```bash
cp client.conf.sample client.conf
```

**client.conf**

```bash
base_path=/data/fastdfs            	 #修改路径
tracker_server=172.18.2.15:22122   	 #tracker服务器IP和端口，有多个tracker服务器可以写多条配置
```
`mkdir -p /data/fastdfs/store` 建立数据存放的目录

#### 安装测试

```bash
/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf start			# 启动 trackerd  [start | stop | restart]
/usr/bin/fdfs_storaged /etc/fdfs/storage.conf start			# 启动 storaged
netstat -lntup |grep fdfs 			# 查看服务是否启动
fdfs_upload_file /etc/fdfs/client.conf /etc/passwd			# 测试文件上传
# 服务器返回
# group1/M00/00/00/wKgCD1yU7_uAaEtmAAADHo8n7u00097846
fdfs_download_file /etc/fdfs/client.conf group1/M00/00/00/wKgCD1yU7_uAaEtmAAADHo8n7u00097846			# 文件下载
fdfs_file_info /etc/fdfs/client.conf group1/M00/00/00/wKgCD1yU7_uAaEtmAAADHo8n7u00097846  # 查看文件信息
fdfs_monitor /etc/fdfs/client.conf											# 监控集群
```

#### 安装nginx

##### fastdfs-nginx-module模块

> ==fastdfs-nginx-module模块只需要安装到storage上。==
>
> FastDFS 通过 Tracker 服务器,将文件放在 Storage 服务器存储,但是同组存储服务器之间需要进入文件复制,有同步延迟的问题。假设 Tracker 服务器将文件上传到了 ip01,上传成功后文件 ID 已经返回给客户端。此时 FastDFS 存储集群机制会将这个文件同步到同组存储 ip02,在文件还没有复制完成的情况下,客户端如果用这个文件 ID 在 ip02 上取文件,就会出现文件无法访问的错误。而 fastdfs-nginx-module 可以重定向文件连接到源服务器取文件,避免客户端由于复制延迟导致的文件无法访问错误。(解压后的 fastdfs-nginx-module 在 nginx 安装时使用)。

```bash
wget https://github.com/happyfish100/fastdfs-nginx-module/archive/V1.20.zip
mv V1.20.zip fastdfs-nginx-module-1.20.zip
unzip fastdfs-nginx-module-1.20.zip -d ~

```

**修改 fastdfs-nginx-module-1.20/src/config**

> 修改6行和15行的内容 针对1.20的版本

`vim fastdfs-nginx-module-1.20/src/config`

```bash
ngx_addon_name=ngx_http_fastdfs_module

if test -n "${ngx_module_link}"; then
ngx_module_type=HTTP
ngx_module_name=$ngx_addon_name
ngx_module_incs="/usr/include/fastdfs /usr/include/fastcommon/"
ngx_module_libs="-lfastcommon -lfdfsclient"
ngx_module_srcs="$ngx_addon_dir/ngx_http_fastdfs_module.c"
ngx_module_deps=
CFLAGS="$CFLAGS -D_FILE_OFFSET_BITS=64 -DFDFS_OUTPUT_CHUNK_SIZE='2561024' -DFDFS_MOD_CONF_FILENAME='"/etc/fdfs/mod_fastdfs.conf"'"
. auto/module
else
HTTP_MODULES="$HTTP_MODULES ngx_http_fastdfs_module"
NGX_ADDON_SRCS="$NGX_ADDON_SRCS $ngx_addon_dir/ngx_http_fastdfs_module.c"
CORE_INCS="$CORE_INCS /usr/include/fastdfs /usr/include/fastcommon/"
CORE_LIBS="$CORE_LIBS -lfastcommon -lfdfsclient"
CFLAGS="$CFLAGS -D_FILE_OFFSET_BITS=64 -DFDFS_OUTPUT_CHUNK_SIZE='2561024' -DFDFS_MOD_CONF_FILENAME='"/etc/fdfs/mod_fastdfs.conf"'"
fi
```

#### 安装nginx

```bash
# 设置软连接
ln -sv /usr/include/fastcommon /usr/local/include/fastcommon 
ln -sv /usr/include/fastdfs /usr/local/include/fastdfs 
ln -sv /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so

# 安装支持库
yum -y install pcre-devel openssl openssl-devel 
tar -xvf nginx-1.14.2.tar.gz
cd nginx-1.14.2/
./configure --prefix=/usr/local/nginx --add-module=/root/fastdfs-nginx-module-1.20/src
make
make install
/usr/local/nginx/sbin/nginx -V 			# 查看版本信息
cp /root/fastdfs-nginx-module-1.20/src/mod_fastdfs.conf /etc/fdfs/
vim /etc/fdfs/mod_fastdfs.conf
```

**mod_fastdfs.conf**

```bash
base_path=/data/fastdfs           #保存日志目录
tracker_server=192.168.53.85:22122
tracker_server=192.168.53.86:22122 
storage_server_port=23000         #storage服务器的端口号
group_name=group1                 #当前服务器的group名
url_have_group_name = true        #文件url中是否有group名
store_path_count=1                #存储路径个数，需要和store_path个数匹配
store_path0=/data/fastdfs/store   #存储路径
group_count = 2                   #设置组的个数
```

