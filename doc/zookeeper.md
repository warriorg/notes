##zookeeper 简介

ZooKeeper 是一个开源的分布式应用程序协调服务器，其为分布式系统提供一致性服务。
其一致性是通过基于 Paxos 算法的 ZAB 协议完成的。其主要功能包括:配置维护、域名服务、
分布式同步、集群管理等。

### 重要概念

#### Session

Session 是指客户端会话。ZooKeeper 对外的服务端口默认是 2181，客户端启动时，首 先会与 zk 服务器建立一个 TCP 长连接，从第一次连接建立开始，客户端会话的生命周期也 开始了。通过这个长连接，客户端能够通过心跳检测保持与服务器的有效会话，也能够向 ZooKeeper 服务器发送请求并接受响应，同时还能通过该连接接收来自服务器的 Watcher 事 件通知。 

Session 的 SessionTimeout 值用来设置一个客户端会话的超时时间。当由于服务器压力 太大、网络故障或是客户端主动断开连接等各种原因导致客户端连接断开时，只要在 Session Timeout 规定的时间内客户端能够重新连接上集群中任意一台服务器，那么之前创建的会话仍然有效。

#### znode

ookeeper 的文件系统采用树形层次化的目录结构，与 Unix 文件系统非常相似。每个目录在 zookeeper 中叫做一个 znode，每个 znode 拥有一个唯一的路径标识，即名称。Znode可以包含数据和子 znode(临时节点不能有子 znode)。Znode 中的数据可以有多个版本，所以查询某路径下的数据需带上版本号。客户端应用可以在 znode 上设置监视器(Watcher)。

#### Watcher 机制

zk 通过 Watcher 机制实现了发布订阅模式。zk 提供了分布式数据的发布订阅功能，一个发布者能够让多个订阅者同时监听某一主题对象，当这个主题对象状态发生变化时，会通知所有订阅者，使它们能够做出相应的处理。zk 引入了 watcher 机制来实现这种分布式的通知功能。zk 允许客户端向服务端注册一个 Watcher 监听，当服务端的一些指定事件触发这个Watcher，那么就会向指定客户端发送一个事件通知。而这个事件通知则是通过 TCP 长连接的 Session 完成的。

#### ACL

ACL 全称为 Access Control List(访问控制列表)，用于控制资源的访问权限，是 zk 数据安全的保障。zk 利用 ACL 策略控制 znode 节点的访问权限，如节点数据读写、节点创建、节点删除、读取子节点列表、设置节点权限等。



## Paxos 算法

## ZAB 协议

## Install

### 配置


| 名称  | IP           | 角色     |
| ----- | ------------ | -------- |
| ZK-20 | 192.168.2.20 |          |
| ZK-21 | 192.168.2.21 |          |
| ZK-22 | 192.168.2.22 |          |
| ZK-23 | 192.168.2.23 | observer |

### 安装

```bash
hostnamectl set-hostname ZK-20
yum -y install java-1.8.0-openjdk
wget https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/stable/zookeeper-3.4.13.tar.gz
tar -xvf zookeeper-3.4.13.tar.gz
mv zookeeper-3.4.13 /usr/local/
cd /usr/local/zookeeper-3.4.13/
cd conf
cp zoo_sample.cfg zoo.cfg
mkdir -p /usr/data/zookeeper
vim zoo.cfg
```

**zoo.cfg**

```bash
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/usr/data/zookeeper
clientPort=2181
# 集群配置
server.1=192.168.2.20:2888:3888
server.2=192.168.2.21:2888:3888
server.3=192.168.2.22:2888:3888
server.4=192.168.2.23:2888:3888:observer
```

在/usr/data/zookeeper 目录中创建表示当前主机编号的 myid 文件。该主机编号要与 zoo.cfg 文件中设置的编号一致。 

```bash
echo 1 > /usr/data/zookeeper/myid
```

observer 的配置

```bash
tickTime=2000
initLimit=10
syncLimit=5
dataDir=/usr/data/zookeeper
clientPort=2181
# 集群配置
peerType=observer
server.1=192.168.2.20:2888:3888
server.2=192.168.2.21:2888:3888
server.3=192.168.2.22:2888:3888
server.4=192.168.2.23:2888:3888:observer
```

### 设置防火墙

```bash
firewall-cmd --zone=public --add-port=2888/tcp --permanent
firewall-cmd --zone=public --add-port=3888/tcp --permanent
firewall-cmd --zone=public --add-port=2181/tcp --permanent
firewall-cmd --reload   # 使配置生效
firewall-cmd --list-all  # 检查配置
```

### Test

```bash
./zkServer.sh start
./zkServer.sh status
```

### zkCli

```bash
zkCli.sh -server 127.0.0.1:2181 								# 连接服务器
ls /              															# 查看当前节点数据
ls2 /             															# 查看当前节点数据并能看到更新次数等数据
ls /dubbo/me.warriorg.service.CategoryService/consumers		# 查看dubbo的消费之
create /config "test" 													# 创建一个新的节点并设置关联值
create /config “”     													# 创建一个新的空节点
get /brokers      															# 获取节点内容
set /zk "zkbak"   															# 对 zk 所关联的字符串进行设置
delete /brokers  																# 删除节点
rmr    /brokers  																# 删除节点及子节点
```

