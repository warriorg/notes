



#  简介

![数据结构与算法](./assets/images/数据结构与算法.png)


# 复杂度分析

* $$O(1)$$ 常数复杂度(Constant Complexity)
* $$O(log n)$$ 对数复杂度(Logarithmic Complexity)
* $$O(n)$$ 线性时间复杂度(Linear Complexity)
* $$O(n^2)$$ 平方(N square Complexity)
* $$O(n^3)$$ 立方(N square Complexity)
* $$O(2^n)$$ 指数(Exponential Growth)
* $$O(n!)$$ 阶乘(Factorial)

## 大O复杂度表示法

*所有代码的执行时间 T(n) 与每行代码的执行次数 n 成正比*

$$T(n)=O(f(n))$$

$$T(n)$$ 代码执行的时间, $$n$$ 数据规模的大小, $$f(n)$$ 表示每行代码执行的次数总和, $$O$$ 表示代码的执行时间 T(n) 与 f(n) 表达式成正比



## 时间复杂度分析

### 1. 只关注循环执行次数最多的一段代码

### 2. 加法法则：总复杂度等于量级最大的那段代码的复杂度

### 3. 乘法法则：嵌套代码的复杂度等于嵌套内外代码复杂度的乘积



## 几种常见的时间复杂度实例分析

### $$O(1)$$

只要算法中不存在循环语句、递归语句，即使有成千上万行的代码，其时间复杂度也是Ο(1)

```java
int n = 1000;
System.out.println("hi," + n)
```

### $$O(n)$$

```java
for (int i = 1; i <= n; i++) {
  System.out.println("hi," + i)
}
```

### $$O(n^2)$$

```bash
for (int i = 1; i <= n; i++) {
	for (int j = 1; j <= n; j++) {
		System.out.println("hi, i " + i + " and y " + y)
	}
}
```

### $$O(k^n)$$

```java
for (int i = 1; i <= Math.pow(2, n); i++) {
  System.out.println("hi, " + i)
}
```

### $$O(n!)$$

```java
for (int i = 1; i <= factorial(n); i++) {
  System.out.println("hi, " + i)
}
```

### O(logn)、O(nlogn)

```c
// 例子1
for (int i = 1; i < n; i = i * 2) {
  System.out.println("hi," + i)
}

// 例子2
i=1;
while (i <= n)  {
  i = i * 3;
}

```

例子1的时间复杂度 $$O(log_2n)$$， 例子2的时间复杂度为$$O(log_3n)$$，不管是以 2 为底、以 3 为底，还是以 10 为底，我们可以把所有对数阶的时间复杂度都记为 O(logn)

$log_3n=log_32*log_2n$
$C=log_32$
$O(log_3n)=O(C*log_2n)$
$O(Cf(n))=O(f(n))$


如果一段代码的时间复杂度是 O(logn)，我们循环执行 n 遍，时间复杂度就是 O(nlogn) 了。而且，O(nlogn) 也是一种非常常见的算法时间复杂度。比如，归并排序、快速排序的时间复杂度都是 O(nlogn)。

>  **换底公式**
> $log_ab=\frac{log_cb}{log_ca} \qquad (c>0 \ c\neq1)$
> **证明**
> 设 $log_ab=x$
> $b = a^x$
> $log_cb=log_ca^x$
> $log_cb=xlog_ca$
> $x=\frac{log_cb}{log_ca}$


$log_3n=log_32*log_2n$ 

$log_32=\frac{log_2n}{log_3n}$

![时间复杂度图](./assets/images/时间复杂度图.png)



![common data structure operations](./assets/images/common data structure operations.png)

> https://www.bigocheatsheet.com/

# 数组 Array

数组数据结构（array data structure），简称数组（Array），是由相同类型的元素（element）的集合所组成的数据结构，分配一块连续的内存来存储。利用元素的索引（index）可以计算出该元素对应的存储地址。

![array](./assets/images/array.png)

# 链表

