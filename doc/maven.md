### 设置仓库

setting.xml

```xml
<profiles>
    <profile>
        <id>defaultProfile</id>
        <repositories>
            <repository>
                <id>company</id>
                <name>company repository</name>
                <url>http://ip:port/repository/maven-public/</url>
            </repository>
            <repository>
                <id>aliyun</id>
                <name>aliyun repository</name>
                <url>https://maven.aliyun.com/repository/central/</url>
            </repository>
            <repository>
                <id>clojars</id>
                <name>clojars repository</name>
                <url>http://clojars.org/repo/</url>
            </repository>
        </repositories>
    </profile>
</profiles>
<activeProfiles>
    <activeProfile>defaultProfile</activeProfile>
</activeProfiles>
```

### 部署代码到 nexus
1. Servers 配置认证信息
在Maven settings.xml中添加Nexus认证信息：
```xml
<server>
  <id>nexus-releases</id>
  <username>admin</username>
  <password>admin123</password>
</server>

<server>
  <id>nexus-snapshots</id>
  <username>admin</username>
  <password>admin123</password>
</server>
```
* nexus-releases: 用于发布Release版本
* nexus-snapshots: 用于发布Snapshot版本

Release版本与Snapshot版本的区分：

>Release: 4.3.0
>Snapshot: 4.3.0-SNAPSHOT

* 在项目POM.xml中设置的版本号添加SNAPSHOT标识的都会发布为SNAPSHOT版本，没有SNAPSHOT标识的都会发布为Release版本。

* SNAPSHOT版本会自动加一个时间作为标识，如：4.3.0-SNAPSHOT发布后为变成4.3.0-SNAPSHOT-20160712.114532-1.jar

2. 配置自动化部署构件
在POM.xml中添加以下代码：
```xml
<distributionManagement>  
  <repository>  
    <id>nexus-releases</id>  
    <name>Nexus Release Repository</name>  
    <url>http://127.0.0.1:8081/repository/maven-releases/</url>  
  </repository>  
  <snapshotRepository>  
    <id>nexus-snapshots</id>  
    <name>Nexus Snapshot Repository</name>  
    <url>http://127.0.0.1:8081/repository/maven-snapshots/</url>  
  </snapshotRepository>  
</distributionManagement> 
```
注意：

> ID名称必须要与settings.xml中Servers配置的ID名称保持一致。
>
> 项目版本号中有SNAPSHOT标识的，会发布到Nexus Snapshots Repository, 否则发布到Nexus Release Repository，并根据ID去匹配授权账号。

3. 部署到Nexus仓库
`mvn deploy`

### dependency
> maven-dependency-plugin

* [dependency:analyze](https://maven.apache.org/plugins/maven-dependency-plugin/analyze-mojo.html) 分析项目的依赖关系，确定哪些是:声明并使用，声明未使用，未声明并使用，

* [dependency:analyze-dep-mgt](https://maven.apache.org/plugins/maven-dependency-plugin/analyze-dep-mgt-mojo.html) analyzes your projects dependencies and lists mismatches between resolved dependencies and those listed in your dependencyManagement section.

* [dependency:analyze-only](https://maven.apache.org/plugins/maven-dependency-plugin/analyze-only-mojo.html) is the same as analyze, but is meant to be bound in a pom. It does not fork the build and execute test-compile.

* [dependency:analyze-report](https://maven.apache.org/plugins/maven-dependency-plugin/analyze-report-mojo.html) analyzes the dependencies of this project and produces a report that summarises which are: used and declared; used and undeclared; unused and declared.

* [dependency:analyze-duplicate](https://maven.apache.org/plugins/maven-dependency-plugin/analyze-duplicate-mojo.html) analyzes the `<dependencies/>` and `<dependencyManagement/>` tags in the pom.xml and determines the duplicate declared dependencies.

* [dependency:build-classpath](https://maven.apache.org/plugins/maven-dependency-plugin/build-classpath-mojo.html) tells Maven to output the path of the dependencies from the local repository in a classpath format to be used in java -cp. The classpath file may also be attached and installed/deployed along with the main artifact.

* [dependency:copy](https://maven.apache.org/plugins/maven-dependency-plugin/copy-mojo.html) takes a list of artifacts defined in the plugin configuration section and copies them to a specified location, renaming them or stripping the version if desired. This goal can resolve the artifacts from remote repositories if they don't exist in either the local repository or the reactor.

* [dependency:copy-dependencies](https://maven.apache.org/plugins/maven-dependency-plugin/copy-dependencies-mojo.html) takes the list of project direct dependencies and optionally transitive dependencies and copies them to a specified location, stripping the version if desired. This goal can also be run from the command line.

* [dependency:display-ancestors](https://maven.apache.org/plugins/maven-dependency-plugin/display-ancestors-mojo.html) displays all ancestor POMs of the project. This may be useful in a continuous integration system where you want to know all parent poms of the project. This goal can also be run from the command line.

* [dependency:get](https://maven.apache.org/plugins/maven-dependency-plugin/get-mojo.html) resolves a single artifact, eventually transitively, from a specified remote repository.

* [dependency:go-offline](https://maven.apache.org/plugins/maven-dependency-plugin/go-offline-mojo.html) tells Maven to resolve everything this project is dependent on (dependencies, plugins, reports) in preparation for going offline.

* [dependency:list](https://maven.apache.org/plugins/maven-dependency-plugin/list-mojo.html) alias for resolve that lists the dependencies for this project.

* [dependency:list-repositories](https://maven.apache.org/plugins/maven-dependency-plugin/list-repositories-mojo.html) displays all project dependencies and then lists the repositories used.

* [dependency:properties](https://maven.apache.org/plugins/maven-dependency-plugin/properties-mojo.html) set a property for each project dependency containing the to the artifact on the file system.

* [dependency:purge-local-repository](https://maven.apache.org/plugins/maven-dependency-plugin/purge-local-repository-mojo.html) tells Maven to clear dependency artifact files out of the local repository, and optionally re-resolve them.

* [dependency:resolve](https://maven.apache.org/plugins/maven-dependency-plugin/resolve-mojo.html) tells Maven to resolve all dependencies and displays the version. **JAVA 9 NOTE:** *will display the module name when running with Java 9.*

* [dependency:resolve-plugins](https://maven.apache.org/plugins/maven-dependency-plugin/resolve-plugins-mojo.html) 解析插件及其依赖关系。

* [dependency:sources](https://maven.apache.org/plugins/maven-dependency-plugin/sources-mojo.html) 解析所有依赖项及其源附件，并显示版本。

* [dependency:tree](https://maven.apache.org/plugins/maven-dependency-plugin/tree-mojo.html) 显示此项目的依赖关系树

* [dependency:unpack](https://maven.apache.org/plugins/maven-dependency-plugin/unpack-mojo.html) like copy but unpacks.

* [dependency:unpack-dependencies](https://maven.apache.org/plugins/maven-dependency-plugin/unpack-dependencies-mojo.html) like copy-dependencies but unpacks.


### 其它
#### 安装包到本地仓库
```bash
mvn install:install-file -Dfile=/filePath/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.1.0 -Dpackaging=jar
```
#### 打包跳过单元测试
1. package
```bash
mvn package -Dmaven.test.skip=true
```
2. pom.xml
```xml
<properties>
	<maven.test.skip>true</maven.test.skip>
</properties>
```

