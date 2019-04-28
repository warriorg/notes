## 技术框架

### [sentry](https://sentry.io)
> 很流行的错误监控工具. 它能很好的监听线上异常以及开发者手动抛出的错误并记录

### NATS

[NATS](http://nats.io/) is an open-source, cloud-native messaging system

###Elasticsearch 

搜索服务器

ELK(ElasticSearch, Logstash, Kibana)日志分析

### [Couchbase](http://www.couchbase.com/)


### [Thrift](https://thrift.apache.org/)
>The Apache Thrift software framework, for scalable cross-language services development, combines a software stack with a code generation engine to build services that work efficiently and seamlessly between C++, Java, Python, PHP, Ruby, Erlang, Perl, Haskell, C#, Cocoa, JavaScript, Node.js, Smalltalk, OCaml and Delphi and other languages.

### [ProtocolBuf](https://developers.google.com/protocol-buffers/)
>Protocol buffers are a language-neutral, platform-neutral extensible mechanism for serializing structured data.

### [grpc](http://www.grpc.io/)
>A high performance, open source, general RPC framework that puts mobile and HTTP/2 first.


### Zookeeper 

>是一个用户维护配置信息、命名、分布式同步以及分组服务的集中式服务框架，它使用Java语言编写，通过Zab协议来保证节点的一致性。因为Zookeeper是一个CP型系统，所以当网络分区问题发生时，系统就不能注册或查找服务。

### Doozer
>是一个一致性的、分布式存储系统，使用Go语言编写，通过Paxos来保证强一致性，Doozer项目目前已经停止更新并有将近160个分支。和Zookeeper一样，Doozer也是一个CP型系统，在网络分区问题发生时，会有同样的问题。

### etcd 
>是一个用于共享配置和服务发现的高可用的键值存储系统，使用Go语言编写，通过Raft来保证一致性，有基于HTTP+JSON的API接口。etcd也是一个强一致性系统，但是etcd似乎支持从non-leaders中读取数据以提高可用性；另外，写操作仍然需要leader的支持，所以在网络分区时，写操作仍可能失败。

## 自动化
[jenkins](https://jenkins-ci.org/) 自动构建工具

## js
[JWT](https://jwt.io/)
>JSON Web Token (JWT) 是一种基于 token 的认证方案。

### Vanilla JS	——世界上最轻量的JavaScript框架

### [matomo](<https://github.com/matomo-org/matomo>)
Matomo是领先的Free / Libre开放式分析平台

## golang
[Martini](https://github.com/go-martini/martini)
>Martini is a powerful package for quickly writing modular web applications/services in Golang.

[Negroni](https://github.com/codegangsta/negroni)
>Negroni is an idiomatic approach to web middleware in Go. It is tiny, non-intrusive, and encourages use of `net/http` Handlers.

>If you like the idea of [Martini](https://github.com/codegangsta/negroni), but you think it contains too much magic, then Negroni is a great fit.


#网站
SSH [沃通](https://www.wosign.com/)

#mac
[Karabiner](https://pqrs.org/osx/karabiner/)
>*键盘映射工具*

=====

###播放器
[VLC](http://www.videolan.org/vlc/index.html)

###电子书
[calibre](http://calibre-ebook.com/)
> 处理成kindle可以阅读的书

###项目管理工具
[trello](https://trello.com/)
>多人协作工具

[zeplin](https://zeplin.io/)
>前端与设计协同工作专用工具
>

图标管理工具

Iconjar

Lingo

###网盘
>seafile