链表（Linked list）是一种常见的基础数据结构，是一种线性表，但是并不会按线性的顺序存储数据，而是在每一个节点里存到下一个节点的指针(Pointer)。由于不必须按顺序存储，链表在插入的时候可以达到O(1)的复杂度，比另一种线性表顺序表快得多，但是查找一个节点或者访问特定编号的节点则需要O(n)的时间，而顺序表相应的时间复杂度分别是O(logn)和O(1)。

![](./assets/images/链表.png)



# 堆栈 Stack

堆栈（英语：stack）又称为栈或堆叠，是计算机科学中的一种抽象数据类型，只允许在有序的线性数据集合的一端（称为堆栈顶端，英语：top）进行加入数据（英语：push）和移除数据（英语：pop）的运算。因而按照后进先出（LIFO, Last In First Out）的原理运作。

常与另一种有序的线性数据集合队列相提并论。

堆栈常用一维数组或链表来实现。

![](./assets/images/stack.jpeg)



# 队列 Queue

队列是线性表的一种，在操作数据元素时，和栈一样，有自己的规则：使用队列存取数据元素时，数据元素只能从表的一端进入队列，另一端出队列。

![](./assets/images/queue.png)



# 排序

## 选择排序(Selection)

首先，找到数组中最小的那个元素，其次，将它和数组的第一个元素交换位置（如果第一个元素就是最小元素那么它就和自己交换）。再次，在剩下的元素中找到最小的元素，将它与数组的第二个元素交换位置。如此往复，直到将整个数组排序。

### 特点

* 运行时间和输入无关 为了找出最小的元素而扫描一遍数组并不能为下一遍扫描提供什么信息。一个已经有序的数组或是主键全部相等的数组和一个元素随机排列的数组所用的排序时间竟然一样长！
* 数据移动是最少的。每次交换都会改变两个数组元素的值，因此选择排序用了次交换——交换次数和数组的大小是线性关系

![image-20210121155627167](./assets/images/image-20210121155627167.png)

![Selection-Sort-example](assets/images/Selection-Sort-example.gif)

## 插入排序(Insertion)

取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入，将其余所有元素在插入之前都向右移动一位。并保证已排序区间数据一直有序。重复这个过程，直到未排序区间中元素为空，算法结束。

![image-20210121155540739](./assets/images/image-20210121155540739.png)

![Insertion-sort-example](assets/images/Insertion-sort-example.gif)

## 希尔排序(Shell)

希尔排序的思想是使数组中任意间隔为h的元素都是有序的。这样的数组被称为h有序数组。换句话说，一个h有序数组就是h个互相独立的有序数组编织在一起组成的一个数组。在进行排序时，如果h很大，我们就能将元素移动到很远的地方，为实现更小的h有序创造方便。用这种方式，对于任意以1结尾的h序列，我们都能够将数组排序

![image-20210121170204264](./assets/images/image-20210121170204264.png)

An example run of Shellsort with gaps 5, 3 and 1 is shown below.

| Input data      | 62   | 83   | 18   | 53   | 07   | 17   | 95   | 86   | 47   | 69   | 25   | 28   |
| --------------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| After 5-sorting | 17   | 28   | 18   | 47   | 07   | 25   | 83   | 86   | 53   | 69   | 62   | 95   |
| After 3-sorting | 17   | 07   | 18   | 47   | 28   | 25   | 69   | 62   | 53   | 83   | 86   | 95   |
| After 1-sorting | 07   | 17   | 18   | 25   | 28   | 47   | 53   | 62   | 69   | 83   | 86   | 95   |

