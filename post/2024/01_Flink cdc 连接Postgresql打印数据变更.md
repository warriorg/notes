# Flink cdc 连接Postgresql打印数据库变更

引入包

```java
implementation "com.ververica:flink-connector-postgres-cdc:3.0.1"
implementation "io.debezium:debezium-connector-mysql:1.9.8.Final"

```

修改 `postgresql.conf` 配置

```bash
wal_level = logical  # minimal, replica, or logical
```

## SourceFunction-based DataStream

```java
import com.ververica.cdc.connectors.postgres.PostgreSQLSource;
import com.ververica.cdc.debezium.DebeziumDeserializationSchema;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class PostgresqlSourceExample {

    public static void main(String[] args) throws Exception {

        DebeziumDeserializationSchema<String> deserializer =
                new JsonDebeziumDeserializationSchema();

        SourceFunction<String> sourceFunction = PostgreSQLSource.<String>builder()
                .hostname("localhost")
                .port(5432)
                .database("warriorg") 
                .schemaList("public")  
                .tableList("public.iam_user") 
                .username("warriorg")
                .password("12345678")
                // 如果没有则报错如下 ERROR: could not access file "decoderbufs": No such file or directory
                .decodingPluginName("pgoutput") 
                .deserialddizer(deserializer) // converts SourceRecord to JSON String
                .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(3000);

        env.addSource(sourceFunction)
                .print().setParallelism(1);

        env.execute("Output Postgres Snapshot");
    }
}
```



## 参考

https://nightlies.apache.org/flink/flink-cdc-docs-release-3.0/zh/docs/connectors/cdc-connectors/postgres-cdc/
https://blog.csdn.net/u011788214/article/details/123107572
