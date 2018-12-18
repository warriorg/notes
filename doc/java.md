###jar
```bash
jar xvf test.jar 			# 解压到当前目录
jar cvf filename.jar a.class b.class    #压缩指定文件
jar cvf weibosdkcore.jar *  #全部压缩
```

## Test

###java run single unit testing
```base
gradle test -Dtest.single=PropertyBillServiceTest
```



## 日志

### slf4j
SLF4J（Simple Logging Facade for Java）用作各种日志框架（例如java.util.logging，logback，log4j）的简单外观或抽象，允许最终用户在部署时插入所需的日志记录框架。

```java 
// 日志配置
compile "org.slf4j:slf4j-api:$slf4jVersion"
compile "ch.qos.logback:logback-core:$logbackVersion"
compile "ch.qos.logback:logback-classic:$logbackVersion"
```

## H2数据库

###连接H2数据库有以下方式
* 服务式 （Server）`jdbc:h2:tcp://localhost/~/test`
* 嵌入式（Embedded） `jdbc:h2:~/test`
* 内存（Memory） `jdbc:h2:tcp://localhost/mem:test`

## mapstruct学习
[官网](http://mapstruct.org/)		
[IDEA插件](https://plugins.jetbrains.com/plugin/10036-mapstruct-support)			


## jenv管理版本
```bash
#macos
brew install jenv  
# bashrc 根据自己的配置，bash 为 bash_profile， zsh 为 zshrc
echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.bashrc
echo 'eval "$(jenv init -)"' >> ~/.bashrc

# 列出所有java home
/usr/libexec/java_home -V  

jenv add /Library/Java/JavaVirtualMachines/openjdk-11.0.1.jdk/Contents/Home
jenv add /Library/Java/JavaVirtualMachines/jdk1.8.0_192.jdk/Contents/Home

jenv versions 
  system
  1.8
  1.8.0.192
  11.0
  11.0.1
* openjdk64-11.0.1 (set by /Users/warrior/.jenv/version)
  oracle64-1.8.0.192
  
#Configure global version
jenv global oracle64-1.8.0.192 
#Configure local version (per directory)
jenv local oracle64-1.8.0.192 
#Configure shell instance version
jenv shell oracle64-1.8.0.192 
```