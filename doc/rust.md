# Rust

## cargo



## 变量

使用 `let` 语法可以把值（如字面常量）绑定到变量。

```rust 
let an_integer = 1u;
let a_boolean = true;
let unit = ();
```

### 可变性

变量默认是不可变（immutable）的。添加 `mut` 关键字可将变量定义为可变的（mutable）。

```rust
 let _immutable_variable = 1i;
 let mut mutable_variable = 1i;
```

### 作用域和覆盖

变量具有局部作用域，被限制在其所属的代码块（block）内。（代码块指被花括号{}围住的语句组合。） Rust允许[变量屏蔽（variable shadowing）](https://en.wikipedia.org/wiki/Variable_shadowing)。

### 先声明再使用

可以先声明变量，然后再初始化。但是不常见，因为这样会导致用到未初始化的变量。

## 类型

Rust通过静态类型检查实现了类型安全。变量可以在声明时指定类型，然而多数情况下可以省略类型，编译器能够通过上下文自动推导出变量类型，大幅简化了程序员的负担。

```rust
fn main() {
    // Type annotated variable
    let a_float: f64 = 1.0;
    // This variable is an `int`
    let mut an_integer = 5i;
    // Error! The type of a variable can't be changed
    an_integer = true;
}
```

Rust语言的内置类型（primitive types）总结：

- 有符号正数：`i8`, `i16`, `i32`, `i64` 和 `int` (机器字长)
- 无符号正数: `u8`, `u16`, `u32`, `u64` 和 `uint` (机器字长)
- 浮点数: `f32`, `f64`
- `char` Unicode字符（Scalars）例如 `'a'`, `'α'` and `'∞'` (4字节长)
- `bool` 逻辑类型，取值可为 `true` 和 `false` 二者之一
- 空元组类型 `()`, 其唯一的值也是 `()` 

### 转换

Rust不提供基础类型之间的隐式类型转换，只能使用 `as` 关键字显式转换类型。

## 操作符

## 条件语句

## 循环

## 函数

## 类和结构体



## 错误处理

## 测试

