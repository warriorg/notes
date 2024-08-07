# Sentinel

高可用护航的利器

[Sentinel](https://github.com/alibaba/Sentinel?spm=a2ck6.20206201.0.0.3c4a1fd6CKgAvf) 是阿里巴巴开源的，面向分布式服务架构的高可用防护组件，主要以流量为切入点，从流量控制、流量整形、熔断降级、系统自适应保护、热点防护等多个维度来帮助开发者保障微服务的稳定性。Sentinel 承接了阿里巴巴近 10 年的双十一大促流量的核心场景，例如秒杀、冷启动、消息削峰填谷、自适应流量控制、实时熔断下游不可用服务等，是保障微服务高可用的利器，原生支持 Java/Go/C++ 等多种语言，并且提供 Istio/Envoy 全局流控支持来为 Service Mesh 提供高可用防护的能力。

Sentinel 的技术亮点：

- 高度可扩展能力：基础核心 + SPI 接口扩展能力，用户可以方便地扩展流控、通信、监控等功能
- 多样化的流量控制策略（资源粒度、调用关系、流控指标、流控效果等多个维度），提供分布式集群流控的能力
- 热点流量探测和防护
- 对不稳定服务进行熔断降级和隔离
- 全局维度的系统负载自适应保护，根据系统水位实时调节流量
- 覆盖 API Gateway 场景，为 Spring Cloud Gateway、Zuul 提供网关流量控制的能力
- 实时监控和规则动态配置管理能力

![sentinel-arch.png](../assets/images/sentinel-arch.png)



## 控制台

Sentinel 提供一个轻量级的开源控制台，它提供机器发现以及健康情况管理、监控（单机和集群），规则管理和推送的功能。

Sentinel 控制台包含如下功能:

- **查看机器列表以及健康情况**：收集 Sentinel 客户端发送的心跳包，用于判断机器是否在线。
- **监控 (单机和集群聚合)**：通过 Sentinel 客户端暴露的监控 API，定期拉取并且聚合应用监控信息，最终可以实现秒级的实时监控。
- **规则管理和推送**：统一管理推送规则。
- **鉴权**：生产环境中鉴权非常重要。这里每个开发者需要根据自己的实际情况进行定制。



### 启动

```bash
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
```

用户名/密码：`sentinel/sentinel`



#### 启动配置项

https://sentinelguard.io/zh-cn/docs/startup-configuration.html



## Spring Cloud Alibaba Sentinel

如果要在您的项目中引入 Sentinel，使用 group ID 为 `com.alibaba.cloud` 和 artifact ID 为 `spring-cloud-starter-alibaba-sentinel` 的 starter。

```groovy
implementation 'com.alibaba.cloud:spring-cloud-starter-alibaba-sentinel'
```

然后在配置文件中增加配置

```yaml
spring:
  cloud:
    sentinel:
      transport:
        port: 8719
        dashboard: localhost:8080
```

这里的 `spring.cloud.sentinel.transport.port` 端口配置会在应用对应的机器上启动一个 Http Server，该 Server 会与 Sentinel 控制台做交互。比如 Sentinel 控制台添加了一个限流规则，会把规则数据 push 给这个 Http Server 接收，Http Server 再将规则注册到 Sentinel 中。





## 服务降级



## 服务熔断

### 概念

#### 什么是服务熔断



#### 什么是服务雪崩



### 动态设置熔断规则



### 熔断器原理







