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