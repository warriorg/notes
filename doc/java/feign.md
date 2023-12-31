# OpenFeign

OpenFeign是Spring Cloud 在Feign的基础上支持了Spring MVC的注解，如`@RequesMapping`等等。
OpenFeign的@FeignClient可以解析SpringMVC的`@RequestMapping`注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。



## 实战



## Ribbon 负载均衡



## 更换负载均衡策略



### 更换内置策略

Ribbon 默认采用的是 RoundRobinRule，即轮询策略。但是可以通过修改配置文件或者JavaConfig的方式更换均衡策略



#### 方式一： 通过修改配置文件

```yml
# <serverName>.<serverNameSpace>.NFLoadBalancerRuleClassName
account:
	ribbon:
		NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
```



#### 方式二： 通过JavaConfig

```java
@Bean
public IRule loadBanlancer() {
    return new RandomRunle();
}
```



### 自定义负载均衡策略



### Ribbon内置负载均衡策略



## 负载均衡器 Spring Cloud LoadBalancer





## OpenFeign 原理


## 问题

如果返回 `Response` ,则 `errorDecoder` 方法不会背触发
