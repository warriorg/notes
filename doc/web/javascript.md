---

---
# Javascript
**对象** 是基于原型(prototype-based)

**原型链（作用域链）** 定义了对象到哪查找它的属性定义

**自执行匿名函数** 


```javascript
(function(){
	//do something
})()
```
> 用来控制变量的作用域，阻止变量泄漏到代码的其他地方

**闭包** 是阻止垃圾回收器将变量从内存中移除的方法，使得在创建变量的执行环境的外貌能够访问到该变量

**基于类** 关注类和类之间的关系开发模型。类与类之间有可能会形成继承、组合等关系。类又往往与语言的类型系统整合，形成一定编译时能力

**基于原型** 提倡关注一系列对象实例行为，而后才去关心如何将这些对象，划分到最近的使用方式相似的原型对象





# 原型

* 如果所有对象都有私有字段 [[prototype]]，就是对象的原型；
* 读一个属性，如果对象本身没有，则会继续访问对象的原型，直到原型为空或者找到为止。

* Object.create 根据指定的原型创建新对象，原型可以是 null；
* Object.getPrototypeOf 获得一个对象的原型；
* Object.setPrototypeOf 设置一个对象的原型。



> 1. 每个函数都是 Function 类型的实例
> 2. 原型 而实例的 __proto__ 和 [[Prototype]] 都是指向原型，感觉5.1、5.2部分增加了知识的复杂度。



## 继承

```javascript
function BaseAPI() {
  this.http = fetch
}

function UserAPI() {
  BaseAPI.call(this)
}

UserAPI.prototype = Object.create(BaseAPI.prototype)
UserAPI.prototype.constructor = UserAPI
```

# 模块化方案
* CommonJS: 主要是 **Node.js** 使用，通过 `require` **同步**加载模块，`exports` 导出内容。
* AMD 主要是**浏览器端**使用，通过 `define` 定义模块和依赖，`require` **异步**加载模块，推崇**依赖前置**。
* CMD: 和 AMD 比较类似，主要是**浏览器端**使用，通过 `require` **异步**加载模块，`exports` 导出内容，推崇**依赖就近**。
* UMD: 通用模块规范，是 CommonJS、AMD 两个规范的大融合，是**跨平台**的解决方案。
* ESM 官方模块化规范，**现代浏览器原生支持**，通过 `import` **异步**加载模块，`export` 导出内容。



## ES6 模块与 CommonJS 模块的差异

 ES6 模块与 CommonJS 模块完全不同,它们有三个重大差异。

- CommonJS 模块输出的是一个值的拷贝，ES6 模块输出的是值的引用。
  - CommonJS 模块输出的是值的拷贝，也就是说，一旦输出一个值，模块内部的变化就影响不到这个值。
  - ES6 模块的运行机制与 CommonJS 不一样。JS 引擎对脚本静态分析的时候，遇到模块加载命令`import`，就会生成一个只读引用。等到脚本真正执行时，再根据这个只读引用，到被加载的那个模块里面去取值。换句话说，ES6 的`import`有点像 Unix 系统的“符号连接”，原始值变了，`import`加载的值也会跟着变。因此，ES6 模块是动态引用，并且不会缓存值，模块里面的变量绑定其所在的模块。
- CommonJS 模块是运行时加载，ES6 模块是编译时输出接口。
  - CommonJS 加载的是一个对象（即`module.exports`属性），该对象只有在脚本运行完才会生成。而 ES6 模块不是对象，它的对外接口只是一种静态定义，在代码静态解析阶段就会生成。
- CommonJS 模块的`require()`是同步加载模块，ES6 模块的`import`命令是异步加载，有一个独立的模块依赖的解析阶段。
