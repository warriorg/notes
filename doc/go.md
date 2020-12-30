

## 介绍
Go语言是一种让代码分享更容易的编程语言。Go语言自带一些工具，让使用别人写的包更容易，并且分享自己写的包更容易。

### 文档
命令行获取
> go doc fmt

浏览文档
>godoc -http=:6060  
> *包含所有Go标准库和你的GOPATH下Go源代码的文档*

### 环境变量

GOPROXY 指定从哪儿下载源码    
GO111MODULE 释放开启module

Go语言对并发的支持是这门语言最重要的特性之一。goroutine很像线程，但是它占用的内存远少于线程，使用它需要的代码更少。通道（channel）是一种内置的数据结构，可以让用户在不同 的goroutine之间同步发送具有类型的消息。

## 术语
**goroutine** 是可以与其他goroutine并行执行的函数，同时也会与主程序（程序的入口）并行执行。

> 个人理解： 相当于其他语言的多线程

**channel** 一种数据结构，可以让goroutine之间进行安全的数据通信。
> 可以避免像其他语言里常见的共享内存访问的问题。

**鸭子类型** 如果它叫起来像鸭子，那它就可能是只鸭子



## 规范
* 如果接口类型只包含一个方法，那么这个类型的名字以`er`结尾

Go语言的类型系统
1. 类型简单
2. Go接口对一组行为建模


__空白标识符(\_)__ 用来抛弃不想继续使用的值，如给导入的包赋予一个空名字，或者忽略函数返回的你不感兴趣的值。

__init函数__ 每个包可以包含任意多个init函数，所有被编译器发现的*init函数*都在*main函数*之前执行。



函数的文档直接写在函数声明之前，使用人类可读的句子编写。如果想给包写一段文字量比较大的文档，可以在工程里包含一个叫作doc.go的文件，使用同样的包名，并把包的介绍使用注释加在包名声明之前。

## 数据类型
> 引用类型 切片、map、channel(通道)、接口、函数， 当声明上述类型的变量时，创建的变量被称作 Header 值

### 数组
固定长度。用于存储一段具有相同的类型的元素的连续块
> 如果使用 `...` 代替数组长度，则更具初始化时数组元素的数量来确定该数组的长度

```go
// 声明一个包含5个元素的整型数组
var array [5]int
// 用具体值初始化每个元素
array := [5]int {1, 2, 3, 4, 5}
// 容量由初始化值的数量决定
array := [...]int {1, 2, 3, 4, 5}
// 用具体值初始化索引为1，2 的元素，其余则保持0
array := [5]int {1: 1, 2: 2}
```

### Slice
动态数组，可以按需自动增长和缩小。是一个结构体，定义如下

```go
type slice struct {
  array unsafe.Pointer
  len int
  cap int
}
```

> 在64位架构机器上, 一个切片需要24字节的内存: 指针字段需要8字节，长度和容量字段分别需要8字节。

```go
// 创建一个字符串切片，其长度和容量都是5个元素
slice := make([]string, 5)
// 长度为3个元素，容量为5个元素 不容许创建容量小雨长度的切片
slice := make([]int, 3, 5)
// 创建 nil 整型切片
var slice []int

```
> 如果在 `[]` 运算符中指定了一个值，那么创建的就是数组而不是切片

>```go
>//创建有3个元素的整型数组
>array := [3]int {10, 20, 30}
>//创建长度和容量都是3的整型切片
>slice := []int {10, 20, 30}
>```

* 赋值和切片   
创建一个新切片就是把底层数组切出一部分

```go
//创建一个整型切片，其长度和容量都是5个元素
slice := []int{1,2,3,4,5}
//创建一个新切片，其长度为2个元素，容量为4个元素
newSlice := slice[1:3]
```
> 对于底层数组容量是k的切片 `slice[i:j]` 来说   
> 长度: j - i  
> 容量: k - i  


* 切片增长  
`append` 总是增加新切片的长度，而容量则有可能会改变，容量改变取决于被操作的切片的可用容量

* 创建切片时的第3个索引
 第三个索引可以用来控制新切片的容量，其目的并不是要增加容量，而是要限制容量
 > `slice[i:j:k]`   
 > 长度 j - i   
 > 容量 k - i  

 * 迭代
`range` 返回两个值。第一个值是 索引 第二个对应位置元素值的一份副本   

### __map__   
存储一系列无序键值对

### __自定义类型__

1. 使用关键字struct创建一个结构类型 	

```golang 
type User struct {
	ID string 
}
```

2. 基于一个已有类型，将其作为新类型的类型说明

```golang
type Duration int64
// int64类型叫作Duration的基础类型。Go 并不认为 Duration 和 int64 是同一类型。这两个类型是完全不同的有区别的类型。
```

