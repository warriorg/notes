# INSTALL

## Debian

```bash
apt-get update
apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release

# Add Docker’s official GPG key
curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null

apt-get update
apt-get install -y docker-ce docker-ce-cli containerd.io

sudo usermod -aG docker ${USER} # 当前用户加入docker组 这样操作的时候就不需要root
```

### 以非root用户管理Docker

```bash
sudo groupadd docker
sudo usermod -aG docker $USER
newgrp docker
```

If you initially ran Docker CLI commands using `sudo` before adding your user to the `docker` group, you may see the following erro

```bash
WARNING: Error loading config file: /home/user/.docker/config.json -
stat /home/user/.docker/config.json: permission denied
```

To fix this problem

```bash
 sudo chown "$USER":"$USER" /home/"$USER"/.docker -R
 sudo chmod g+rwx "$HOME/.docker" -R
```

## 配置加速地址

创建或修改 `/etc/docker/daemon.json`：

```
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
    "registry-mirrors": [
        "https://docker.m.daocloud.io",
        "https://dockerproxy.com",
        "https://docker.mirrors.ustc.edu.cn",
        "https://docker.nju.edu.cn"
    ]
}
EOF
```

修改保存后重启 Docker 以使配置生效。

```bash
$ sudo systemctl daemon-reload
$ sudo systemctl restart docker
```

然后使用 `docker info`检查是否生效

### 参考

https://docs.docker.com/engine/install/debian/



# 基础知识

**容器** 一种沙盒技术

## Namespace

Docker 和虚拟机技术一样，从操作系统级上实现了资源的隔离，它本质上是宿主机上的进程（容器进程），所以资源隔离主要就是指进程资源的隔离。实现资源隔离的核心技术就是 Linux namespace。

隔离意味着可以抽象出多个轻量级的内核（容器进程），这些进程可以充分利用宿主机的资源，宿主机有的资源容器进程都可以享有，但彼此之间是隔离的，同样，不同容器进程之间使用资源也是隔离的，这样，彼此之间进行相同的操作，都不会互相干扰，安全性得到保障。

为了支持这些特性，Linux namespace 实现了 6 项资源隔离，基本上涵盖了一个小型操作系统的运行要素，包括主机名、用户权限、文件系统、网络、进程号、进程间通信。

| 名称    | 宏定义        | 隔离内容                                                                                                                                                    | 发布版本                                           |
| ------- | ------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------- |
| IPC     | CLONE_NEWIPC  | System V IPC, POSIX message queues                                                                                                                          | since Linux 2.6.19                                 |
| Network | CLONE_NEWNET  | network device interfaces, IPv4 and IPv6 protocol stacks, IP routing tables, firewall rules, the /proc/net and /sys/class/net directory trees, sockets, etc | since Linux 2.6.24                                 |
| Mount   | CLONE_NEWNS   | Mount points                                                                                                                                                | since Linux 2.4.19                                 |
| PID     | CLONE_NEWPID  | Process IDs                                                                                                                                                 | since Linux 2.6.24                                 |
| User    | CLONE_NEWUSER | User and group IDs                                                                                                                                          | started in Linux 2.6.23 and completed in Linux 3.8 |
| UTS     | CLONE_NEWUTS  | Hostname and NIS domain name                                                                                                                                | since Linux 2.6.19                                 |

这 6 项资源隔离分别对应 6 种系统调用，通过传入上表中的参数，调用 clone() 函数来完成。

```
int clone(int (*child_func)(void *), void *child_stack, int flags, void *arg);
```

clone() 函数相信大家都不陌生了，它是 fork() 函数更通用的实现方式，通过调用 clone()，并传入需要隔离资源对应的参数，就可以建立一个容器了（隔离什么我们自己控制）。

一个容器进程也可以再 clone() 出一个容器进程，这是容器的嵌套。

## Cgroups

## rootfs



## docker  architecture

