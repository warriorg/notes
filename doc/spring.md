### 控制反转（Inversion of Control，IoC）
* 常见另一种叫法**依赖注入（Dependency Injection，DI）**, 还有一种叫法**依赖查找(Dependency Lookup)** 对象在被创建的时候，由IoC容器注入，对象的创建的控制权由IoC容器负责

* Class A中用到了Class B的对象b，一般情况下，需要在A的代码中显式的new一个B的对象。		
采用依赖注入技术之后，A的代码只需要定义一个私有的B对象，不需要直接new来获得这个对象，而是通过相关的容器控制程序来将B对象在外部new出来并注入到A类里的引用中。而具体获取的方法、对象被获取时的状态由配置文件（如XML）来指定。

### spring 应用上下文
* AnnotationConfigApplicationContext：从一个或多个基于Java的配置类中加载Spring应用上下文。 
* AnnotationConfigWebApplicationContext：从一个或多个基于Java的配置类中加载Spring Web应用上下文。
* ClassPathXmlApplicationContext：从类路径下的一个或多个XML配置文件中加载上下文定义，把应用上下文的定义文件作为类资源。
* FileSystemXmlapplicationcontext：从文件系统下的一个或多个XML配置文件中加载上下文定义。 
* XmlWebApplicationContext：从Web应用下的一个或多个XML配置文件中加载上下文定义。

## Web MVC
### DispatcherServlet
#### interception

所有HandlerMapping注解的方法都支持拦截器，当想要将特定的功能应用到特定的请求(例如检查主体)时，这些拦截器非常有用。拦截器必须实现 org.springframework.web.HandlerInterceptor

* preHandle(..):  在处理程序执行之前

  > 方法返回一个布尔值。可以使用此方法中断或继续执行

* postHandle(..):  在处理程序执行之后

* afterCompletion(..): 请求完成之后

## AOP(Aspect-Oriented Programming)
#### AOP concepts
* *Aspect*： 指的是横切多个类的一种模块。在Spring中，切面用的就是普通的类（xml或者带@Aspect注解配置）
* *Joint point*：连接点,表示要横切的方法。
* *Advice*：通知，想要的功能，例如安全，事物，日志等。先定义好，然后在想用的地方用一下
* *Pointcut*：能匹配上连接点的那些方法
* *Introduction*：声明额外的方法或者属性，比如让bean实现一个接口
* *Target object*：被一个或多个切面advised过的对象。因为Spring AOP是使用运行时代理实现的，所以这个对象总是一个代理对象。
* *AOP proxy*：AOP框架实现切面，用JDK代理或者CGLIB代理
* *Weaving*：织入， 将切面与其他应用程序类型或对象链接以创建advised对象。 在编译时（例如使用AspectJ编译器），加载时,或在运行时完成。 与其他纯Java AOP框架一样，Spring AOP在运行时执行编织。

##### advice类型
* *Before advice*：连接点运行之前通知
* *After returning advice*：在连接点正常完成之后执行的通知，例如，方法返回时没有抛出异常
* *After throwing advice*：方法抛出异常而退出，则执行通知。
* *After (finally) advice*：无论连接点以何种方式退出(正常或异常返回)，都要执行通知。
* *Around advice*：围绕连接点方法调用的通知，最给力的方法，Around通知可以在方法调用前后执行自定义行为，它还负责选择是继续到连接点，还是通过返回自己的返回值或抛出异常来缩短建议的方法执行。

#### SpringAOP的能力和目标

Spring AOP目前只支持方法执行连接点(建议在Spring bean上执行方法)。没有实现字段拦截。

Spring AOP 不同于其他AOP框架（例如AspectJ），目标不是提供最完整的AOP实现，而是提供AOP实现与Spring IoC之间的紧密集成，以帮助解决企业应用程序中的常见问题。

#### AOP Proxies
Spring AOP默认为AOP代理使用标准J2SE动态代理。