### __struct__

*struct* 有2中方法， 值接受者和指针接受者

* 值接受者使用副调用方法，指针接受者使用原始值调用方法
* 因为不能总是拿到值的地址，所以值只能调用值接受者方法
> 也可以使用指针来调用值方法，编译器转换后的代码    
>`(*obj).method()`     
> 也可以使用值来调用引用方法，编译器转换后的代码     
> `(&ojb).method()`   

声明一个方法时使用 _值方法_ 还是 _指针方法_，取决于想得到一个新值还是想修改原有的值

### __接口__		

用来定义行为

### __channel__			

```go
unbuffered := make(chan int)    //无缓冲通道
buffered := make(chan int, 10)
```

* 无缓冲的通道 在接收前没有能力保存任何值的通道	   

### 类型转换
```go
// Numeric Conversions 
i, err := strconv.Atoi("-42")
s := strconv.Itoa(-42)

// ParseBool, ParseFloat, ParseInt, and ParseUint convert strings to values
b, err := strconv.ParseBool("true")
f, err := strconv.ParseFloat("3.1415", 64)
i, err := strconv.ParseInt("-42", 10, 64)
u, err := strconv.ParseUint("42", 10, 64)

// FormatBool, FormatFloat, FormatInt, and FormatUint convert values to strings
s := strconv.FormatBool(true)
s := strconv.FormatFloat(3.1415, 'E', -1, 64)
s := strconv.FormatInt(-42, 16)
s := strconv.FormatUint(42, 16)

// Quote and QuoteToASCII convert strings to quoted Go string literals. The latter guarantees that the result is an ASCII string, by escaping any non-ASCII Unicode with \u:
q := Quote("Hello, 世界")
q := QuoteToASCII("Hello, 世界")
```

### 总结

* Go语言是现代的、快速的、带有一个强大的标准库。
* Go语言内置对并发的支持。
* Go语言使用接口作为代码复用的基础模块。
* 每个代码文件都属于一个包，而包名应该与代码文件所在的文件夹同名
* Go语言提供了多种声明和初始化变量的方式。如果变量的值没有显式初始化，编译器会将变量初始化为零值。
* 使用指针可以在函数间或着goroutine间共享数据。
* 通过启动goroutine和使用通道完成并发和同步
* Go语言提供了内置函数来支持Go语言内部的数据结构。
* Go语言中包是组织代码的基本单位
* 环境变量GOPATH决定了Go源代码在磁盘上被保存、编译和安装的位置。
* 可以为工程设置不同的GOPATH，以保持源代码和依赖的隔离。
* Go工具是在命令行上工作的最好工具
* 开发人员可以使用go get 来获取别人的包并将其安装到自己的GOPATH指定目录
* 要为别人创建包很简单，只要把源代码放到公用代码库，并遵守一些简单的规则就可以了。
* Go语言在设计时将分享代码作为语言的核心特性和驱动力。
* 推荐使用依赖管理工具来管理依赖 如 godep, vender, gb
* 数组结构是切片和map的基石
* Go语言里切片经常用来处理数据的集合，map用来处理具有键值对结构的数据
* 内置函数make可以创建切片和map，并指定原始的长度和容量。也可以直接使用切片和map字面量，或者使用字面量的初始值。
* 切片有容量限制，不过可以使用内置的append函数扩展容量。
* map的增长没有容量或者任何限制
* 内置函数len可以用来获取切片或者map的长度
* 内置函数cap只能用于切片 （cap()函数返回的是数组切片分配的空间大小）
* 通过组合，可以创建多维组和多维切片。也可以使用切片或者其他map作为map的值。但是切片不能用作map的键
* 将切片或者map传递给函数成本很小，并且不会复制底层的数组结构。
* 使用关键字 struct 或者指定已经存在的类型，可以声明用户定义的类型。
* 方法提供了一种给用户定义的类型增加行为的方式。
* 设计类型时需要去人类型的本质是原始的，还是非原始的。
* 接口是声明了一组行为并支持多态的类型。
* 嵌入类型提供了扩展类型的能力，而无需使用继承。
* 标识符要么是从包里公开的，要么是在包里未公开的。

## modules

```bash
download    download modules to local cache
edit        edit go.mod from tools or scripts
graph       print module requirement graph
init        initialize new module in current directory
tidy        add missing and remove unused modules
vendor      make vendored copy of dependencies
verify      verify dependencies have expected content
why         explain why packages or modules are needed
```

```bash
go mod init moduleName # 初始化go.mod
go list -m -json all  # -json JSON格式显示 all 显示全部库
```

### go.mod

```bash
module cws

require (
    github.com/warriorg/cron v1.0.0
    github.com/fatih/color v1.7.0 // indirect
)
```


