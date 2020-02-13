
# MyBatis 

* 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。
* 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。
* 使用简单的 XML 或注解来配置和映射原生信息，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。





## 动态 SQL

### if 

动态 SQL 通常要做的事情是根据条件包含 where 子句的一部分。比如：

```
<select id="findActiveBlogWithTitleLike"
     resultType="Blog">
  SELECT * FROM BLOG
  WHERE state = ‘ACTIVE’
  <if test="title != null">
    AND title like #{title}
  </if>
</select>
```

mybatis是用OGNL表达式来解析的，在OGNL的表达式中，’1’会被解析成字符，java是强类型的，char 和 一个string 会导致不等，所以**单个的字符要写到双引号里面或者使用.toString()。例如`<if test='title != "1"'>`或者改为`<if test="title != '1'.toString()">`


# MyBatis Generator		
[下载地址](https://github.com/mybatis/generator)		

```bash
# 命令行启动
java -jar mybatis-generator-core-x.x.x.jar -configfile generatorConfig.xml -overwrite
# maven 方式启动
mvn mybatis-generator:generate

```

[generatorConfig.xml](assets/files/generatorConfig.xml)	

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!-- 引入配置文件 -->
    <!--<properties resource="xxx.properties"></properties>-->

    <!-- 指定数据库连接驱动jar地址 -->
    <classPathEntry location="~/.m2/repository/com/microsoft/sqlserver/sqljdbc4/4.0/sqljdbc4-4.0.jar"></classPathEntry>

    <!-- 数据库配置 一个数据库一个 context -->
    <context id="sqlserverTables" targetRuntime="MyBatis3">
        <!-- 生成的Java文件的编码 -->
        <property name="javaFileEncoding" value="UTF-8" />

        <!-- 格式化java代码 -->
        <property name="javaFormatter" value="org.mybatis.generator.api.dom.DefaultJavaFormatter" />

        <!-- 格式化XML代码 -->
        <property name="xmlFormatter" value="org.mybatis.generator.api.dom.DefaultXmlFormatter" />

        <!-- beginningDelimiter和endingDelimiter：指明数据库的用于标记数据库对象名的符号，比如ORACLE就是双引号，MYSQL默认是`反引号； -->
        <property name="beginningDelimiter" value="`"/>
        <property name="endingDelimiter" value="`"/>

        <!-- 生成的pojo，将implements Serializable,为生成的Java模型类添加序列化接口-->
        <plugin type="org.mybatis.generator.plugins.SerializablePlugin"/>

        <!-- 为生成的Java模型创建一个toString方法 -->
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin" />

        <!-- 生成 entity时，生成hashcode和equals方法
        <plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin" />  -->

        <!-- 这个插件用来在XXXExample类中生成大小写敏感的LIKE方法
            （插件本身用处不大，但是我们可以通过这个插件学习给XXXExample类添加额外的方法）
        -->
        <plugin type="org.mybatis.generator.plugins.CaseInsensitiveLikePlugin" />

        <!-- 这个插件可以使用正则表达式的方式来重命名生成的XXXExample类，
            通过配置 searchString和replaceString属性来完成
            （这个实现原理请参考MBG配置文件中的columnRenamingRule元素）
        -->
        <plugin type="org.mybatis.generator.plugins.RenameExampleClassPlugin">
            <property name="searchString" value="Example$" />
            <property name="replaceString" value="Criteria" />
        </plugin>

        <!-- 这个插件可以生成一个新的selectByExample方法，这个方法可以接受一个RowBounds参数，
           主要用来实现分页（当然，我们后面会生成我们自己的分页查询函数），
           这个插件只针对MyBatis3/MyBatis3Simple有效
           Mapper中会加入一个新的方法：
           selectByExampleWithRowbounds(XxxExample example, RowBounds rowBounds)
        -->
        <plugin type="org.mybatis.generator.plugins.RowBoundsPlugin"/>

        <!-- 是否去除自动生成的注释 true：是 ： false:否  -->
        <commentGenerator>
            <!-- 是否生成注释代时间戳-->
            <property name="suppressDate" value="true"/>
            <!-- 是否取消注释 -->
            <property name="suppressAllComments" value="true" />
        </commentGenerator>

        <!-- 数据库链接URL、用户名、密码 -->
        <jdbcConnection driverClass="com.microsoft.sqlserver.jdbc.SQLServerDriver"
                        connectionURL="jdbc:sqlserver://192.168.0.32;databaseName=DCIS_OA"
                        userId="sa" password="dcis@202b">
        </jdbcConnection>


        <!-- java类型处理器
                用于处理DB中的类型到Java中的类型，默认使用JavaTypeResolverDefaultImpl；
                注意一点，默认会先尝试使用Integer，Long，Short等来对应DECIMAL和 NUMERIC数据类型；
        -->
        <javaTypeResolver>
            <!--
                true：使用BigDecimal对应DECIMAL和 NUMERIC数据类型
                false：默认,
                    scale>0;length>18：使用BigDecimal;
                    scale=0;length[10,18]：使用Long；
                    scale=0;length[5,9]：使用Integer；
                    scale=0;length<5：使用Short；
             -->
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>


        <!-- java模型创建器，是必须要的元素
              负责：1，key类（见context的defaultModelType）；2，java类；3，查询类
            targetPackage：生成的类要放的包，真实的包受enableSubPackages属性控制；
            targetProject：目标项目，指定一个存在的目录下，生成的内容会放到指定目录中，如果目录不存在，MBG不会自动建目录
         -->
        <javaModelGenerator targetPackage="common.pojo" targetProject="./src/main/java">
            <!--  for MyBatis3/MyBatis3Simple
                             自动为每一个生成的类创建一个构造方法，构造方法包含了所有的field；而不是使用setter；
            <property name="constructorBased" value="false"/>
            -->
            <!-- for MyBatis3 / MyBatis3Simple
                   是否创建一个不可变的类，如果为true，
                   那么MBG会创建一个没有setter方法的类，取而代之的是类似constructorBased的类

            <property name="immutable" value="false"/>
             -->
            <!-- 设置一个根对象，
                 如果设置了这个根对象，那么生成的keyClass或者recordClass会继承这个类；
                 在Table的rootClass属性中可以覆盖该选项
                 注意：如果在key class或者record class中有root class相同的属性，
                 MBG就不会重新生成这些属性了，包括：  1，属性名相同，类型相同，有相同的getter/setter方法；

            <property name="rootClass" value="com._520it.mybatis.domain.BaseDomain"/>
             -->

            <!-- 在targetPackage的基础上，根据数据库的schema再生成一层package，
            最终生成的类放在这个package下，默认为false -->
            <property name="enableSubPackages" value="true"/>
            <!-- 从数据库返回的值被清理前后的空格  -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>


        <!--对应的mapper.xml文件  -->
        <sqlMapGenerator targetPackage="mapping" targetProject="./src/main/resources">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>

        <!-- 对应的Mapper接口类文件 -->
        <javaClientGenerator type="XMLMAPPER" targetPackage="common.dao" targetProject="./src/main/java">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>


        <!-- 列出要生成代码的所有表,表名和生成的实体名 -->
        <!--
            1，schema：数据库的schema；
            2，catalog：数据库的catalog；
            3，alias：为数据表设置的别名，如果设置了alias，那么生成的所有的SELECT SQL语句中，列名会变成：alias_actualColumnName
            4，domainObjectName：生成的domain类的名字，如果不设置，直接使用表名作为domain类的名字；可以设置为somepck.domainName，那么会自动把domainName类再放到somepck包里面；
            5，enableInsert（默认true）：指定是否生成insert语句；
            6，enableSelectByPrimaryKey（默认true）：指定是否生成按照主键查询对象的语句（就是getById或get）；
            7，enableSelectByExample（默认true）：MyBatis3Simple为false，指定是否生成动态查询语句；
            8，enableUpdateByPrimaryKey（默认true）：指定是否生成按照主键修改对象的语句（即update)；
            9，enableDeleteByPrimaryKey（默认true）：指定是否生成按照主键删除对象的语句（即delete）；
            10，enableDeleteByExample（默认true）：MyBatis3Simple为false，指定是否生成动态删除语句；
            11，enableCountByExample（默认true）：MyBatis3Simple为false，指定是否生成动态查询总条数语句（用于分页的总条数查询）；
            12，enableUpdateByExample（默认true）：MyBatis3Simple为false，指定是否生成动态修改语句（只修改对象中不为空的属性）；
            13，modelType：参考context元素的defaultModelType，相当于覆盖；
            14，delimitIdentifiers：参考tableName的解释，注意，默认的delimitIdentifiers是双引号，如果类似MYSQL这样的数据库，使用的是`（反引号，那么还需要设置context的beginningDelimiter和endingDelimiter属性）
            15，delimitAllColumns：设置是否所有生成的SQL中的列名都使用标识符引起来。默认为false，delimitIdentifiers参考context的属性
         -->


        <table tableName="TASK_INFOTASK_INFO" domainObjectName="TaskInfo"
               enableCountByExample="true" enableUpdateByExample="true"
               enableDeleteByExample="true" enableSelectByExample="true"
               selectByExampleQueryId="true">
            <!-- 如果设置为true，生成的model类会直接使用column本身的名字，而不会再使用驼峰命名方法，
                           比如BORN_DATE，生成的属性名字就是BORN_DATE,而不会是bornDate -->
            <property name="useActualColumnNames" value="true" />
            <!-- 替换所有表元素前缀 -->
            <columnRenamingRule searchString="^[^_]+" replaceString=""/>
            <!-- 替换表前缀 -->
            <domainObjectRenamingRule searchString="^Sys" replaceString="" />
        </table>
        <table tableName="TALLY_INFO" domainObjectName="TallyInfo"></table>
    </context>

</generatorConfiguration>
```


## $、# 区别

###  ${}

### #{}



