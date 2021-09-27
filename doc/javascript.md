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

