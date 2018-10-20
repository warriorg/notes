### 控制反转（Inversion of Control，IoC）
* 常见另一种叫法**依赖注入（Dependency Injection，DI）**, 还有一种叫法**依赖查找(Dependency Lookup)** 对象在被创建的时候，由IoC容器注入，对象的创建的控制权由IoC容器负责

* Class A中用到了Class B的对象b，一般情况下，需要在A的代码中显式的new一个B的对象。		
采用依赖注入技术之后，A的代码只需要定义一个私有的B对象，不需要直接new来获得这个对象，而是通过相关的容器控制程序来将B对象在外部new出来并注入到A类里的引用中。而具体获取的方法、对象被获取时的状态由配置文件（如XML）来指定。

#### spring 应用上下文
* AnnotationConfigApplicationContext：从一个或多个基于Java的配置类中加载Spring应用上下文。 
* AnnotationConfigWebApplicationContext：从一个或多个基于Java的配置类中加载Spring Web应用上下文。
* ClassPathXmlApplicationContext：从类路径下的一个或多个XML配置文件中加载上下文定义，把应用上下文的定义文件作为类资源。
* FileSystemXmlapplicationcontext：从文件系统下的一个或多个XML配置文件中加载上下文定义。 
* XmlWebApplicationContext：从Web应用下的一个或多个XML配置文件中加载上下文定义。



