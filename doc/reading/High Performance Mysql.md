
# 高性能 Mysql

## performance_schema

- 开启Performance Schema库

> 在配置文件中添加performance_schema=on

| 表名                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| setup_instruments                                            | 设置需要开启对哪些项监控进行统计                             |
| setup_consumers                                              | 控制是否将监控到的结果进行记录                               |
| setup_threads                                                | 包含可以监控的后台线程列表，不存储用户线程的设置             |
| setup_actors                                                 | 包含确定是否为新的前台服务器线程启用监视和历史事件日志记录的信息。 |
| file_instances                                               | 包含mysql打开的文件名和访问这些文件的线程数                  |
| [metadata_locks](#metadata_locks)                            | 开元数据锁定信息                                             |
| threads                                                      | 服务器上所有的线程信息                                       |
| events_statements_current                                    | The current statement event for each thread.                 |
| events_statements_history                                    | The most recent statement events that have ended per thread. |
| [events_statements_history_long](#events_statements_history_long) | The most recent statement events that have ended globally (across all threads). |
| prepared_statements_instances                                | Prepared statement instances and statistics                  |
|                                                              |                                                              |
|                                                              |                                                              |

### metadata_locks

- 已授予的锁（显示哪些会话拥有哪个当前元数据锁）。
- 已请求但尚未授予的锁（显示哪些会话正在等待哪些元数据锁）。
- 死锁检测器已杀死的锁定请求。
- 超时并且正在等待请求会话的锁定请求被丢弃的锁定请求。

### events_statements_history_long

#### 表说明

| 列名                    | 例值                            | 说明                                                         |
| ----------------------- | ------------------------------- | ------------------------------------------------------------ |
| THREAD_ID               | 411603                          | 和EVENT ID一起标识唯一行                                     |
| EVENT_ID                | 755431                          |                                                              |
| END_EVENT_ID            | 755494                          | 该列在事件开始时设置为NULL，在事件结束时更新为线程当前事件数 |
| EVENT_NAME              | statement/com/Execute           | 收集事件的名称                                               |
| SOURCE                  | init_net_server_extension.cc:93 | 产生事件的检测代码的源文件的名称                             |
| TIMER_START             | 589129626102578000              | 事件的计时信息                                               |
| TIMER_END               | 589129626197743000              | 事件的计时信息                                               |
| TIMER_WAIT              | 95165000                        | 事件的计时信息                                               |
| LOCK_TIME               | 2000000                         | 等待表锁的时间                                               |
| SQL_TEXT                |                                 | SQL语句的文本                                                |
| DIGEST                  |                                 | 语句摘要MD5值                                                |
| DIGEST_TEXT             |                                 | 规范化语句摘要文本                                           |
| CURRENT_SCHEMA          | pms                             | 语句的默认数据库，如果没有则为NULL                           |
| OBJECT_TYPE             |                                 | 对于嵌套语句(存储程序)，这些列包含有关父语句的信息           |
| OBJECT_SCHEMA           |                                 | 对于嵌套语句(存储程序)，这些列包含有关父语句的信息           |
| OBJECT_NAME             |                                 | 对于嵌套语句(存储程序)，这些列包含有关父语句的信息           |
| OBJECT_INSTANCE_BEGIN   |                                 | 标识语句在内存中对象的地址。                                 |
| MYSQL_ERRNO             | 0                               | 语句错误编号，来自语句诊断区域                               |
| RETURNED_SQLSTATE       |                                 | 语句诊断区域中的语句SQLSTATE值                               |
| MESSAGE_TEXT            |                                 | 语句错误消息，来自语句诊断区域                               |
| ERRORS                  | 0                               | 语句是否发生错误                                             |
| WARNINGS                | 0                               | 来自语句诊断区域的警告数量                                   |
| ROWS_AFFECTED           | 0                               | 受语句影响的行数                                             |
| ROWS_SENT               | 1                               | 语句返回的行数                                               |
| ROWS_EXAMINED           | 1                               | 服务器层检查的行数                                           |
| CREATED_TMP_DISK_TABLES | 0                               | 执行语句时磁盘上创建临时表的数量                             |
| CREATED_TMP_TABLES      | 0                               | 执行语句时创建临时表的数量                                   |
| SELECT_FULL_JOIN        | 0                               | 执行表扫描的连接数，因为它们不使用索引。如果这个值不为0，则应该仔细检查表的索引 |
| SELECT_FULL_RANGE_JOIN  | 0                               | 在引用表上使用范围搜索的连接数                               |
| SELECT_RANGE            | 0                               | 如果连接使用范围搜索来解析第一个表中的行。即使值很大，这通常也不是一个关键问题 |
| SELECT_RANGE_CHECK      | 0                               | 如果 JOIN 没有索引，它会在每一行之后检查键。 这是一个非常糟糕的症状，如果这个值大于零，你需要重新考虑你的表索引。 |
| SELECT_SCAN             | 0                               | 对第一个表进行完整扫描的连接数                               |
| SORT_MERGE_PASSES       | 0                               | 排序算法需要做的归并次数。如果这个值很大，您应该考虑增加`sort_buffer_size`变量的值。 |
| SORT_RANGE              | 0                               | 使用范围进行排序的数量                                       |
| SORT_ROWS               | 0                               | 已排序的行数                                                 |
| SORT_SCAN               | 0                               | 通过扫描表完成排序的数量                                     |
| NO_INDEX_USED           | 0                               | 如果语句执行表扫描而不使用索引，则为1，否则为0               |
| NO_GOOD_INDEX_USED      | 0                               | 如果服务器没有找到适合该语句的索引，则返回1，否则返回0       |
| NESTING_EVENT_ID        |                                 |                                                              |
| NESTING_EVENT_TYPE      |                                 |                                                              |
| NESTING_EVENT_LEVEL     | 0                               |                                                              |
| STATEMENT_ID            | 11892902                        |                                                              |
| CPU_TIME                | 94110000                        |                                                              |

#### 找到所有没有使用索引的查询

```sql
SELECT THREAD_ID, SQL_TEXT, ROWS_SENT, ROWS_EXAMINED, CREATED_TMP_TABLES,NO_INDEX_USED, NO_GOOD_INDEX_USED 
FROM performance_schema.events_statements_history_long
WHERE NO_INDEX_USED > 0 OR NO_GOOD_INDEX_USED > 0;
```

#### 找到所有创建临时表的查询

```sql
SELECT THREAD_ID, SQL_TEXT, ROWS_SENT, ROWS_EXAMINED, CREATED_TMP_TABLES, CREATED_TMP_DISK_TABLES 
FROM performance_schema.events_statements_history_long 
WHERE CREATED_TMP_TABLES > 0 OR CREATED_TMP_DISK_TABLES > 0;
```



## Operating System and Hardware Optimization

