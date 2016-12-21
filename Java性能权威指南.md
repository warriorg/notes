
TPS 每秒事物数

RPS 每秒请求数

OPS 每秒操作数

###3. Java性能调优工具箱

jcmd 它用来打印Java进程所涉及的基本类、线程和VM信息。
jconsole 提供JVM活动的图形视图，包括线程的使用、类的使用和GC活动
jhat  读取内存堆转储，事后工具
jmap  提供堆转储后台其他JVM内存使用的信息
jinfo 查看JVM的系统属性
jstack 转储Java进程的栈信息
jvisualvm 监视JVM的GUI工具
jmc 开启一个窗口显示当前机器上的JVM进程

### 4. JIT编译器
JIT(Just In Time) 即时 

编译器最重要的优化包括何时使用主内存中的值，以及何时在寄存器中存贮值

逃逸分析（escape analysis）

### 5.垃圾收集入门

最主流的4个垃圾收集器分别为：

* Serial收集器（常用于单CPU环境）
* THroughput(或者Parallel) 收集器
* Concurrent 收集器
* G1 收集器

