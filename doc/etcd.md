## 简介

etcd 是一个分布式键值对存储，设计用来可靠而快速的保存关键数据并提供访问。通过分布式锁，leader选举和写屏障(write barriers)来实现可靠的分布式协作。etcd集群是为高可用，持久性数据存储和检索而准备。

"etcd"这个名字源于两个想法，即 unix "/etc" 文件夹和分布式系统"d"istibuted。 "/etc" 文件夹为单个系统存储配置数据的地方，而 etcd 存储大规模分布式系统的配置信息。因此，"d"istibuted 的 "/etc" ，是为 "etcd"。

etcd 以一致和容错的方式存储元数据。分布式系统使用 etcd 作为一致性键值存储，用于配置管理，服务发现和协调分布式工作。使用 etcd 的通用分布式模式包括领导选举，分布式锁和监控机器活动。

## 安装





## 快速开始

### 列出所有的KEY

```bash
etcdctl get --prefix "" --keys-only  
etcdctl get --prefix aaa --keys-only  	# 列出前缀aaa开始的key
```

### 删库跑路

```bash
etcdctl del --prefix ""
```





