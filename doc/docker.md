## 基础知识

**容器** 一种沙盒技术

### Namespace

Docker 和虚拟机技术一样，从操作系统级上实现了资源的隔离，它本质上是宿主机上的进程（容器进程），所以资源隔离主要就是指进程资源的隔离。实现资源隔离的核心技术就是 Linux namespace。

隔离意味着可以抽象出多个轻量级的内核（容器进程），这些进程可以充分利用宿主机的资源，宿主机有的资源容器进程都可以享有，但彼此之间是隔离的，同样，不同容器进程之间使用资源也是隔离的，这样，彼此之间进行相同的操作，都不会互相干扰，安全性得到保障。

为了支持这些特性，Linux namespace 实现了 6 项资源隔离，基本上涵盖了一个小型操作系统的运行要素，包括主机名、用户权限、文件系统、网络、进程号、进程间通信。

| 名称    | 宏定义        | 隔离内容                                                     | 发布版本                                           |
| ------- | ------------- | ------------------------------------------------------------ | -------------------------------------------------- |
| IPC     | CLONE_NEWIPC  | System V IPC, POSIX message queues                           | since Linux 2.6.19                                 |
| Network | CLONE_NEWNET  | network device interfaces, IPv4 and IPv6 protocol stacks, IP routing tables, firewall rules, the /proc/net and /sys/class/net directory trees, sockets, etc | since Linux 2.6.24                                 |
| Mount   | CLONE_NEWNS   | Mount points                                                 | since Linux 2.4.19                                 |
| PID     | CLONE_NEWPID  | Process IDs                                                  | since Linux 2.6.24                                 |
| User    | CLONE_NEWUSER | User and group IDs                                           | started in Linux 2.6.23 and completed in Linux 3.8 |
| UTS     | CLONE_NEWUTS  | Hostname and NIS domain name                                 | since Linux 2.6.19                                 |

这 6 项资源隔离分别对应 6 种系统调用，通过传入上表中的参数，调用 clone() 函数来完成。

```
int clone(int (*child_func)(void *), void *child_stack, int flags, void *arg);
```

clone() 函数相信大家都不陌生了，它是 fork() 函数更通用的实现方式，通过调用 clone()，并传入需要隔离资源对应的参数，就可以建立一个容器了（隔离什么我们自己控制）。

一个容器进程也可以再 clone() 出一个容器进程，这是容器的嵌套。

### Cgroups



## 安装

### [Docker 中国官方镜像加速](https://www.docker-cn.com/registry-mirror)

修改 `/etc/docker/daemon.json` 文件并添加上 registry-mirrors 键值。

```
{
  "registry-mirrors": ["https://registry.docker-cn.com"]
}
```

修改保存后重启 Docker 以使配置生效。

```bash
$ sudo systemctl daemon-reload
$ sudo systemctl restart docker
```

然后使用`docker info`检查是否生效





##常用命令

```bash
#运行一个容器
docker run -i -t tomcat /bin/bash   
#docker run -d  -p 7001:6379 redis  

#容器命名
docker run --name bob_the_container -i -t ubuntu /bin/bash

#重启已经停止的容器
docker start bob_the_container

#附着到已启动的容器上
docker attach bob_the_container

#进入容器
docker exec -it redmine bash

#删除Images
docker rmi imageid
#删除提示 must be force时
docker rmi -f imageid

#列出最新的1000条日志
docker logs --tail 1000 ihome-tomcat

# remove all containers
docker rm $(docker ps -all -q)
# remove all image
docker rmi $(docker image ls -q)

```

### 参数
```bash
-m 限制内存
```

#### 删除无用的docker实例及镜像
```bash
# 删除停止或一直处于已创建状态的实例
docker ps --filter "status=exited"|sed -n -e '2,$p'|awk '{print $1}'|xargs docker rm
docker ps --filter "status=created"|sed -n -e '2,$p'|awk '{print $1}'|xargs docker rm
# 删除虚悬镜像
docker image prune --force
# 删除REPOSITORY是长长uuid的镜像
docker images | sed -n -e '2,$p'|awk '{if($1 ~ /[0-9a-f]{32}/) print $1":"$2}'|xargs docker rmi
# 删除TAG是长长uuid的镜像
docker images | sed -n -e '2,$p'|awk '{if($2 ~ /[0-9a-f]{64}/) print $1":"$2}'|xargs docker rmi
```




### Listing containers
```bash
$ docker ps # Lists only running containers
$ docker ps -a # Lists all containers
```

### Committing (saving) a container state
```base
# Commit your container to a new named image
$ docker commit <container> <some_name>
```

##Dockerfile
1. Docker从基础镜像运行一个容器
2. 执行一条指令，对容器修改
3. 执行类似docker commit的操作，提交一个新的镜像
4. Docker再基于刚提交的镜像运行一个新容器
5. 执行Dockerfile中的下一条指令，知道所有指令都执行完成

>docker build 执行时, Dockerfile 中的所有指令都被执行并且提交，并且在该命令成功结束后返回一个新镜像。

###Dockerfile指令

## Docker Compose

### 环境变量

#### COMPOSE_PROJECT_NAME

设置项目名称。在启动时，此值连同服务名称一起组成容器名称。

```bash
# 同样的功能
COMPOSE_PROJECT_NAME=zk_test docker-compose up
docker-compose -p zk_test up
```





### build

```
--compress              Compress the build context using gzip.
--force-rm              Always remove intermediate containers.
--no-cache              Do not use cache when building the image.
--pull                  Always attempt to pull a newer version of the image.
-m, --memory MEM        Sets memory limit for the build container.
--build-arg key=val     Set build-time variables for services.
--parallel              Build images in parallel.
```

### bundle

