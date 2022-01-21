# Docker-Compose

Compose是一个用于定义和运行多容器Docker应用程序的工具。

## 编写 Docker Compose 模板文件
在使用 Docker Compose 启动容器时， Docker Compose 会默认使用 docker-compose.yml 文件， docker-compose.yml 文件的格式为 yaml。

Docker Compose 文件主要分为三部分：services（服务）、networks（网络）和 volumes（数据卷）。

* services（服务）：服务定义了容器启动的各项配置，就像我们执行`docker run`命令时传递的容器启动的参数一样，指定了容器应该如何启动，例如容器的启动参数，容器的镜像和环境变量等。
* networks（网络）：网络定义了容器的网络配置，就像我们执行`docker network create`命令创建网络配置一样。
* volumes（数据卷）：数据卷定义了容器的卷配置，就像我们执行`docker volume create`命令创建数据卷一样。

### services

### volumes

### networks



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

