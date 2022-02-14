---
title: nacos
---
# 简介

Nacos 官网 https://nacos.io/

Nacos 的关键特性包括:

* 服务发现和服务健康监测
* 动态配置服务
* 动态 DNS 服务
* 服务及其元数据管理
* ...

## [Raft 算法](./DSA.md#Raft)

Nacos Discovery集群为了保证集群中数据的一致性，其采用了Raft算法。这是一种通过对日志进行复制管理来达到一致性的算法。Raft通过选举Leader并由Leader节点负责管理日志复制来实现各个节点间数据的一致性。

Raft算法不是强一致性算法，时最终一致性算法。

#### Nacos 与 [CAP](./理论.md#CAP) 
>>>>>>> d8282af (study cap)

ZK 默认是 CP, Eureka 是 AP，Nacos 同时支持 AP 与 CP。

默认情况下，Nacos Discovery集群是 AP 的。但其也支持 CP 模式，需要进行转换。如果要转换为 CP 的。可以提交如下的 PUT 亲求，完成 AP 到 CP 的转换。

```bash
curl -X PUT '127.0.0.1:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP'
```

# Install

Nacos支持三种部署模式

* 单机模式 - 用于测试和单机试用。
* 集群模式 - 用于生产环境，确保高可用。
* 多集群模式 - 用于多数据中心场景。

## 环境准备

* 64 bit OS Linux [设置IP地址](./linux.md#ip)
* 64 bit JDK 1.8+
* [安装Mysql 8.0](./mysql.md#Install)
* 3个或3个以上Nacos节点才能构成集群

## 下载安装文件

`https://github.com/alibaba/nacos/releases`

## 伪集群安装

### 配置 nacos 目录下 conf 中的 cluster.conf

```bash
192.168.157.129:8848
192.168.157.129:8858
192.168.157.129:8868
```



## 启动服务器

### Stand-alone mode

```bash
sh startup.sh -m standalone
```

### 集群模式

```bash
sh startup.sh
``` 

## 服务注册&发现和配置管理

### 服务注册

```bash
curl -X PUT 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
```

### 服务发现

```bash
curl -X GET 'http://127.0.0.1:8848/nacos/v1/ns/instance/list?serviceName=nacos.naming.serviceName'
```

### 发布配置

```bash
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=helloWorld"
```

### 获取配置

```bash
curl -X GET "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test"
```

