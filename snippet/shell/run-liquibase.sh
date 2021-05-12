#!/bin/bash
source /etc/profile
echo '开始执行更新脚本'
typeset -l db_type

if [ $# -gt 0 ]
then
   db_type=$1
else
   echo "请输入数据库类型"
   exit 1
fi

echo "db type: $db_type"

version=$2

if [[ $db_type != "oracle" && $db_type != "postgresql" ]]
then
   echo "$db_type数据库类型不支持!"
   exit 1
fi

if [ ! -f "./db.zip" ]
then
   echo "请上传db.zip数据库脚本包"
   exit 1
fi

if [ -d "./db" ];then
rm -rf ./db
fi

echo "开始解压db.zip文件"

unzip db.zip

if [ ! -f "./db/liquibase/${db_type}_master.xml" ];then
 echo "./db/liquibase/${db_type}_master.xml文件不存在,db.zip文件有误！"
 
fi

db_driver=""
db_url=""
db_user=""
db_pwd=""

if [ $db_type = "oracle" ]
then
    db_driver="oracle.jdbc.OracleDriver"
    db_url="jdbc:oracle:thin:@192.168.1.2:1521:ORCL"
    db_user="std"
    db_pwd="dbwork1"
fi

if [ $db_type = "postgresql" ]
then
    db_driver="org.postgresql.Driver"
    db_url="jdbc:postgresql://192.168.1.3:5432/wstd?useSSL=false"
    db_user="std"
    db_pwd="dbwork1"
fi

echo "开始执行liquibase的update命令"

liquibase --driver=$db_driver --changeLogFile=./db/liquibase/${db_type}_master.xml --url=$db_url --username=$db_user --password=$db_pwd update
echo "更新脚本结束"
if [ -n "$version" ];then
  echo "开始执行liquibase的tag命令"
  liquibase --driver=$db_driver --changeLogFile=./db/liquibase/${db_type}_master.xml --url=$db_url --username=$db_user --password=$db_pwd tag $version
  echo "更新版本号为：$version"
fi
