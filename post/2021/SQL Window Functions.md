## SQL Window Functions

### 简介

开窗函数也叫分析函数，针对一组行计算值，并为每行返回一个结果。这与聚合函数不同；聚合函数会为一组行返回一个结果。

开窗函数包含一个 `OVER` 子句，该子句定义了涵盖所要计算行的行窗口。对于每一行，系统会使用选定的行窗口作为输入来计算分析函数结果，并可能进行聚合。

借助开窗函数，您可以计算移动平均值、对各项进行排名、计算累计总和，以及执行其他分析。

### 语法

```sql
window_function_name ( expression ) OVER (
    partition_clause
    order_clause
    frame_clause
)
```

**window_function_name** 执行窗口函数的函数, 如 `ROW_NUMBER`, `RANK`, and `SUM`等

**expression** 特定于窗口函数的参数。 有些函数具有参数，而有些函数则没有。

**over** OVER子句定义了窗口分区以形成行组，指定了分区中的行顺序。OVER子句由三个子句组成:partition_clause、order_clause和frame_clause。

**partition_clause**

```sql
PARTITION BY expr1, expr2, ...
```

**order_clause**

```sql
ORDER BY 
    expression [ASC | DESC]  [NULL {FIRST| LAST}]
    ,...
```

**frame_clause**

```sql
{ RANGE | ROWS } frame_start
{ RANGE | ROWS } BETWEEN frame_start AND frame_end  
```

**frame_start**

```sql
N PRECEDING
UNBOUNDED PRECEDING
CURRENT ROW
```

**frame_end**

```sql
CURRENT ROW
UNBOUNDED FOLLOWING
N FOLLOWING
```

### 实战

```sql
 # 获取最后一条的审计日志
 select t.* from (
   select row_number() over (partition by biz_id order by insert_time desc) num,
     biz_id,
     note,
     insert_time 
   from T_MRP_AUDIT 
 ) t where t.num = 1
```



### 参考

https://www.sqltutorial.org/sql-window-functions/

https://en.wikipedia.org/wiki/Window_function_(SQL)

https://cloud.google.com/bigquery/docs/reference/standard-sql/analytic-function-concepts

