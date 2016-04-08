##问题
Cannot connect to the Docker daemon. Is the docker daemon running on this host?
>*重启*

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

```







##Listing containers
``` bash
$ docker ps # Lists only running containers
$ docker ps -a # Lists all containers
```

##Committing (saving) a container state
```base
# Commit your container to a new named image
$ docker commit <container> <some_name>
```

##Delete all containers
```bash
docker rm $(docker ps -a -q)
```
##Delete all images
```bash
docker rmi $(docker images -q)
```

## Other command
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


##在一台主机上测试Consul集群
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

##ubuntu 上安装 Java8
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
##ubuntu安装tomcat8
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

##Error
###Layer already being pulled by another client. Waiting.

```bash
$ docker-machine stop default
$ docker images -q | xargs docker rmi
$ docker-machine start default
```