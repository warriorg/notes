##Kafka学习

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