### @AspectJ support
通过类注解@AspectJ来声明一个切面， AspectJ 5 引入了 @AspectJ 风格，Spring 2.0使用AspectJ提供的用于切入点解析和匹配的库，与AspectJ 5相同的注解。AOP运行时仍然是纯粹的Spring AOP，并且不依赖于AspectJ编译器或weaver。
#### Enabling @AspectJ Support
java代码配置方式开启
```java
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

}
```
xml配置方式
```xml
<aop:aspectj-autoproxy/>
```
#### Declaring an aspect
```java
package me.warriorg.spring.aop.example;

import org.aspectj.lang.annotation.Aspect;

@Aspect
public class NotVeryUsefulAspect {
}
```

```xml
<bean id="myAspect" class="me.warriorg.spring.aop.example.NotVeryUsefulAspect"></bean>
```

####  Declaring a pointcut

切入点表达式由@Pointcut注解实现

Spring AOP 支持 的AspectJ切入点指示符（PCD）表达式：

* *execution* - 对于匹配方法执行连接点，这是在使用Spring AOP时将使用的主要切入点指示器
* *within* - 限制对特定类型内连接点的匹配(简单地说，在使用Spring AOP时在匹配类型内声明的方法的执行)
* *this* - 限制匹配连接点为指定AOP代理类的类型

- *target* - 限制匹配连接点为指定目标对象的类型
- *args* - 限制匹配连接点为指定的参数类型
- *@within* - 限制匹配连接点为指定注解的类
- *@annotation* - 限制匹配连接点为指定方法所应用的注解

Examples
```java
execution(public * *(..)) //任何公共方法的执行
execution(* set*(..)) //任何名称以“set”开头的方法的执行
execution(* com.xyz.service.AccountService.*(..))//AccountService接口定义的任何方法的执行
execution(* com.xyz.service.*.*(..)) //service包中定义的任何方法的执行
execution(* com.xyz.service..*.*(..)) //service包或子包中定义的任何方法的执行
within(com.xyz.service.*) //service包中任何连接点
within(com.xyz.service..*) //service包或子包中任何连接点
this(com.xyz.service.AccountService) //代理实现AccountService接口的任何连接点(仅在Spring AOP中执行方法)
target(com.xyz.service.AccountService) //目标对象实现AccountService接口的任何连接点(仅在Spring AOP中执行方法)
args(java.io.Serializable)	//任何连接点(仅在Spring AOP中执行方法)只接受一个参数，并且在运行时传递的参数是可序列化的
@target(org.springframework.transaction.annotation.Transactional) //任何连接点(仅在Spring AOP中执行方法)，其中声明的目标对象类型具有@Transactional注解
@annotation(org.springframework.transaction.annotation.Transactional) //任何连接点(仅在Spring AOP中执行方法)，其中执行方法具有@Transactional注解
@args(com.xyz.security.Classified) //任何接受单个参数的连接点(仅在Spring AOP中执行方法)，以及传递的参数的运行时类型都有@Classified注解
bean(tradeService) //在名为tradeService的Spring bean上的任何连接点(仅在Spring AOP中执行方法)
bean(*Service) //Spring bean上的任何连接点(仅在Spring AOP中执行方法)，其名称与通配符表达式*Service匹配
```

#### Declaring advice

建议与切入点表达式相关联，并在切入点匹配的方法执行之前，之后或周围运行。 切入点表达式可以是对命名切入点的简单引用，也可以是在适当位置声明的切入点表达式。



## Spring Boot

[Manual](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/)

### Spring Boot Actuator

[Manual](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready)

使用HTTP或JMX端点来管理和监视应用程序、审计、健康

#### Endpoints

* `auditevents` 公开当前应用程序的审计事件信息。

* `beans` 显示应用程序中所有Spring bean的完整列表

* `caches` Exposes available caches.

* `conditionklkls` Shows the conditions that were evaluated on configuration and auto-configuration classes and the reasons why they did or did not match.

* `configprops` 显示@ConfigurationProperties完整目录

* `env` Exposes properties from Spring’s `ConfigurableEnvironment`.

* `flyway`

* `health`

* `httptrace`

* `info`

* `integrationgraph`

* `loggers`

* `metrics`

* `mappings`

* `scheduledtasks`

* `sessions`

* `shutdown`

* `threaddump`

Spring MVC, Spring WebFlux, or Jersey 

* `heapdump`
* `jolokia`
* `logfile`
* `prometheus`