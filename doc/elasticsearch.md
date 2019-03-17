[官网](https://www.elastic.co/)

## 安装

| 名称  | IP 地址      |
| ----- | ------------ |
| es-12 | 192.168.2.12 |
| es-13 | 192.168.2.13 |
| es-14 | 192.168.2.14 |

编辑 `vim /etc/security/limits.conf`

```bash
* soft nofile 65536
* hard nofile 65536
# 解决启动时的错误
# [1]: max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536]
```

更改后需要重新登录用户

编辑 `vim /etc/sysctl.conf`

```bash
vm.max_map_count=655360
# 解决启动时的错误
# [2]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
```

 执行命令 `sysctl -p`

开始安装

```bash
yum -y install java-1.8.0-openjdk
# 安装 elasticsearch-head 插件时需要
yum -y install nodejs 
# elasticsearch 不能使用root用户运行 
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.6.2.tar.gz
tar -xvf elasticsearch-6.6.2.tar.gz
mv elasticsearch-6.6.2 /opt/es
adduser warriorg
passwd warriorg
cd /opt
chown -R warriorg es				# 赋权限
su warriorg
./elasticsearch -d 					# 运行 elasticsearch,只能本机访问
# 查看启动的进程
ps -aux | grep elasticsearch
```

修改配置文件  elasticsearch.yml

```bash
cluster.name: es-test
node.name: node-12
network.host: 0.0.0.0
```

配置防火墙

```bash
firewall-cmd --zone=public --add-port=9200/tcp --permanent
firewall-cmd --zone=public --add-port=9300/tcp --permanent
firewall-cmd --reload   # 使配置生效
firewall-cmd --list-all  # 检查配置
```

安装[elasticsearch-head](http://mobz.github.io/elasticsearch-head)插件,  **当然也可以chrome的插件,感觉效果更好!!**
```bash
cd /opt
git clone git://github.com/mobz/elasticsearch-head.git
cd elasticsearch-head
npm install
npm run start
```
==es-12== elasticsearch.yaml 

```bash
cluster.name: es-test
node.name: node-12
node.master: true
network.host: 0.0.0.0
http.port: 9200
discovery.zen.ping.unicast.hosts: ["192.168.2.12:9300", "192.168.2.13:9300", "192.168.2.14:9300"]
discovery.zen.minimum_master_nodes: 2
http.cors.enabled: true
http.cors.allow-origin: "*"
```
==es-13== elasticsearch.yaml 

```bash
cluster.name: es-test
node.name: node-13
node.master: true
network.host: 0.0.0.0
http.port: 9200
discovery.zen.ping.unicast.hosts: ["192.168.2.12:9300", "192.168.2.13:9300", "192.168.2.14:9300"]
discovery.zen.minimum_master_nodes: 2
http.cors.enabled: true
http.cors.allow-origin: "*"
```
==es-14== elasticsearch.yaml 

```bash
cluster.name: es-test
node.name: node-14
node.master: true
network.host: 0.0.0.0
http.port: 9200
discovery.zen.ping.unicast.hosts: ["192.168.2.12:9300", "192.168.2.13:9300", "192.168.2.14:9300"]
discovery.zen.minimum_master_nodes: 2
http.cors.enabled: true
http.cors.allow-origin: "*"
```

### 集群健康

`status` 字段指示着当前集群在总体上是否工作正常。它的三种颜色含义如下：

- **green **所有的主分片和副本分片都正常运行。
- **yellow **所有的主分片都正常运行，但不是所有的副本分片都正常运行。
- **red** 有主分片没能正常运行。

#### 集群信息查看

| uri                               | method | 描述                       |
| --------------------------------- | ------ | -------------------------- |
| /_cat/                            | GET    | 集群信息查询列表           |
| /_cat/health?v                    | GET    | 集群状态                   |
| /_nodes/stats/process?pretty      | GET    | node状态信息               |
| /_cat/nodes?v                     | GET    | 查看每个node的机器使用情况 |
| /_cat/indices?v                   | GET    | 查看集群的索引信息         |
| /_cat/indices?v&health=yellow     | GET    | 只显示状态为黄色的         |
| /_cat/indices?v&s=docs.count:desc | GET    | 根据文档数降序排序         |
| /_cat/indices?v&h=i,tm&s=tm:desc  | GET    | 显示每个索引占的内存       |
| /_cat/allocation?v                | GET    | node使用的磁盘情况         |
| /_cat/count?v                     | GET    | 查看文档数                 |
| /_cat/fielddata?v                 | GET    | fieldData的大小            |
| /_cat/fielddata?v&fields=test     | GET    | 查看指定的字段             |
| /_cat/master?v                    | GET    | 主节点信息                 |
| /_cat/recovery?v                  | GET    | 分片的恢复                 |

### elasticsearch.yml

```bash
# 默认启动的集群名字叫 elasticsearch 。 你最好给你的生产环境的集群改个名字，改名字的目的很简单， 就是防止某人的笔记本电脑加入了集群这种意外
cluster.name: elasticsearch_production
# 节点名称,每格几点名字不同,默认情况下自动生成,从ES的jar中的config文件下的name.txt中随机取出名字
node.name: node-12
# 是否允许该节点成为master(默认开启); ES启动时使用第一个节点为master,如果挂掉会从允许为master的节点中选举出来一个新的.
node.master: true
node.attr.rack: r1
# 该节点上允许出现的实例数,如果一个节点一个ES服务就不需要,如果需要在单台躲开就要改
node.max_local_storage_nodes=3
path.data: /path/to/data
path.logs: /path/to/logs
bootstrap.memory_lock: true
network.host: 192.168.0.1
http.port: 9200
# Elasticsearch 默认被配置为使用单播发现，以防止节点无意中加入集群。只有在同一台机器上运行的节点才会自动组成集群。
# master节点的初始列表;会探索,但是如果网络不稳定,会出现脑裂,最好加上node的信息
discovery.zen.ping.unicast.hosts: ["host1", "host2"]
# 集群中最小节点数,防止脑裂一般为:节点数/2+1. 例如: 网络中5个节点最小master是2,当网络故障后形成2个集群,通了后又变成一个.数据会严重错乱或无法工作
discovery.zen.minimum_master_nodes: 2
gateway.recover_after_nodes: 3
action.destructive_requires_name: true

# head访问
http.cors.enabled: true
http.cors.allow-origin: "*"
```



## 索引管理

### 字段类型

| 一级分类 | 二级分类 | 具体类型                             | 备注                                                         |
| ------------- | -------- | ------------------------------------ | ------------------------------------------------------------ |
| 核心     | 字符串   | string                               | 新版不再使用                                                 |
|          |          | text                                 | 全文搜索的内容,会模糊匹配,会被拆分然后建立倒序索引,不用于排序,聚合也少用 |
|          |          | keyword                              | 会拆分,适用于排序聚合等,精确匹配                             |
|          | 整数     | integer,long,short,byte              |                                                              |
|          | 浮点     | double,float,half_float,scaled_float |                                                              |
|          | 逻辑     | boolean                              |                                                              |
|          | 日期     | date                                 |                                                              |
|          | 范围     | range                                |                                                              |
|          | 二进制   | binary                               |                                                              |
| 复合     | 数组     | array                                |                                                              |
|          | 对象     | object                               |                                                              |
|          | 嵌套     | nested                               |                                                              |
| 地理     | 地理坐标 | geo_point                            |                                                              |
|          | 地理地图 | geo_shape                            |                                                              |
| 特殊     | IP       | ip                                   |                                                              |
|          | 范围     | completion                           |                                                              |
|          | 令牌计数 | token_count                          |                                                              |
|          | 附件     | attachment                           |                                                              |
|          | 抽取     | percolator                           |                                                              |

### DSL语法