Docker 使用client-server 架构。Docker客户段与Docker守护进程通信，后者负责构建、运行和分发Docker容器的工作。

![docker architecture](../assets/images/docker architecture.svg)



### Docker daemon



### Docker client



### Docker registries



# Configure

## Daemon

### data directory

By default this directory is:

- `/var/lib/docker` on Linux.
- `C:\ProgramData\docker` on Windows.

```bash
{
  "data-root": "/mnt/docker-data"
}
```

### HTTP/HTTPS proxy

1. 创建目录 `mkdir -p /etc/systemd/system/docker.service.d`

2. 创建配置文件 `/etc/systemd/system/docker.service.d/http-proxy.conf`

   ```bash
   [Service]
   Environment="HTTP_PROXY=http://proxy.example.com:80"
   Environment="HTTPS_PROXY=https://proxy.example.com:443"
   Environment="NO_PROXY=localhost,127.0.0.1,docker-registry.example.com,.corp"
   ```

3. Flush changes and restart Docker

   ```bash
   systemctl daemon-reload
   systemctl restart docker
   ```

4. Verify that the configuration has been loaded and matches the changes you made, for example:

   ```bash
   systemctl show --property=Environment docker
   ```



## Client

### Proxy

配置 `~/.docker/config.json` 文件

```json
{
 "proxies":
 {
   "default":
   {
     "httpProxy": "http://192.168.1.12:3128",
     "httpsProxy": "http://192.168.1.12:3128",
     "noProxy": "*.test.example.com,.example2.com,127.0.0.0/8"
   }
 }
}
```





# CLI

## Romote

### ssh

```bash
# 创建一个使用ssh连接远程服务器的配置
docker context create \                                                                                                                                                                                                                                                   
    --docker host=ssh://root@192.168.1.94 \
    --description="Work engine" \
    work
    
# 切换cli使用work
docker context use work 

# 切换会本地的配置
docker context use default
```



## 参数

```bash
-m 限制内存
```

### 删除无用的docker实例及镜像

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
# 删除指定 TAG 的镜像
docker images --format "{{.ID}} {{.Tag}}" | grep v1.10.10 | awk '{print $1}' | xargs docker rmi

# 根据 docker name 清理 images
docker images | grep none | awk '{print $3}' | xargs docker rmi -f
```

## run

### Mount volumes from container (--volumes-from)

```bash
docker run --volumes-from 777f7dc92da7 --volumes-from ba8c0c54f0f2:ro -i -t ubuntu pwd
```

`--volumes-from`标志从引用的容器中装入所有定义的卷。容器可以通过重复 `--volumes-from`参数来指定。容器ID可以添加 `:ro`或 `:rw`后缀，分别用于以只读或读写方式挂载卷。默认情况下，卷以与引用容器相同的模式(读、写或只读)挂载。

像SELinux这样的标签系统要求在装入容器的卷内容上放置适当的标签。如果没有标签，安全系统可能会阻止容器内运行的进程使用内容。默认情况下，Docker不会改变操作系统设置的标签。

要在容器上下文中更改标签，可以添加两个后缀中的任何一个 `:z`或 `:Z`到卷安装。这些后缀会告诉Docker以重新标记共享卷上的文件对象。`z`选项告诉 `Docker`，两个容器共享卷内容。因此，Docker将内容标记为共享内容标签。共享卷标签允许所有容器读/写内容。`Z`选项告诉Docker用私有未共享标签标记内容。只有当前容器可以使用私人卷。

## save



## load



## ps

```bash
$ docker ps # Lists only running containers
$ docker ps -a  # Lists all containers
$ docker ps --size  # 大小
```

## network

管理网络

```bash
docker network ls    # 列出当前的网络
```

## Committing (saving) a container state

```base
# Commit your container to a new named image
$ docker commit <container> <some_name>
```



## tip

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






# 生产环境

## Volume

[Use volumes](https://docs.docker.com/storage/volumes/)

卷是用于持久化由Docker容器生成和使用的数据的首选机制。 尽管绑定挂载取决于主机的目录结构，但是卷完全由Docker管理。 与绑定安装相比，卷具有几个优点：

* 与绑定安装相比，卷更易于备份或迁移。
* 您可以使用Docker CLI命令或Docker API管理卷。
* 卷在Linux和Windows容器上均可工作。
* 可以在多个容器之间更安全地共享卷。
* 卷驱动程序使您可以将卷存储在远程主机或云提供商上，以加密卷内容或添加其他功能。
* 新卷的内容可以由容器预先填充。

![types-of-mounts-volume.png](../assets/images/types-of-mounts-volume.png)

```bash
# create volume
docker volume create my-vol
# 列出 volume
docker volume ls
# inspect volumen, 显示详细信息
docker volume inspect my-vol
# 删除一个volume
docker volume rm my-vol
# list volumes in docker containers
docker ps -a --format '{{ .ID }}' | xargs -I {} docker inspect -f '{{ .Name }}{{ printf "\n" }}{{ range .Mounts }}{{ printf "\n\t" }}{{ .Type }} {{ if eq .Type "bind" }}{{ .Source }}{{ end }}{{ .Name }} => {{ .Destination }}{{ end }}{{ printf "\n" }}' {}
```

### 备份、恢复、迁移数据卷

以 nexus3 的数据卷备份和还原来说明如何备份和还原卷。

```yml
version: "3"