[参考理解](https://www.cs.usfca.edu/~galles/visualization/ComparisonSort.html)



## 归并排序(Merge)

将一个数组排序，可以先（递归地）将它分成两半分别排序，然后将结果归并起来。归并排序最吸引人的性质是它能够保证将任意长度为$N$的数组排序所需时间和$NlogN$成正比；它的主要缺点则是它所需的额外空间和$N$成正比。

![image-20210122173539722](./assets/images/image-20210122173539722.png)







# 缓存算法（页面置换算法）

* FIFO（First In，First Out）先进先出策略 
* LFU（Least Frequently Used）最少使用策略
* LRU（Least Recently Used）最近最少使用策略



# Tree

## Merkle Tree

Merkle Tree，通常也被称作Hash Tree，顾名思义，就是存储hash值的一棵树。Merkle树的叶子是数据块(例如，文件或者文件的集合)的hash值。非叶节点是其对应子节点串联字符串的hash。

Merkle Tree的主要作用是当我拿到Top Hash的时候，这个hash值代表了整颗树的信息摘要，当树里面任何一个数据发生了变动，都会导致Top Hash的值发生变化。



## Trie Tree

Trie树，又称字典树，单词查找树或者前缀树，是一种用于快速检索的多叉树结构，如英文字母的字典树是一个26叉树，数字的字典树是一个10叉树。

Trie树的基本性质可以归纳为：

- 根节点不包含字符，除根节点以外每个节点只包含一个字符。
- 从根节点到某一个节点，路径上经过的字符连接起来，为该节点对应的字符串。
- 每个节点的所有子节点包含的字符串不相同。



## MPT Tree

> 一种经过改良的、融合了**默克尔树和前缀树**两种树结构优点的数据结构，以太坊中，MPT是一个非常重要的数据结构，在以太坊中，帐户的交易信息、状态以及相应的状态变更，还有相关的交易信息等都使用MPT来进行管理，其是整个数据存储的重要一环。交易树，收据树，状态树都是采用的MPT结构。

1. 存储任意长度的key-value键值对数据；
2. 提供了一种快速计算所维护数据集哈希标识的机制；
3. 提供了快速状态回滚的机制；
4. 提供了一种称为默克尔证明的证明方法，进行轻节点的扩展，实现简单支付验证；



# 限流算法

## 令牌桶算法
令牌桶算法是一个存放固定容量令牌的桶，按照固定速率往桶里添加令牌

- 假设限制速率为2r/s，则按照500毫秒的速率往令牌桶添加令牌
- 令牌桶最多存放b个令牌，当桶满的时候，新的令牌会被抛弃或拒绝
- 当一个n个字节大小的数据包到达，将从桶中删除n个令牌，接着发送数据包到网络上
- 如果桶中的流量不足n个，则不会删除令牌，该数据将被限流（要么丢弃，要么缓存）


## 漏桶算法
漏桶算法是网络世界中流量整形（Traffic Shaping）或速率限制（Rate Limiting）时经常使用的一种算法
- 一个固定容量的漏桶，按照常量固定速率流出水滴
- 如果桶是空的，则不需要流出水滴
- 可以以任意速率流入水滴到漏桶
- 如果流出水滴超出了桶的容量，则流入的水滴溢出（被丢弃），则漏桶的容量是不变的



# 共识算法

共识是对某个提案(proposal)达成一致意见的过程，分布式系统中提案的含义十分宽泛，包括事件发生顺序、谁是leader等。区块链系统中，共识是各个共识节点对交易执行结果达成一致的过程。

## 共识算法分类

### CFT类算法

普通容错类算法，当系统出现网络、磁盘故障，服务器宕机等普通故障时，仍能针对某个提议达成共识，经典的算法包括Paxos、Raft等，这类算法性能较好、处理速度较快、可以容忍不超过一半的故障节点

### BFT类算法

拜占庭容错类算法，除了容忍系统共识过程中出现的普通故障外，还可容忍部分节点故意欺骗(如伪造交易执行结果)等拜占庭错误，经典算法包括PBFT等，这类算法性能较差，能容忍不超过三分之一的故障节点。

## Paxos

**Paxos算法**是[莱斯利·兰伯特](https://zh.wikipedia.org/wiki/莱斯利·兰伯特)（Leslie Lamport）于1990年提出的一种基于消息传递且具有高度容错特性的共识（consensus）算法

需要注意的是，Paxos常被误称为“一致性算法”。但是“[一致性（consistency）](https://zh.wikipedia.org/wiki/线性一致性)”和“共识（consensus）”并不是同一个概念。Paxos是一个共识（consensus）算法。

## PBFT

**PBFT**(Practical Byzantine Fault Tolerance)共识算法可以在少数节点作恶(如伪造消息)场景中达成共识，它采用签名、签名验证、哈希等密码学算法确保消息传递过程中的防篡改性、防伪造性、不可抵赖性，并优化了前人工作，将拜占庭容错算法复杂度从指数级降低到多项式级别，在一个由(3*f+1)个节点构成的系统中，只要有不少于(2*f+1)个非恶意节点正常工作，该系统就能达成一致性，如：7个节点的系统中允许2个节点出现拜占庭错误。

## Raft

Raft（Replication and Fault Tolerant）是一个允许网络分区（Partition Tolerant）的一致性协议，它保证了在一个由N个节点构成的系统中有(N+1)/2（向上取整）个节点正常工作的情况下的系统的一致性，比如在一个5个节点的系统中允许2个节点出现非拜占庭错误，如节点宕机、网络分区、消息延时。Raft相比于Paxos更容易理解，且被证明可以提供与Paxos相同的容错性以及性能，其详细介绍可见[官网](https://raft.github.io/)及[动态演示](http://thesecretlivesofdata.com/raft/)。

### 应用
[Etcd](./etcd.md)

## POW

POW（Proof of Work），工作量证明，引入了对一个特定值的计算工作。

比特币采用的共识算法就是POW，矿工们在挖一个新的区块时，必须对SHA-256密码散列函数进行运算，区块中的随机散列值以一个或多个0开始。随着0数目的上升，找到这个解所需要的工作量将呈指数增长，矿工通过反复尝试找到这个解。

### SHA 无记忆，无进展

SHA(Secure Hash Algorithm) 在统计学和概率上以[无记忆性（memoryless）](https://en.wikipedia.org/wiki/Memorylessness) 闻名。所谓无记忆性，就是无论之前发生了什么，都不影响这一次事件发生的概率。

无进展（progress-free）的问题，无记忆性又是必要条件。progress-free 意味着当矿工试图通过对 [nonces](https://en.bitcoin.it/wiki/Nonce) 进行迭代解决难题时，每一次尝试都是一个独立事件，无论之前已经算过了多少次，每次尝试找到答案的概率是固定的。换句话来说，每次尝试，参与者都不会离“答案”越近，或者说有任何进展（progress）。就下一次尝试而言，一个已经算了一年的矿工，与上一秒刚开始算的矿工，算出来的概率是一样的。

在指定时间内，给定一个难度，找到答案的概率*唯一地由所有参与者能够迭代哈希的速度决定*。与之前的历史无关，与数据无关，只跟算力有关。



## PoS

PoS（Proof-of-Stake）权益证明机制，在PoS共识中，节点争夺记账权依靠的不是算力而是权益（代币）。PoS同样需要计算哈希值，但与PoW不同的是，不需要持续暴力计算寻找nonce值

## DPoS

委托权益证明 Delegate Proof of Stake（DPoS）， 是由PoS演化而来的。持币用户通过抵押代币获得选票，以投票的方式选出若干的节点作为区块生产者，代表持币用户履行产生区块的义务。DPoS与公司董事会制度相似，让持币用户将生产区块的工作委托给更有能力胜任的专业人士去完成，同时也能享受参与出块获得的奖励。







# 加密算法

## DSA



## ECDSA

`ECDSA`是**[Elliptic Curve Digital Signature Algorithm](https://en.wikipedia.org/wiki/Elliptic_Curve_Digital_Signature_Algorithm)**的简称，主要用于对数据（比如一个文件）创建数字签名，以便于你在不破坏它的安全性的前提下对它的真实性进行验证。





# 参考

https://www.cs.usfca.edu/~galles/visualization/Algorithms.html			

https://visualgo.net/zh

