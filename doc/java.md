## 基础理论
**DO**（Data Object）与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
**DTO**（Data Transfer Object）是远程调用对象，它是 RPC 服务提供的领域模型。
> 对于 DTO 一定要保证其序列化，实现 Serializable 接口，并显示提供 serialVersionUID，否则在反序列化时，如果 serialVersionUID 被修改，那么反序列化会失败。

**BO**（Business Object）是业务逻辑层封装业务逻辑的对象，一般情况下，它是聚合了多个数据源的复合对象。
**DTO**（View Object） 通常是请求处理层传输的对象，它通过 Spring 框架的转换后，往往是一个 JSON 对象。


```
JavaBeans spec:
getUrl/setUrl => property name: url
getURL/setURL => property name: URL

Jackson:
getUrl/setUrl => property name: url
getURL/setURL => property name: url

Introspector.decapitalize() // 转换命名
```


### SPI

SPI 全称为 (Service Provider Interface) ，是JDK内置的一种服务提供发现机制。SPI是一种动态替换发现的机制， 比如有个接口，想运行时动态的给它添加实现，你只需要添加一个实现。我们经常遇到的就是java.sql.Driver接口，其他不同厂商可以针对同一接口做出不同的实现，mysql和postgresql都有不同的实现提供给用户，而Java的SPI机制可以为某个接口寻找服务实现。

当服务的提供者提供了一种接口的实现之后，需要在classpath下的META-INF/services/目录里创建一个以服务接口命名的文件，这个文件里的内容就是这个接口的具体的实现类。当其他的程序需要这个服务的时候，就可以通过查找这个jar包（一般都是以jar包做依赖）的META-INF/services/中的配置文件，配置文件中有接口的具体实现类名，可以根据这个类名进行加载实例化，就可以使用该服务了。JDK中查找服务实现的工具类是：java.util.ServiceLoader。



### JMX

JMX（Java Management Extensions，即 Java 管理扩展）是一个为应用程序、设备、系统等植入监控管理功能的框架。JMX 使用管理 MBean 来监控业务资源，这些 MBean 在 JMX MBean 服务器上注册，代表 JVM 中运行的应用程序或服务。每个 MBean 都有一个属性列表。JMX 客户端可以连接到 MBean Server 来读写 MBean 的属性值。



## 语言特性





## Test

### java run single unit testing

```bash
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



## 常用命令

### java

```bash
-verbose:gc     # 输出垃圾回收信息
```



#### 实战

```bash
# 当前的垃圾回收器版本
java -XX:+PrintCommandLineFlags -version
# java8内存占用
java -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary -XX:+PrintNMTStatistics -version
# java11内存占用
java -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary -XX:+PrintNMTStatistics -version

```

### jar

```bash
jar xvf test.jar 			# 解压到当前目录
jar cvf filename.jar a.class b.class    #压缩指定文件
jar cvf weibosdkcore.jar *  #全部压缩

# 解开压缩包，修改文件，在次打包成jar
unzip test.jar test			
cd test
jar -cmv0f META-INF/MANIFEST.MF test.jar *
```



### jhsdb

jdk9 以后提供

```bash
 ~ jhsdb
    clhsdb       	command line debugger
    debugd       	debug server
    hsdb         	ui debugger
    jstack --help	to get more information
    jmap   --help	to get more information
    jinfo  --help	to get more information
    jsnap  --help	to get more information
