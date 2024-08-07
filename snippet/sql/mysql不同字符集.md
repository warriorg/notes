# mysql 不同字符集比较

mysql显示当前有多少字符集 `show charset;`


 ```sql
 SELECT DISTINCT t2.id FROM a t1 JOIN b t2 ON t1.group_id = t2.group_id COLLATE utf8mb4_unicode_ci WHERE t1.id = 1;
 ```