## 简介

是一个开源构建自动化系统，它基于Apache Ant和Apache Maven的概念，并引入了基于Groovy的特定于域的语言（DSL），而不是Apache Maven用于声明项目配置的XML形式。



## [Usage](https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_usage)

### implementation

 编译项目的生产源所需的依赖项，这些依赖项不是项目公开的API的一部分。 例如，该项目使用Hibernate进行内部持久层实现。

### api

编译项目的生产源所需的依赖项，这是项目公开的API的一部分。 例如，项目使用Guava并在方法签名中公开具有Guava类的公共接口。

### testImplementation

编译和运行项目的测试源所需的依赖项。例如，项目决定使用测试框架JUnit编写测试代码。



## Managing Dependencies

### Declaring Dependencies

#### [Declaring a dependency without version](https://docs.gradle.org/current/userguide/declaring_dependencies.html#declaring_a_dependency_without_version)

```groovy
build.gradle
dependencies {
    implementation 'org.springframework:spring-web'
}

dependencies {
    constraints {
        implementation 'org.springframework:spring-web:5.0.2.RELEASE'
    }
}
```

#### [Declaring a dynamic version](https://docs.gradle.org/current/userguide/declaring_dependencies.html#sub:declaring_dependency_with_dynamic_version)

快照版本包含后缀-SNAPSHOT

```groovy
dependencies {
    implementation 'org.springframework:spring-web:5.0.3.BUILD-SNAPSHOT'
}

configurations.all {
  	// 默认情况下，Gradle缓存24小时更改依赖项的版本。 调整10分钟
    resolutionStrategy.cacheDynamicVersionsFor 10, 'minutes'
}
```