services:
  nexus:
    restart: always
    container_name: nexus
    image: sonatype/nexus3
    volumes:
      - nexus-data:/nexus-data
    ports:
      - "8081:8081"

volumes:
  nexus-data:
```

#### Backup

```bash
docker run --rm --volumes-from nexus -v $(pwd):/backup alpine tar cvf /backup/backup.tar /nexus-data

# 使用时间输出备份文件名
docker run --rm --volumes-from nexus -v $(pwd):/backup alpine tar cvf /backup/nexus-$(date +"%Y%m%d%H%M").tar /nexus-data
```

#### Restore

```bash
docker run --rm --volumes-from nexus -v $(pwd):/backup alpine sh -c "cd /nexus-data && tar xvf /backup/backup.tar --strip 1"
```

### 在macos上

在macos上，`/var/lib/docker/volumes` 是没有的，需要以以下方式进入虚拟机查看

```bash
# 进入虚拟机的命令
screen ~/Library/Containers/com.docker.docker/Data/vms/0/tty
```

`ctrl a` + `d` to detach the screen

`screen -dr` to re-attach the screen again

# Dockerfile

1. Docker从基础镜像运行一个容器
2. 执行一条指令，对容器修改
3. 执行类似docker commit的操作，提交一个新的镜像
4. Docker再基于刚提交的镜像运行一个新容器
5. 执行Dockerfile中的下一条指令，知道所有指令都执行完成

> docker build 执行时, Dockerfile 中的所有指令都被执行并且提交，并且在该命令成功结束后返回一个新镜像。



## Docker Build 工作流程

因为命令行“docker”是一个简单的客户端，真正的镜像构建工作是由服务器端的“Docker daemon”来完成的，所以“docker”客户端就只能把“构建上下文”目录打包上传（显示信息 Sending build context to Docker daemon ），这样服务器才能够获取本地的这些文件。但这个机制也会导致一些麻烦，如果目录里有的文件（例如 readme/.git/.svn 等）不需要拷贝进镜像，docker 也会打包上传，效率很低。为了避免这种问题，你可以在“构建上下文”目录里再建立一个 .dockerignore 文件，语法与 .gitignore 类似，排除那些不需要的文件。

`-f` 指定用来构建的`Dockerfile`文件名称，如果省略，docker build 就会在当前目录下找名字是 Dockerfile 的文件


### BuildKit
在构建镜像时，通过设置 DOCKER_BUILDKIT=1，可以启用 **BuildKit**

1. 并行构建：
  * BuildKit 可以并行处理多个构建步骤，而传统的 Docker 构建是线性的。这提高了构建速度，特别是在多阶段构建中。
2. 缓存优化：
  * BuildKit 使用更高效的缓存机制，减少重复工作。例如，即使 Dockerfile 中的某些步骤发生了变化，它仍然可以重用未改变步骤的缓存。
3. 安全性增强：
	* 支持构建时使用密钥（Secrets），例如用来访问私有仓库的密钥：
      ```bash
      # syntax=docker/dockerfile:1.4
      RUN --mount=type=secret,id=mysecret cat /run/secrets/mysecret
      ```
4. 更好的调试和日志：
  * 构建日志更加清晰，支持按步骤查看构建输出。
5. 支持更多语法扩展：
  * 启用 BuildKit 后，可以使用 # syntax 指令选择特定的 Dockerfile 前缀语法扩展。
    ```bash
      # syntax=docker/dockerfile:1.4
    ```



| DOCKER_BUILDKIT=0                                            | DOCKER_BUILDKIT=1                                            |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| **关闭 BuildKit**，使用传统的 Docker 构建方式。              | **启用 BuildKit**，使用新的构建引擎。                        |
| 构建是 **线性顺序执行**，每个步骤依次运行。                  | 支持 **并行构建**：非依赖关系的步骤可以同时运行。            |
| 性能较低，特别是在多阶段构建或重复构建中。                   | 更高效的缓存机制，减少重复工作，加快构建速度。               |
| 不支持 BuildKit 提供的高级功能，例如 Secrets、SSH 转发和新的语法扩展。 | 高级功能支持：<br/>	• Secrets：用于安全地传递构建时需要的敏感信息（例如 API 密钥）。<br/>	• SSH 转发：用于拉取私有 Git 仓库。<br/>	• 更丰富的语法扩展：通过 # syntax 指令支持最新的 Dockerfile 功能。 |
| 构建功能相对简单，适合基本需求。                             | 提供更好的日志和调试信息。                                   |
| 输出较为简单，每个步骤的日志直接打印到终端。                 | 日志更有层次感，按构建步骤分组。                             |
| 当日志量较大时，可能显得杂乱。                               | 默认只显示关键输出，可通过 --progress=plain 显示详细日志。   |
| 不支持安全地处理敏感信息，构建时传递的环境变量可能会暴露。   | 支持 **Secrets 和挂载**，可安全传递敏感信息而不将其写入镜像中。 |

> 在构建过程中，如果需要输出详细构建日志可以使用 `DOCKER_BUILDKIT=0 docker build -t bone`



## Dockerfile指令

### FROM

```bash
FROM [--platform=<platform>] <image>[@<digest>] [AS <name>]
```

### ARG

ARG指令定义了一个变量，用户可以使用`-build-arg <varname> = <value>` flag在`docker build`命令中将用户定义的值传递给构建器。如果用户指定dockerfile中未定义的构建参数，则会输出警告。

```bash
ARG <name>[=<default value>]
```

ARG 和 ENV 的区别在于 **ARG** 创建的变量只在镜像构建过程中可见，容器运行时不可见，而 **ENV** 创建的变量不仅能够在构建镜像的过程中使用，在容器运行时也能够以环境变量的形式被应用程序使用。

和 `ENV` 的效果一样，都是设置环境变量。所不同的是，`ARG` 所设置的构建环境的环境变量，在将来容器运行时是不会存在这些环境变量的。但是不要因此就使用 `ARG` 保存密码之类的信息，因为 `docker history` 还是可以看到所有值的。



### EXPOSE

`EXPOSE` 指令是声明容器运行时提供服务的端口，这只是一个声明，在容器运行时并不会因为这个声明应用就会开启这个端口的服务。在 Dockerfile 中写入这样的声明有两个好处，一个是帮助镜像使用者理解这个镜像服务的守护端口，以方便配置映射；另一个用处则是在运行时使用随机端口映射时，也就是 `docker run -P` 时，会自动随机映射 `EXPOSE` 的端口。

要将 `EXPOSE` 和在运行时使用 `-p <宿主端口>:<容器端口>` 区分开来。`-p`，是映射宿主端口和容器端口，换句话说，就是将容器的对应端口服务公开给外界访问，而 `EXPOSE` 仅仅是声明容器打算使用什么端口而已，并不会自动在宿主进行端口映射。





# Docker-Compose

Compose是一个用于定义和运行多容器Docker应用程序的工具。

## 环境变量

### COMPOSE_PROJECT_NAME

设置项目名称。在启动时，此值连同服务名称一起组成容器名称。

```bash
# 同样的功能
COMPOSE_PROJECT_NAME=zk_test docker-compose up
docker-compose -p zk_test up
```

## build

```
--compress              Compress the build context using gzip.
--force-rm              Always remove intermediate containers.
--no-cache              Do not use cache when building the image.
--pull                  Always attempt to pull a newer version of the image.
-m, --memory MEM        Sets memory limit for the build container.
--build-arg key=val     Set build-time variables for services.
--parallel              Build images in parallel.
```

## bundle

```
--push-images              Automatically push images for any services
													 which have a `build` option specified.

