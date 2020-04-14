# Java性能调优工具箱

## 操作系统的工具和分析

### CPU使用率

### CPU运行队列

### 磁盘使用率

### 网络使用率

## Java监控工具

* jcmd 它用来打印Java进程所涉及的基本类、线程和VM信息。

  ```bash
  jcmd process_id command optional_arguments
  ```

* jconsol 提供JVM活动的图形化视图，包括线程的使用、类的使用和GC活动。

* jhat 提供JVM活动的图形化视图，包括线程的使用、类的使用和GC活动。

* jmap 提供堆转储和其他JVM内存使用的信息。可以适用于脚本，但堆转储必须在事后分析工具中使用。

* jinfo 查看JVM的系统属性，可以动态设置一些系统属性。可适用于脚本。

* jstack 转储Java进程的栈信息。可适用于脚本。

* jstat 提供GC和类装载活动的信息。可适用于脚本。

* jvisualvm 监视JVM的GUI工具，可用来剖析运行的应用，分析JVM堆转储（事后活动，虽然jvisualvm也可以实时抓取程序的堆转储）。

### 基本的VM信息

```bash
jcmd process_id VM.uptime 									# JVM运行时长
jcmd process_id VM.system_properties    		# 系统属性
jinfo -sysprops process_id 									# 系统属性 等同于上一条
jcmd process_id VM.version									# 获取JVM版本
jcmd process_id VM.command_line 						# 显示程序所用的命令行
jcmd process_id VM.flags 										# 生效的JVM调优标志
java -XX:+PrintFlagsFinal -version					# 输出所有的调优标志
jinfo -flag -PrintGCDetails process_id			# turns off PrintGCDetails
```

### 线程信息

```bash
jstack process_id 
jcmd process_id Thread.print
```

## 性能分析工具

### 采样分析器

性能分析有两种模式

* 数据采样
* 数据探查

## Java任务控制



# JIT 编译器

## 概览

### 热点编译

## 调优入门：选择编译器类型（Client、Server或者二者同用）

## 编译器中级调优

