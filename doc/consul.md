

## 理论基础

### Raft 

#### 算法简介
[Raft 一致性算法论文译文](https://www.infoq.cn/article/raft-paper)

Diego Ongaro和 John Ousterhout 大神的论文原文
[In Search of an Understandable Consensus Algorithm (Extended Version)](https://ramcloud.atlassian.net/wiki/download/attachments/6586375/raft.pdf)







###Consul优点

1.   使用 Raft 算法来保证一致性, 比复杂的 Paxos 算法更直接. 相比较而言, zookeeper 采用的是 Paxos, 而 etcd 使用的则是 Raft.
2. 支持多数据中心，内外网的服务采用不同的端口进行监听,多数据中心集群可以避免单数据中心的单点故障,而其部署则需要考虑网络延迟, 分片等情况等. zookeeper 和 etcd 均不提供多数据中心功能的支持.
3. 支持健康检查. etcd 不直接提供此功能(利用etcd使用创建节点的ttl机制可以实现健康检测).
4. 支持 http 和 dns 协议接口. zookeeper 的集成较为复杂, etcd 只支持 http 协议.
5. 官方提供web管理界面, etcd 无此功能.

启动一个接点的测试服务器
`docker run -d -p 8400:8400 -p 8500:8500 -p 53:53/udp -h consul-server-node --name consul progrium/consul -server -bootstrap`
windows 下启动一个测试接点
`consul.exe agent -data-dir=consul-data -client=192.168.17.108 -bootst
rap-expect 1 -server -ui`

###consul启动参数理解

>Agent 
>
>>运行在consul集群的每个node上的daemon，agent可以run在server或者是client上，每个agent都可以提供DNS或者是HTTP的接口，负责health check和service发现等。用consul agent命令可以启动，具体可run "consul agent --help"查看参数。

>Client
>
>>client把所有的RPCs转发到server端，是相对无状态的。唯一在后台运行的时client端执行了LAN gossip pool，只消耗极少的资源和网络带宽。   

>Server
>
>>server 负责维护cluster state， RPC quiries， 和其他数据中心交换WAN gossip，转发queries给leader或者其他数据中心

>-bootstrap
>
>>用来控制一个server是否在bootstrap模式，在一个datacenter中只能有一个server处于bootstrap模式，当一个server处于bootstrap模式时，可以自己选举为raft leader.

>-bootstrap-expect
>
>>在一个datacenter中期望提供的server节点数目，当该值提供的时候，consul一直等到达到指定sever数目的时候才会引导整个集群，该标记不能和bootstrap公用,一般要是单机consule的话直接指定 -bootstrap-expect 1 默认就是leader.

>-config-file
>
>>明确的指定要加载哪个配置文件.

>-config-dir
>
>>配置文件目录，里面所有以.json结尾的文件都会被加载.

>-data-dir
>
>>提供一个目录用来存放agent的状态，所有的agent允许都需要该目录，该目录必须是稳定的，系统重启后都继续存在.
```

###参考文档
[SERVICE DISCOVERY FOR DOCKER CONTAINERS USING CONSUL AND REGISTRATOR](https://www.livewyer.com/blog/2015/02/05/service-discovery-docker-containers-using-consul-and-registrator)
```

## 安装

## 使用

### consul

```bash
consul
Usage: consul [--version] [--help] <command> [<args>]

Available commands are:
    acl            Interact with Consul's ACLs
    agent          Runs a Consul agent
    catalog        Interact with the catalog
    config         Interact with Consul's Centralized Configurations
    connect        Interact with Consul Connect
    debug          Records a debugging archive for operators
    event          Fire a new event
    exec           Executes a command on Consul nodes
    force-leave    Forces a member of the cluster to enter the "left" state
    info           Provides debugging information for operators.
    intention      Interact with Connect service intentions
    join           Tell Consul agent to join cluster
    keygen         Generates a new encryption key
    keyring        Manages gossip layer encryption keys
    kv             Interact with the key-value store
    leave          Gracefully leaves the Consul cluster and shuts down
    lock           Execute a command holding a lock
    login          Login to Consul using an auth method
    logout         Destroy a Consul token created with login
    maint          Controls node or service maintenance mode
    members        Lists the members of a Consul cluster
    monitor        Stream logs from a Consul agent
    operator       Provides cluster-level tools for Consul operators
    reload         Triggers the agent to reload configuration files
    rtt            Estimates network round trip time between nodes
    services       Interact with services
    snapshot       Saves, restores and inspects snapshots of Consul server state
    tls            Builtin helpers for creating CAs and certificates
    validate       Validate config files/directories
    version        Prints the Consul version
    watch          Watch for changes in Consul
```

### 常用命令

```bash
consul agent -dev  #  Starts the agent in development mode.
consul members 		# Lists the members of a Consul cluster
curl localhost:8500/v1/catalog/nodes   # 
dig @127.0.0.1 -p 8600 Armons-MacBook-Air.node.consul

# 启动server 
consul agent -server -data-dir dest  -bootstrap -client 0.0.0.0 -ui
```





## consul-template