#!/bin/bash

old_db='pub_param'
new_db='pub_param_bak'
MYSQL_PASSWORD="123456"



list_table=$(MYSQL_PWD="$MYSQL_PASSWORD" mysql -uroot -h192.168.2.138 -Nse "select table_name from information_schema.TABLES where TABLE_SCHEMA='$old_db'")

for table in $list_table
do
    MYSQL_PWD="$MYSQL_PASSWORD" mysql -uroot -h192.168.2.138 -e "rename table $old_db.$table to $new_db.$table"
done