-o, --output PATH          Path to write the bundle file to.
                           Defaults to "<project name>.dab".
```

## up

构建、(重新)创建、启动和附加到服务的容器

# 实战

## 在一台主机上测试Consul集群

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

## ubuntu 上安装 Java8

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

## ubuntu安装tomcat8

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

## FTP

```bash
docker run -d -v /Users/warriorg/Downloads:/home/vsftpd -p 20:20 -p 21:21 \
-p 47400-47470:47400-47470 \
-e FTP_USER=test \
-e FTP_PASS=test \
-e PASV_ADDRESS=0.0.0.0 \
--name ftp bogem/ftp
```

# 常见问题

## Layer already being pulled by another client. Waiting.

```bash
$ docker-machine stop default
$ docker images -q | xargs docker rmi
$ docker-machine start default
```

## Cannot connect to the Docker daemon. Is the docker daemon running on this host?

> *重启*

在命令行直接启动

> ` /Applications/Docker/Docker\ Quickstart\ Terminal.app/Contents/Resources/Scripts/start.sh`

## 改变docker存储位置

You can change Docker's storage base directory (where container and images go) using the -g option when starting the Docker daemon.

* Ubuntu/Debian: edit your /etc/default/docker file with the -g option: DOCKER_OPTS="-dns 8.8.8.8 -dns 8.8.4.4 -g /mnt"
* Fedora/Centos: edit /etc/sysconfig/docker, and add the -g option in the other_args variable: ex. other_args="-g /var/lib/testdir". If there's more than one option, make sure you enclose them in " ". After a restart, (service docker restart) Docker should use the new directory.

Using a symlink is another method to change image storage.

*Caution - These steps depend on your current /var/lib/docker being an actual directory (not a symlink to another location).*

1. Stop docker: `systemctl stop docker`. Verify no docker process is running ps faux
2. Double check docker really isn't running. Take a look at the current docker directory: `ls /var/lib/docker/`
3. Make a backup - `tar -zcC /var/lib docker > /mnt/pd0/var_lib_docker-backup-$(date +%s).tar.gz`
4. Move the /var/lib/docker directory to your new partition: `mv /var/lib/docker /mnt/data/docker`
5. Make a symlink: `ln -s /mnt/data/docker /var/lib/docker`
6. Take a peek at the directory structure to make sure it looks like it did before the mv: `ls /var/lib/docker/` (note the trailing slash to resolve the symlink)
7. Start docker back up `systemctl start docker`
8. restart your container

# 参考

[Kubernetes](kubernetes.md)
