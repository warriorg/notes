#### 1. 考虑用静态工厂方法代替构造器
> 静态方法的  
> *优势*  
> 1. 有名称，便于理解
> 2. 不用每次都创建一个新对象
> 3. 可以返回类型的任何子类型对象		
> 4. 创建参数化类型实例的时候，代码更加简洁  

>*缺点*  
> 1. 类如果不包含共有或者受保护的构造器，就不能被子类化  
> 2. 与其他人静态方法无区别

#### 2. 遇到多个构造器时要考虑用构建器
#### 3. 用私有构造器或者枚举类型强化Singleton属性
>* 枚举类实现其实省略了private类型的构造函数  
>* 枚举类的域(field)其实是相应的enum类型的一个实例对象

```java
//Define what the singleton must do.
public interface MySingleton {
    public void doSomething();
}
private enum Singleton implements MySingleton {
    /**
     * The one and only instance of the singleton.
     *
     * By definition as an enum there MUST be only one of these and it is inherently thread-safe.
     */
    INSTANCE {
                @Override
                public void doSomething() {
                    // What it does.
                }
            };
}
public static MySingleton getInstance() {
    return Singleton.INSTANCE;
}
```
>https://stackoverflow.com/questions/23721115/singleton-pattern-using-enum-version

#### 4. 通过私有构造器强化不可实例化的能力
#### 5. 避免创建不必要的对象
#### 6. 消除过期的对象引用
#### 7. 避免使用 finalizer 方法
#### 8. 重写 equals 时请遵守通用约定
#### 9. 重写 equals 时总要重写 hashCode
#### 10. 始终重写 toString
#### 11. 谨慎重写 clone
#### 12. 考虑实现 Comparable 接口
#### 13. 使类和成员的可访问性最小化
#### 14. 在共有类中使用访问方法而非共有域
> 使用getter setter 方法
#### 15. 使可变性最小化
> 每个实例中包含的所有信息都必须在创建该实例的时候就提供，并在对象的整个生命周期(lifetime)內固定不变
#### 16. 符合优先于继承
> 继承打破了封装性
#### 17. 要么为继承而设计，并提供文档说明，要么就禁止继承
#### 18. 接口优于抽象类
> 现有的类可以很容易被更新，以实现新的接口
> 接口时定义mixin（混合类型) 的理想选择
> 接口允许我们构造非层次接口的类型框架
#### 19. 接口只用于定义类型
> 常量接口不满足次条件，常量接口是对接口的不良使用
#### 20. 类层次优于标签类
#### 21. 用函数对象表示策略
#### 22. 优先考虑静态成员类
> 嵌套类(nested class)
> * 静态成员类  
> * 非静态成员类  
> * 匿名类   
> * 局部类  
>
>除了第一种外 其他三种被称为内部类
#### 23. 请不要在新代码中使用原生态类型
#### 24. 消除非受检警告
#### 25. 列表优先于数组
> 数组与泛型相比，数组是协变的(covariant)、具体化的（reified）
#### 26. 优先考虑泛型
#### 27. 优先考虑泛型方法
#### 28. 利用有限制通配符来提升API的灵活性
#### 29. 优先考虑类型安全的异构容器
#### 30. 用 enum 代替 int 常量
> 枚举天生就不可变
####31. 用实例域代替序数
> 不要根据枚举的序数导出与关联的值，而是要将它保存在一个实例域
> ```java
>public enum Ensemble {
>   SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
>   SEXTET(6), ETPTET(7), OCTET(8), DOUBLE_QUARTET(8)
>
>   private final int numberOfMusicians;
>   Ensemble(int size) { this.numberOfMusicians = size; }
>   public int numberOfMusicians() { return numberOfMusicians; }
>}
>```
#### 32. 用 EnumSet 代替位域
> 位域(bit field) 用OR位运算将几个常量合并到一个集合中
#### 33. 用 EnumMap 代替序数索引
#### 34. 用接口模拟可伸缩的枚举
#### 35. 注解优先于命名模式
#### 36. 坚持使用 Override 注解
#### 37. 用标记接口定义类型
> 标记接口 (marker interface) 没有包含方法声明的接口，而只是指明一个类实现了具有某种属性的接口
#### 38. 检查参数的有效性
#### 39. 必要时进行保护醒拷贝
#### 40. 谨慎设计方法签名
> * 谨慎地选择方法的名称   
> * 不要过于追求提供便利的方法  
> * 避免过长的参数列表  
#### 41. 慎用重载
#### 42. 慎用可变参数
#### 43. 返回零长度的数组或者集合，而不是null
#### 44. 为所有导出的API元素编写文档注释
#### 45. 将局部变量的作用域最小化
> * 用到在声明
> * 声明时都应该包含一个初始化表达式
#### 46. for-each 循环优先于传统的 for 循环
#### 47. 了解和使用类库
#### 48. 如果需要精确的答案，请避免使用 float 和 double
> 使用 BigDecimal、int、long 进行货币计算
#### 49. 基本类型优先于装箱基本类型
#### 50. 如果其他类型更合适，则尽量避免使用字符串
> * 字符串不适合代替其他的值类型
> * 字符串不适合代替枚举类型
> * 字符串不适合代替聚集类型
> * 字符串也不适合代替能力表 (capabilities)
#### 51. 当心字符串连接的性能
#### 52. 通过接口引用对象
```java
// good
List<Subscriber> subscribers = new Vector<Subscriber>()
// bad
Vector<Subscriber> subscribers = new Vector<Subscriber>()
```
#### 53. 接口优先于反射机制			
> 反射的弊端
> * 丧失了编译时类型检查的好处
> * 执行反射访问所需要的代码笨拙和冗长
> * 性能损失
#### 54. 谨慎地使用本地方法
> Java Native Interface (JNI) 允许调用本地方法(native method)
#### 55. 谨慎的进行优化
#### 56. 遵守普遍接受的命名惯例
#### 57. 只针对异常的情况才使用异常
#### 58. 对可恢复的情况使用受检异常，对编程错误使用运行时异常
> 三种可抛出错误(throwable)
> * 受检的异常（checked exception） 希望调用者能适当地恢复
> * 运行时异常 (runtime exception) 前提违例 (precondition violation)
> * 错误（error）
#### 59. 避免不必要地使用受检的异常
#### 60. 优先使用标准的异常
#### 61. 抛出与抽象相对应的异常
#### 62. 每个方法抛出的异常都要有文档
#### 63. 在细节消息中包含能捕获失败的信息
#### 64. 努力使失败保持原子性
> 失败方法调用应该使对象保持在被调用之前的状态
#### 65. 不要忽略异常
#### 66. 同步访问共享的可变数据
#### 67. 避免过度同步
#### 68. executor 和 task 优先于线程
#### 69. 并发工具优先于wait 和 notify
#### 70. 线程安全性的文档化
#### 71. 慎用延迟初始化
#### 72. 不要依赖线程调度器
#### 73. 避免使用线程组
#### 74. 谨慎地实现 Serializable 接口
#### 75. 考虑使用自定义序列化形式
#### 76. 保护性地编写 readObject 方法
#### 77. 对于实例控制，枚举类型优先于 readResolve
#### 78. 考虑用序列化代理代替序列化实例
> 为可序列化的类设计一个私有的静态嵌套类，精确地表示外围类的实例的逻辑状态。这个嵌套类被称作序列化代理(serialization proxy)
