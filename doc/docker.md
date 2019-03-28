## 安装

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

```

### 参数
```bash
-m 限制内存
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

### Delete all containers
```bash
docker rm $(docker ps -a -q)
```
### Delete all images
```bash
docker rmi $(docker images -q)
```

###  Other command
* `docker ps` - Lists containers.
* `docker logs` - Shows us the standard output of a container.
* `docker stop` - Stops running containers.

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