```
--push-images              Automatically push images for any services
													 which have a `build` option specified.

-o, --output PATH          Path to write the bundle file to.
                           Defaults to "<project name>.dab".
```

### up

构建、(重新)创建、启动和附加到服务的容器



## 实战

### 在一台主机上测试Consul集群
```bash
$ docker run -d --name node1 -h node1 progrium/consul -server -bootstrap-expect 3
$ JOIN_IP="$(docker inspect -f '{{ .NetworkSettings.IPAddress }}' node1)"
$ docker run -d --name node2 -h node2 progrium/consul -server -join $JOIN_IP
$ docker run -d --name node3 -h node3 progrium/consul -server -join $JOIN_IP

#没有暴露出任何一个端口用以访问这个集群，但是我们可以使用第四个agent节点以client的模式（不是用 -server参数）。
#这意味着他不参与选举但是可以和集群交互。（译注: 参与选举说的应该是选举leader的时候， 他没有话语权）
#而且这个client模式的agent也不需要磁盘做持久化。（译注：就是一个交互的通道）
$ docker run -d -p 8400:8400 -p 8500:8500 -p 8600:53/udp -h node4 progrium/consul -join $JOIN_IP
```

### ubuntu 上安装 Java8
```base
# Pull base image. if you use "latest" instead of "trusty",
# you will use latest ubuntu images as base image
FROM ubuntu:trusty

# Set maintainer details
MAINTAINER SHAMEERA

# Install prerequisites
RUN apt-get update
RUN apt-get install -y software-properties-common

# Install java8
RUN add-apt-repository -y ppa:webupd8team/java
RUN apt-get update
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java8-installer
```
### ubuntu安装tomcat8
```bash
FROM ubuntu:14.04

MAINTAINER Carlos Moro <cmoro@deusto.es>

ENV TOMCAT_VERSION 8.0.26

# Set locales
RUN locale-gen en_GB.UTF-8
ENV LANG en_GB.UTF-8
ENV LC_CTYPE en_GB.UTF-8

# Fix sh
RUN rm /bin/sh && ln -s /bin/bash /bin/sh

# Install dependencies
RUN apt-get update
RUN apt-get install -y git build-essential curl wget software-properties-common

# Install JDK 8
RUN \
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
add-apt-repository -y ppa:webupd8team/java && \
apt-get update && \
apt-get install -y oracle-java8-installer wget unzip tar && \
rm -rf /var/lib/apt/lists/* && \
rm -rf /var/cache/oracle-jdk8-installer

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Get Tomcat
RUN wget --quiet --no-cookies http://apache.rediris.es/tomcat/tomcat-8/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz -O /tmp/tomcat.tgz

# Uncompress
RUN tar xzvf /tmp/tomcat.tgz -C /opt
RUN mv /opt/apache-tomcat-${TOMCAT_VERSION} /opt/tomcat
RUN rm /tmp/tomcat.tgz

# Remove garbage
RUN rm -rf /opt/tomcat/webapps/examples
RUN rm -rf /opt/tomcat/webapps/docs
RUN rm -rf /opt/tomcat/webapps/ROOT

# Add admin/admin user
ADD tomcat-users.xml /opt/tomcat/conf/

ENV CATALINA_HOME /opt/tomcat
ENV PATH $PATH:$CATALINA_HOME/bin

EXPOSE 8080
EXPOSE 8009
VOLUME "/opt/tomcat/webapps"
WORKDIR /opt/tomcat

# Launch Tomcat
CMD ["/opt/tomcat/bin/catalina.sh", "run"]
```

### FTP

```bash
docker run -d -v /Users/warriorg/Downloads:/home/vsftpd -p 20:20 -p 21:21 \
-p 47400-47470:47400-47470 \
-e FTP_USER=test \
-e FTP_PASS=test \
-e PASV_ADDRESS=0.0.0.0 \
--name ftp bogem/ftp
```



## 常见问题

### Layer already being pulled by another client. Waiting.

```bash
$ docker-machine stop default
$ docker images -q | xargs docker rmi
$ docker-machine start default
```

### Cannot connect to the Docker daemon. Is the docker daemon running on this host?
>*重启*

在命令行直接启动
>` /Applications/Docker/Docker\ Quickstart\ Terminal.app/Contents/Resources/Scripts/start.sh`

### 改变docker存储位置
You can change Docker's storage base directory (where container and images go) using the -g option when starting the Docker daemon.

* Ubuntu/Debian: edit your /etc/default/docker file with the -g option: DOCKER_OPTS="-dns 8.8.8.8 -dns 8.8.4.4 -g /mnt"

* Fedora/Centos: edit /etc/sysconfig/docker, and add the -g option in the other_args variable: ex. other_args="-g /var/lib/testdir". If there's more than one option, make sure you enclose them in " ". After a restart, (service docker restart) Docker should use the new directory.

Using a symlink is another method to change image storage.

*Caution - These steps depend on your current /var/lib/docker being an actual directory (not a symlink to another location).*

1. Stop docker: `service docker stop`. Verify no docker process is running ps faux
2. Double check docker really isn't running. Take a look at the current docker directory: `ls /var/lib/docker/`
3. Make a backup - `tar -zcC /var/lib docker > /mnt/pd0/var_lib_docker-backup-$(date +%s).tar.gz`
4. Move the /var/lib/docker directory to your new partition: `mv /var/lib/docker /mnt/pd0/docker`
5. Make a symlink: `ln -s /mnt/pd0/docker /var/lib/docker`
6. Take a peek at the directory structure to make sure it looks like it did before the mv: `ls /var/lib/docker/` (note the trailing slash to resolve the symlink)
7. Start docker back up `service docker start`
8. restart your containers



## [Kubernetes](kubernetes.md)