##Kafka学习
>一个高吞吐量、分布式的发布-订阅消息系统。	

**主题（Topic）** Kafka 将一组消息抽象归纳为一个主题(Topic)， 也就是说，一个主题就是对消息的一个分类。		
**消息** 消息是Kafka通信的基本单位，由一个固定长度的消息头和一个可变长度的消息体构成。		
**分区和副本** Kafka将一组消息归纳为一个主题，而每个主题又被分成一个或多个分区。每个分区由一系列有序、不可变的消息组成，是一个有序队列。
> 每个分区在物理上对应为一个文件夹。分区的命名柜子为 [主题名称]-[分区编号]    
> 每个分区又有一至多个副本(Replica), 分区的副本分布在集群的不同代理上，以提高可用性

**Leader副本和Follower副本** Leader副本负责处理客户端读/写请求，Follower副本从Leader副本同步数据。   
**偏移量** 任何发布到分区的消息会被直接追加到日志文件的尾部，而每条消息在日志文件中的位置都会对应一个按序递增的偏移量。偏移量是一个分区下严格有序的逻辑值、它并不表示消息在磁盘上的物理位置。   
**日志段(LogSegment)** 一个日志又被划分为多个日志段(LogSegment),日志段是Kafka 日志对象分片的最小单位。    
**代理（Broker）** 每一个Kafka实例被称为代理(Broker)	
**生产者（Producer）** 负责将消息发送给代理。   
**消费者（Comsumer）和消费组（ComsumerGroup）** 已拉取（pull）方式拉取数据，它是消费客户端
> 每一个消费者都属于一个特定消费组

**ISR** Kafka在ZooKeeper中动态维护一个ISR(In-sync Replica)，即保存同步的副本列表，该列表中保存的是与Leader副本保持消息同步的所有副本对应的代理节点id。  
**ZooKeeper** 
> Kafka利用ZooKeeper保存相应元数据信息，Kafka元数据信息包括如代理节点信息、Kafka集群信息、旧版消费者信息及其消费偏移量信息、主题信息、分区状态信息、分区副本分配方案信息、动态配置信息等。

![image](images/kafka-cluster-structure.png)

####特性
1. 消息持久化
2. 高吞吐量
3. 扩展性
4. 多客户端支持
5. Kafka Streams
6. 安全机制
7. 数据备份
8. 轻量级
9. 消息压缩

#### 应用场景
1. 消息系统
2. 应用监控
3. 网站用户行为追踪
4. 流处理
5. 持久性日志

Download [kafka](http://kafka.apache.org/)

Start zookeeper server

```bash
$ bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start kafka server

```
$ bin/kafka-server-start.sh config/server.properties
```

Create a topic

```bash
#create a topic named "test" with a single partition and only one replica
 $ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test                                         [14:44:32]
Created topic "test".
#查看创建的topic
 $ bin/kafka-topics.sh --list --zookeeper localhost:2181                                                                                              [14:44:34]
test
```

Send some messages

```bash
 $ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test                                                                            [14:44:51]
this is a message
this is another message
```

Start a consumer

```bash
 $ bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning                                                             
this is a message
this is another message
```

###Java使用kafka

