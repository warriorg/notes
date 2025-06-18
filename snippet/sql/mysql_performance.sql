-- 显示innodb 状态
show engine innodb status;

-- explain 
explain select statement

-- 输出更详细的查询计划
explain format=tree select statement

-- 更详细的查询计划
explain analyze

--  InnoDB 缓冲池
SHOW STATUS LIKE 'innodb_buffer_pool_size';

-- 显示数据库缓存使用
show STATUS
where
variable_name in ('innodb_page_size', 'Innodb_buffer_pool_pages_total', 'Innodb_buffer_pool_pages_free', 'Innodb_buffer_pool_pages_data', 'Innodb_buffer_pool_pages_dirty', 'Innodb_buffer_pool_wait_free');

-- Innodb_buffer_pool_pages_data 缓冲池中存储了实际数据的页面数量
-- Innodb_buffer_pool_pages_dirty 缓冲池中被修改但尚未写入磁盘的数据页数量
-- Innodb_buffer_pool_pages_total 缓冲池的总页面数
-- Innodb_buffer_pool_pages_free 缓冲池中空闲的页面数量
-- Innodb_buffer_pool_wait_free 缓冲池等待释放的页面数量
-- Innodb_page_size 表示 InnoDB 存储引擎使用的页面大小

-- 查询未使用的索引
SELECT * FROM sys.schema_unused_indexes WHERE object_schema NOT IN ('performance _schema');

-- 分析表，查看表中的索引，其中 Cardinality（基数）越大，基数越高，MySQL在执行连接时使用索引的可能性就越大。
analyze table cms_declaration_element_item_data;  
SHOW INDEX FROM  cms_declaration_element_item_data;

-- 显示表状态
show table status from cms where name = 'blade_oss';

-- 查询索引的大小
select 
  index_name, sum(stat_value ) * @@innodb_page_size size 
FROM 
  mysql.innodb_index_stats 
where 
  stat_name = 'size' and database_name = 'employees' and table_name = 'departments'
group by index_name

-- 查询95% 的响应时间
SELECT 
	ROUND(bucket_quantile * 100, 1) as p,
	ROUND(bucket_timer_high / 1000000000, 3) as ms
from 
	performance_schema.events_statements_histogram_global
where 
	bucket_quantile >= 0.95
order by bucket_quantile limit 100;

-- 查询数据库大小
select 
	table_schema as db,
	ROUND(SUM(data_length + index_length) / 1073741824, 2) as 'size_GB'
FROM 
	information_schema.tables
group by TABLE_SCHEMA;

-- 查询表大小 
SELECT 
	table_schema as db,
	table_name as tbl,
	ROUND((data_length + index_length) / 1073741824, 2) as 'size_GB'
from 
	information_schema.tables
where 
	table_type = 'BASE TABLE'
	and TABLE_SCHEMA = 'information_schema'

-- 查询锁定的行
select * FROM performance_schema.data_locks;

-- 查询活跃时间超过 1s 的事务
SELECT
  ROUND (trx. timer_wait/1000000000000,3) AS trx_runtime, 
  trx. thread_id AS thread_id,
    trx.event_id AS trx_event_id,
    trx. isolation_level,
    trx.autocommit, 
    stm.current_schema AS db, 
    stm.SQL_TEXT AS query, 
    stm.rows_examined AS rows_examined, 
    stm.rows_affected AS rows_affected,
    stm.rows_sent As rows_sent,
    IF(stm.end_event_id IS NULL, 'running', 'done') AS exec_state,
    ROUND (stm. timer_wait/1000000000000,3) AS exec_time
  FROM
  performance_schema.events_transactions_current trx
  JOIN performance_schema.events_statements_current stm USING (thread_id)
WHERE
  trx.state = 'ACTIVE'
  AND trx.timer_wait > 1000000000000 * 1

-- Calculate table column size
select
	sum(estimated_size_per_column)
from
	(
	select
		COLUMN_NAME,
		DATA_TYPE,
		case
			when DATA_TYPE in ('varchar', 'text', 'char') then CHARACTER_MAXIMUM_LENGTH * 
              case
				when CHARACTER_SET_NAME = 'utf8mb4' then 4
				when CHARACTER_SET_NAME = 'utf8' then 3
				else 1
			end
			when DATA_TYPE in ('int', 'bigint', 'decimal', 'float', 'double', 'date', 'datetime', 'time', 'year') then 
              case
				when DATA_TYPE = 'int' then 4
				when DATA_TYPE = 'bigint' then 8
				-- BIGINT takes 8 bytes
				when DATA_TYPE = 'decimal' then 4
				when DATA_TYPE = 'float' then 4
				when DATA_TYPE = 'double' then 8
				when DATA_TYPE = 'date' then 3
				when DATA_TYPE = 'datetime' then 8
				when DATA_TYPE = 'time' then 3
				when DATA_TYPE = 'year' then 1
			end
			else 0
		end as estimated_size_per_column
	from
		information_schema.columns
	where
		table_schema = 'cms_dev'
		and table_name = 'cms_report_import_ledger'
) t


