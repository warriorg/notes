#安装logstash

`docker pull logstash`

`docker run -it --rm logstash -e 'input { stdin { } } output { stdout { } }'`

`docker run -it --rm -v "$PWD":/logstash.conf -p 4560:4560 logstash -f /logstash.conf`

配置测试

```bash
input {
      stdin{}
}
output {
    stdout{
        codec => rubydebug
    }
}
```

配置spring boot的日志到logstash

```bash 
input {
  stdin{}
  tcp {
      port => 4560
      codec => json_lines
  }
}
output {
    stdout{
        codec => rubydebug
    }
}
```
>tips: 开启1-1024之间的端口，必须使用root用户

spring boot 配置

```bash
compile('net.logstash.logback:logstash-logback-encoder:4.7')

```
logback 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="3 seconds">
       <logger name="org.springframework.web" level="INFO"/>
       <logger name="logging.level.org.hibernate" level="ERROR"/>

       <appender name="stash" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
              <destination>127.0.0.1:4560</destination>
              <encoder class="net.logstash.logback.encoder.LogstashEncoder" />
       </appender>

       <root level="DEBUG">
              <appender-ref ref="stash" />
       </root>
</configuration>
```

> ==在配置过程中，发现logback 1.1.7 存在一个bug，而spring boot 1.4.1恰好使用了这个版本，作者说在1.1.8的时候修复，所以指定logback的版本为之前版本==

```bash
  buildscript {
        ext {
            springBootVersion = "1.4.1.RELEASE"
        }
        //指定logback 的版本
        ext['logback.version'] = '1.1.6'
      	 //其它
    }
```

#安装 elasticsearch

`docker pull elasticsearch`

`docker run --rm -it -p 9200:9200 -p 9300:9300 -v "$PWD/esdata":/usr/share/elasticsearch/data elasticsearch`

>测试  http://127.0.0.1:9200


#安装 kibana

`docker pull kibana`
`docker run --rm -it --link some-elasticsearch:elasticsearch -p 5601:5601 kibana`

>测试  http://127.0.0.1:5601