# Logback日志输出到ELK

### 用docker-compose本机部署elk

#### docker-compose.yml

```dockerfile
version: "3"
services: 
  es01:
    image: docker.elastic.co/elasticsearch/elasticsearch:7.3.0
    container_name: es01
    volumes:
      - ./esdata:/usr/share/elasticsearch/data
    environment:
      - discovery.type=single-node
    ports:
      - "9200:9200"
      - "9300:9300"
    networks:
      - elk

  kibana:
    image: docker.elastic.co/kibana/kibana:7.3.0
    links:
      - es01
    environment:
      ELASTICSEARCH_HOSTS: http://es01:9200
    ports:
      - "5601:5601"
    depends_on:
      - es01 
    networks:
      - elk
    container_name: kibana

  logstash:
    image: docker.elastic.co/logstash/logstash:7.3.0
    links:
      - es01
    command: logstash -f /etc/logstash/conf.d/logstash.conf  #logstash 启动时使用的配置文件
    volumes:
      - $PWD/logstash/conf.d:/etc/logstash/conf.d  #logstash 配文件位置
      - $PWD/logstash/config/logstash.yml:/usr/share/logstash/config/logstash.yml  #logstash 配文件位置
    depends_on:
      - es01  #后于elasticsearch启动
    ports:
      - "4560:4560"
      - "9600:9600"
    networks:
      - elk 
    container_name: logstash

networks:
  elk:
```
#### logstash.conf

```bash
input {
  tcp {
    port => 4560
    codec => json_lines
  }
}

filter {
}

output {
  stdout {
    codec => rubydebug
  }
  elasticsearch {
    hosts => ["http://es01:9200"]
    action => "index"
    index => "%{[appname]}-%{+YYYY.MM.dd}"
  }
}
```

#### logstash.yml

```bash
http.host: "0.0.0.0"
xpack.monitoring.elasticsearch.hosts: [ "http://es01:9200" ]

## X-Pack security credentials
#
xpack.monitoring.enabled: true
# xpack.monitoring.elasticsearch.username: elastic
# xpack.monitoring.elasticsearch.password: changeme
```

#### docker-compose 命令

```bash 
docker-compose up -d   		# 启动
docker-compose down 			# 停止
docker logs -f logstash   # 查看 logstash 输出的日志，这个方便调试
```



### Spring boot 项目配置

#### 依赖包

```groovy
compile 'net.logstash.logback:logstash-logback-encoder:6.1'
```


#### Logback.xml 配置

```bash
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">
    <logger name="org.springframework" level="WARN" />
    <logger name="org.hibernate" level="WARN" />
    <logger name="tk.mybatis" level="WARN" />
    <logger name="org.mongodb" level="WARN" />
    <logger name="springfox.documentation" level="ERROR" />
    <logger name="org.apache" level="WARN" />
    <logger name="io.netty" level="WARN" />

    <property name="log.path" value="logs/log.log" />
    <property name="log.pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n" />

    <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${log.pattern}</pattern>
        </encoder>
    </appender>

    <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log.path}</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}.%d{yyyy-MM-dd}.zip</fileNamePattern>
        </rollingPolicy>
        <encoder>
            <pattern>${log.pattern}</pattern>
        </encoder>
    </appender>
		<!-- logstash 配置部分 appanme 根据实际情况修改 -->
    <appender name="logstash" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>127.0.0.1:4560</destination>
        <encoder class="net.logstash.logback.encoder.LogstashEncoder">
            <includeContext>false</includeContext>
            <customFields>{"appname": "cs-elk", "server": "${HOSTNAME}"}</customFields>
        </encoder>
    </appender>

    <root level="debug">
        <appender-ref ref="stdout" />
        <appender-ref ref="file" />
        <appender-ref ref="logstash" />
    </root>
</configuration>
```



### 日志查看

在浏览器中打开 `http://127.0.0.1:5601/`kibana，查看汇总的日志信息！