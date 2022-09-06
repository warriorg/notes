# mysql 锁表查询

```sql
-- 查询当前的事务
select * FROM innodb_trx\G;
***************************[ 1. row ]***************************
trx_id                     | 12959762
trx_state                  | RUNNING					-- 状态
trx_started                | 2022-09-06 13:45:19
trx_requested_lock_id      | <null>
trx_wait_started           | <null>
trx_weight                 | 452
trx_mysql_thread_id        | 73430						-- mysql执行线程id
trx_query                  | <null>						-- 事务语句
trx_operation_state        | <null>
trx_tables_in_use          | 0
trx_tables_locked          | 4
trx_lock_structs           | 58
trx_lock_memory_bytes      | 8312
trx_rows_locked            | 3709
trx_rows_modified          | 394
trx_concurrency_tickets    | 0
trx_isolation_level        | REPEATABLE READ
trx_unique_checks          | 1
trx_foreign_key_checks     | 1
trx_last_foreign_key_error | <null>
trx_adaptive_hash_latched  | 0
trx_adaptive_hash_timeout  | 0
trx_is_read_only           | 0
trx_autocommit_non_locking | 0
trx_schedule_weight        | <null>



-- 查询执行事物的连接 根据trx_mysql_thread_id查询
select * FROM information_schema.processlist where id = 73430;
+-------+------+-------------------+------+---------+------+-------+--------+
| ID    | USER | HOST              | DB   | COMMAND | TIME | STATE | INFO   |
+-------+------+-------------------+------+---------+------+-------+--------+
| 73430 | root | 192.168.0.1:33941 | nems | Sleep   | 22   |       | <null> |
+-------+------+-------------------+------+---------+------+-------+--------+


-- 查看事务锁信息 8.0
SELECT * FROM performance_schema.data_locks\G;
-- 8.0 以下
SELECT * FROM performance_schema.innodb_locks\G;
***************************[ 1. row ]***************************
ENGINE                | INNODB
ENGINE_LOCK_ID        | 140442212676280:6064:140439862655768
ENGINE_TRANSACTION_ID | 12960072
THREAD_ID             | 836249
EVENT_ID              | 159
OBJECT_SCHEMA         | nems
OBJECT_NAME           | write_off_import_receipt
PARTITION_NAME        | <null>
SUBPARTITION_NAME     | <null>
INDEX_NAME            | <null>
OBJECT_INSTANCE_BEGIN | 140439862655768
LOCK_TYPE             | TABLE
LOCK_MODE             | IX
LOCK_STATUS           | GRANTED
LOCK_DATA             | <null>
***************************[ 2. row ]***************************
ENGINE                | INNODB
ENGINE_LOCK_ID        | 140442212676280:5200:140439862655680
ENGINE_TRANSACTION_ID | 12960072
THREAD_ID             | 836249
EVENT_ID              | 157
OBJECT_SCHEMA         | nems
OBJECT_NAME           | write_off_actual_folding_summary
PARTITION_NAME        | <null>
SUBPARTITION_NAME     | <null>
INDEX_NAME            | <null>
OBJECT_INSTANCE_BEGIN | 140439862655680
LOCK_TYPE             | TABLE
LOCK_MODE             | IX
LOCK_STATUS           | GRANTED
LOCK_DATA             | <null>

-- 查看对应被阻塞的INNODB事务 8.0
SELECT * FROM performance_schema.data_lock_waits\G;
-- 8.0以下
SELECT * FROM performance_schema.innodb_lock_waits\G;


-- 关闭线程,释放事务
kill 73430;

```