```



#### 实战

```bash
jhsdb jmap --heap --pid <pid>			# 查看heap的信息，GC使用的算法，heap的配置. (原来的jmap -heap 命令没有了, 被这个命令代替了)
```



### jcmd

```bash
jcmd <pid> PerfCounter.print 			# 性能统计信息
jcmd <pid> help    # 列出可执行的进程操作
```

```bash
~ jcmd 43358 help
43358:
The following commands are available:
Compiler.CodeHeap_Analytics
Compiler.codecache
Compiler.codelist
Compiler.directives_add
Compiler.directives_clear
Compiler.directives_print
Compiler.directives_remove
Compiler.queue
GC.class_histogram		 # 查看系统中类统计信息 jmap -histo <pid>
GC.class_stats
GC.finalizer_info
GC.heap_dump
GC.heap_info
GC.run  								# 对JVM执行System.gc()
GC.run_finalization
JFR.check
JFR.configure
JFR.dump
JFR.start
JFR.stop
JVMTI.agent_load
JVMTI.data_dump
ManagementAgent.start
ManagementAgent.start_local
ManagementAgent.status
ManagementAgent.stop
Thread.print				 			# 查看线程堆栈信息  jstack <pid>
VM.class_hierarchy
VM.classloader_stats
VM.classloaders
VM.command_line
VM.dynlibs
VM.flags										# 查看JVM的启动参数
VM.info
VM.log
VM.metaspace
VM.native_memory
VM.print_touched_methods
VM.set_flag
VM.stringtable
VM.symboltable
VM.system_properties				 # 查看JVM的系统配置信息
VM.systemdictionary
VM.uptime
VM.version
help
```



### jmap

可以生成 java 程序的 dump 文件， 也可以查看堆内对象示例的统计信息、查看 ClassLoader 的信息以及 finalizer 队列。

```bash
jmap -dump:live,format=b,file=dump.hprof <pid>  # 输出堆信息到文件
```



#### 参数：

- **option：** 选项参数。
- **pid：** 需要打印配置信息的进程ID。
- **executable：** 产生核心dump的Java可执行文件。
- **core：** 需要打印配置信息的核心文件。
- **server-id** 可选的唯一id，如果相同的远程主机上运行了多台调试服务器，用此选项参数标识服务器。
- **remote server IP or hostname** 远程调试服务器的IP地址或主机名。

#### option

- **no option：** 查看进程的内存映像信息,类似 Solaris pmap 命令。
- **heap：** 显示Java堆详细信息, java9 以后实用`jhsdb`
- **histo[:live]：** 显示堆中对象的统计信息
- **clstats：**打印类加载器信息
- **finalizerinfo：** 显示在F-Queue队列等待Finalizer线程执行finalizer方法的对象
- **dump:<dump-options>：**生成堆转储快照
- **F：** 当-dump没有响应时，使用-dump或者-histo参数. 在这个模式下,live子参数无效.
- **help：**打印帮助信息
- **J<flag>：**指定传递给运行jmap的JVM的参数



### JConsole

#### 通过 JConsole 监控 Tomcat

首先我们需要开启 JMX 的远程监听端口，具体来说就是设置若干 JVM 参数。我们可以在 Tomcat 的 bin 目录下新建一个名为`setenv.sh`的文件，然后输入下面的内容：

```bash
export JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote"
export JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.port=9001"
export JAVA_OPTS="${JAVA_OPTS} -Djava.rmi.server.hostname=x.x.x.x"
export JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.ssl=false"
export JAVA_OPTS="${JAVA_OPTS} -Dcom.sun.management.jmxremote.authenticate=false"
```

重启 Tomcat，这样 JMX 的监听端口 9001 就开启了，接下来通过 JConsole 来连接这个端口。

```bash
jconsole x.x.x.x:9001
```



## JVM 优化

### 参数

| **参数名称**                | **含义**                                                   | **默认值**           |                                                              |
| --------------------------- | ---------------------------------------------------------- | -------------------- | ------------------------------------------------------------ |
| -Xms                        | 初始堆大小                                                 | 物理内存的1/64(<1GB) | 默认(MinHeapFreeRatio参数可以调整)空余堆内存小于40%时，JVM就会增大堆直到-Xmx的最大限制. |
| -Xmx                        | 最大堆大小                                                 | 物理内存的1/4(<1GB)  | 默认(MaxHeapFreeRatio参数可以调整)空余堆内存大于70%时，JVM会减少堆直到 -Xms的最小限制 |
| -Xmn                        | 年轻代大小(1.4or lator)                                    |                      | **注意**：此处的大小是（eden+ 2 survivor space).与jmap -heap中显示的New gen是不同的。 整个堆大小=年轻代大小 + 年老代大小 + 持久代大小. 增大年轻代后,将会减小年老代大小.此值对系统性能影响较大,Sun官方推荐配置为整个堆的3/8 |
| -XX:NewSize                 | 设置年轻代大小(for 1.3/1.4)                                |                      |                                                              |
| -XX:MaxNewSize              | 年轻代最大值(for 1.3/1.4)                                  |                      |                                                              |
| -XX:PermSize                | 设置持久代(perm gen)初始值                                 | 物理内存的1/64       |                                                              |
| -XX:MaxPermSize             | 设置持久代最大值                                           | 物理内存的1/4        |                                                              |
| -Xss                        | 每个线程的堆栈大小                                         |                      | JDK5.0以后每个线程堆栈大小为1M,以前每个线程堆栈大小为256K.更具应用的线程所需内存大小进行 调整.在相同物理内存下,减小这个值能生成更多的线程.但是操作系统对一个进程内的线程数还是有限制的,不能无限生成,经验值在3000~5000左右 一般小的应用， 如果栈不是很深， 应该是128k够用的 大的应用建议使用256k。这个选项对性能影响比较大，需要严格的测试。（校长） 和threadstacksize选项解释很类似,官方文档似乎没有解释,在论坛中有这样一句话:"” -Xss is translated in a VM flag named ThreadStackSize” 一般设置这个值就可以了。 |
| -*XX:ThreadStackSize*       | Thread Stack Size                                          |                      | (0 means use default stack size) [Sparc: 512; Solaris x86: 320 (was 256 prior in 5.0 and earlier); Sparc 64 bit: 1024; Linux amd64: 1024 (was 0 in 5.0 and earlier); all others 0.] |
| -XX:NewRatio                | 年轻代(包括Eden和两个Survivor区)与年老代的比值(除去持久代) |                      | -XX:NewRatio=4表示年轻代与年老代所占比值为1:4,年轻代占整个堆栈的1/5 Xms=Xmx并且设置了Xmn的情况下，该参数不需要进行设置。 |
| -XX:SurvivorRatio           | Eden区与Survivor区的大小比值                               |                      | 设置为8,则两个Survivor区与一个Eden区的比值为2:8,一个Survivor区占整个年轻代的1/10 |
| -XX:LargePageSizeInBytes    | 内存页的大小不可设置过大， 会影响Perm的大小                |                      | =128m                                                        |
| -XX:+UseFastAccessorMethods | 原始类型的快速优化                                         |                      |                                                              |
| -XX:+DisableExplicitGC      | 关闭System.gc()                                            |                      | 这个参数需要严格的测试                                       |
| -XX:MaxTenuringThreshold    | 垃圾最大年龄                                               |                      | 如果设置为0的话,则年轻代对象不经过Survivor区,直接进入年老代. 对于年老代比较多的应用,可以提高效率.如果将此值设置为一个较大值,则年轻代对象会在Survivor区进行多次复制,这样可以增加对象再年轻代的存活 时间,增加在年轻代即被回收的概率 该参数只有在串行GC时才有效. |
| -XX:+AggressiveOpts         | 加快编译                                                   |                      |                                                              |
| -XX:+UseBiasedLocking       | 锁机制的性能改善                                           |                      |                                                              |
| -Xnoclassgc                 | 禁用垃圾回收                                               |                      |                                                              |
| -XX:SoftRefLRUPolicyMSPerMB | 每兆堆空闲空间中SoftReference的存活时间                    | 1s                   | softly reachable objects will remain alive for some amount of time after the last time they were referenced. The default value is one second of lifetime per free megabyte in the heap |
| -XX:PretenureSizeThreshold  | 对象超过多大是直接在旧生代分配                             | 0                    | 单位字节 新生代采用Parallel Scavenge GC时无效 另一种直接在旧生代分配的情况是大的数组对象,且数组中无外部引用对象. |
| -XX:TLABWasteTargetPercent  | TLAB占eden区的百分比                                       | 1%                   |                                                              |
| -XX:+*CollectGen0First*     | FullGC时是否先YGC                                          | false                |                                                              |

**Java8 内存占用**

```bash
java -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary -XX:+PrintNMTStatistics -version
```

**Java11 内存占用**

```bash
java -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary -XX:+PrintNMTStatistics -version
```

* **Reserved** 由操作系统承诺的可用内存大小。但尚未分配，JVM 无法访问

* **Committed** 已被 JVM 分配，可访问

**当前的垃圾回收器版本**

```bash
java -XX:+PrintCommandLineFlags -version
```

3