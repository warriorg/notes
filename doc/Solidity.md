# 简介

Solidity 是一门面向合约的、为实现智能合约而创建的高级编程语言。这门语言受到了 C++，Python 和 Javascript 语言的影响，设计的目的是能在以太坊虚拟机（EVM）上运行。



# 类型

## 值类型

### bool

可能的取值为字面常量值 `true` 和 `false` 。

### 整型

`int` / `uint` ：分别表示有符号和无符号的不同位数的整型变量。 支持关键字 `uint8` 到 `uint256` （无符号，从 8 位到 256 位）以及 `int8` 到 `int256`，以 `8` 位为步长递增。 `uint` 和 `int` 分别是 `uint256` 和 `int256` 的别名。

### 定长浮点型

`fixed` / `ufixed`：表示各种大小的有符号和无符号的定长浮点型。 在关键字 `ufixedMxN` 和 `fixedMxN` 中，`M` 表示该类型占用的位数，`N` 表示可用的小数位数。 `M` 必须能整除 8，即 8 到 256 位。 `N` 则可以是从 0 到 80 之间的任意数。 `ufixed` 和 `fixed` 分别是 `ufixed128x19` 和 `fixed128x19` 的别名。

### 地址类型 Address

地址类型有两种形式，他们大致相同：

> - `address`：保存一个20字节的值（以太坊地址的大小）。
> - `address payable` ：可支付地址，与 `address` 相同，不过有成员函数 `transfer` 和 `send` 。

这种区别背后的思想是 `address payable` 可以接受以太币的地址，而一个普通的 `address` 则不能。

类型转换:

允许从 `address payable` 到 `address` 的隐式转换，而从 `address` 到 `address payable` 必须显示的转换, 通过 `payable(<address>)` 进行转换。

`address` 允许和 `uint160`、 整型字面常量、`bytes20` 及合约类型相互转换。

只能通过 `payable(...)` 表达式把 `address` 类型和合约类型转换为 `address payable`。 只有能接收以太币的合约类型，才能够进行此转换。例如合约要么有 `receive` 或可支付的回退函数。 注意 `payable(0)` 是有效的，这是此规则的例外。

### 合约类型

每一个 `contract`定义都有他自己的类型。

您可以隐式地将合约转换为从他们继承的合约。 合约可以显式转换为 `address` 类型。

只有当合约具有 接收receive函数 或 payable 回退函数时，才能显式和 `address payable` 类型相互转换 转换仍然使用 `address(x)` 执行， 如果合约类型没有接收或payable 回退功能，则可以使用 `payable(address(x))` 转换为 `address payable` 。

### 定长字节数组

`bytes1`， `bytes2`， `bytes3`， …， `bytes32`。

### 变长字节数组

- `bytes`:

  变长字节数组，它并不是值类型。

- `string`:

  变长 UTF-8 编码字符串类型，并不是值类型。

### 地址字面常量

### 有理数和整数字面常量

整数字面常量由范围在 0-9 的一串数字组成，表现成十进制。 例如，69 表示数字 69。 Solidity 中是没有八进制的，因此前置 0 是无效的。

字符串字面常量是指由双引号或单引号引起来的字符串（`"foo"` 或者 `'bar'`）。 它们也可以分为多个连续的部分（`"foo" "bar"` 等效于`”foobar”`）

字符串字面常量只能包含可打印的ASCII字符，这意味着他是介于0x1F和0x7E之间的字符。

### Unicode 字面常量

常规字符串文字只能包含ASCII，而Unicode文字（以关键字unicode为前缀）可以包含任何有效的UTF-8序列。 它们还支持与转义序列完全相同的字符作为常规字符串文字。

```sol
string memory a = unicode"Hello 😃";
```

### 枚举类型

枚举是在Solidity中创建用户定义类型的一种方法。 它们是显示所有整型相互转换，但不允许隐式转换。 从整型显式转换枚举，会在运行时检查整数时候在枚举范围内，否则会导致异常（ Panic异常）。 枚举需要至少一个成员,默认值是第一个成员，枚举不能多于 256 个成员。

### 函数类型

函数类型是一种表示函数的类型。可以将一个函数赋值给另一个函数类型的变量，也可以将一个函数作为参数进行传递，还能在函数调用中返回函数类型变量。 函数类型有两类：

> - *内部（internal）* 函数类型
> - *外部（external）* 函数类型

内部函数只能在当前合约内被调用（更具体来说，在当前代码块内，包括内部库函数和继承的函数中），因为它们不能在当前合约上下文的外部被执行。 调用一个内部函数是通过跳转到它的入口标签来实现的，就像在当前合约的内部调用一个函数。

外部函数由一个地址和一个函数签名组成，可以通过外部函数调用传递或者返回。

```sol
function (<parameter types>) {internal|external} [pure|constant|view|payable] [returns (<return types>)]
```



# 合约

## 函数

### View 函数

可以将函数声明为 `view` 类型，这种情况下要保证不修改状态。

```solidity
pragma solidity ^0.4.16;

contract C {
    function f(uint a, uint b) public view returns (uint) {
        return a * (b + 42) + now;
    }
}
```

> `constant` 是 `view` 的别名。
>
> Getter 方法被标记为 `view`。
>
> ==编译器没有强制 `view` 方法不能修改状态。==

### **Pure** 函数

函数可以声明为 `pure` ，在这种情况下，承诺不读取或修改状态。

```solidity
pragma solidity ^0.4.16;

contract C {
    function f(uint a, uint b) public pure returns (uint) {
        return a * (b + 42);
    }
}
```

> ==编译器没有强制 `pure` 方法不能读取状态。==

### Fallback 函数

合约可以有一个未命名的函数。这个函数不能有参数也不能有返回值。 如果在一个到合约的调用中，没有其他函数与给定的函数标识符匹配（或没有提供调用数据），那么这个函数（fallback 函数）会被执行。

### 函数重载

合约可以具有多个不同参数的同名函数。这也适用于继承函数。



