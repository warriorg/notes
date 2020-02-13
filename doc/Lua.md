## Lua学习笔记

### if - else 

```lua
score = 90
if score == 100 then
	print("Very good! Your score is 100")
elseif score >= 60 then
	-- 注意，elseif 是连在一起的
	print("Congratulations, you have passed it")
else 
	print("Sorry, you do not pass the exam!")
end
```

### While 型控制结构

```lua
while 表达式 do
--body
end
```
lua 没有提供`continue`,但是提供`break`跳出当前循环

```lua
local t = {1, 3, 5, 8, 11, 18, 21}
local i 
for i, v in ipairs(t) do
	if 11 == v then
		print("index[".. i .. "] have right value[11]")
		break
	end
end
```

#### repeat 控制结构
repeat 控制结构类似于其他语言中的do-while 但是控制方式是刚好相反的。简单点说，执行repeat循环体后，知道until的条件为真时才结束。

```lua
x = 10
repeat 
	print(x)
until false
--这段代码将是一个死循环，因为until的条件一直为假，循环不会结束
```

#### for控制结构
for 语句有两种形式: numeric(数字) for 和 generic(范型)for 
#####numeric for

```lua
for var = begin, finish, step do
	--body
end
```
1. var 从begin变化到 finish, 每次变化都以step作为步长递增 var
2. begin、finish、step 三个表达式只会在循环开始时执行一次
3. 第三个表达式step是可选的，默认为1

#####generic for

```lua
local a = {"a", "b", "c", "d"}
for i, v in ipairs(a) do
	print("index:", i, "value:", v)
end
```
>`ipairs` 用于遍历数组的迭代器函数。每次循环中，i 会被赋予一个索引值，同事v被赋予一个对应于该索引的数组元素值。

##	函数
使用函数的好处：

1. 降低程序的复杂性：把函数作为一个独立的模块，写完函数后，只关心它的功能，而不再考虑函数里面的细节。
2. 增加程序的可读性：当我们调用 math.max() 函数时，很明显函数是用于求最大值的，实现细节就不关心了。
3. 避免重复代码：当程序中有相同的代码部分时，可以把这部分写成一个函数，通过调用函数来实现这部分代码的功能，节约空间，减少代码长度。
4. 隐含局部变量：在函数中使用局部变量，变量的作用范围不会超出函数，这样它就不会给外界带来干扰。

###函数定义
```lua
function function_name (arc) --arc 参数列表，可以为空
	--body
end
```
等价于

```lua
function_name = function (arc)
	--body
end
```
上面的语法定义了一个全局函数，全局函数本质上就是函数类型的值赋给了一个全局变量。由于全局变量一般会污染全局名字空间，同时也有性能损耗（即查询全局环境表的开销），因此我们应当尽量使用“局部函数”，其记法是类似的，只是开头加上 local 修饰符：

```lua
local function function_name (arc)
	--body
end
```
定义函数要注意几点：

1. 利用名字来解释函数、变量的目的，使人通过名字就能看出来函数、变量的作用。
2. 每个函数的长度要尽量控制在一个屏幕内，一眼可以看明白。
3. 让代码自己说话，不需要注释最好。

### 参数传递

1. Lua 函数大部分是按值传递的。
2. 函数参数是table类型时，为引用传
3. Lua 在调用时传入参数不同时，会自动调整实参个数，调整规则：若实参个数大于行参，从左向右，多余实参忽略；若实参个数小于行参，从左向右，没有被实参初始化的行参会被初始化为nil

### 函数返回值
  lua 容许函数返回多个值，返回多个值时，值之间用","分割

## 模块
`require`用于加载和缓存模块。

```lua
//nbzhou.lua
local foo={}

local function getName()
	return "NB周"
end

function foo.greeting()) 
	print("hello " .. getName())
end

return foo
```

```lua
//main.lua
local fp = require(nbzhou)
fp.greeting()
```

## table 库
table 库由一些辅助函数构成，这些函数将table作为数组来操作

>lua中，数组下标从1开始计数

## 元表
元表 (metatable) 的表现行为类似于 C++ 语言中的操作符重载，例如我们可以重载 "__add" 元方法 (metamethod) ，来计算两个 Lua 数组的并集；或者重载 "__index" 方法，来定义我们自己的 Hash 函数。

* setmetatable(table, metatable)：此方法用于为一个表设置元表。
* getmetatable(table)：此方法用于获取表的元表对象。






