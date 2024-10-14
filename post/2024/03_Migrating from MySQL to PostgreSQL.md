# Migrating from MySQL to PostgreSQL

## 函数

- Check column is null or empty or space

```sql
-- mysql
COALESCE(trim(corp_customs_code),'') = ''
-- pg
nullif(TRIM(corp_customs_code), '') is null
```

- xxxxx

```sql
-- postgreSQL 有问题
select * from inventory_head ih where ih.inventory_no is not null and ih.inventory_no != ''
-- 推荐使用的写法，支持 mysql， pg
select * from inventory_head ih where nullif(trim(ih.inventory_no), '') is not null
```

- Get current timestamp to Bigint

```sql
-- mysql
UNIX_TIMESTAMP(now())*1000
-- pg
floor(extract(epoch FROM now()) * 1000)
```

- Bigint to timestamp

```sql
-- mysql
FROM_UNIXTIME(second)
FROM_UNIXTIME(millisecond/1000)
-- pg
TO_TIMESTAMP(millisecond / 1000)
```
