###备份
```bash
mongodump -h dbhost -d dbname -o dbdirectory
```

###恢复

```bash
mongorestore -h dbhost -d dbname --directoryperdb dbdirectory

mongorestore -d dbname dbdirectory
mongorestore -d dbname -c collectionName dbdirectory    # 恢复一张表
```

###删除数据库

```bash
use dbname
db.dropDatabase()
```

###QA
####Unable to create/open lock file: /mnt/mongodb/data/mongod.lock errno:13 Permission denied Is a mongod instance already running?, terminating
```bash
chown -R mongodb:mongodb /mnt/mongodb/
```

## 升级

```bash
vim /etc/yum.repos.d/mongodb-org-3.4.repo
```
更新 repo文件
```
[mongodb-org-3.6]
name=MongoDB Repository
baseurl=https://repo.mongodb.org/yum/redhat/7/mongodb-org/3.6/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://www.mongodb.org/static/pgp/server-3.6.asc
```
升级包

```bash
yum -y upgrade mongodb-org
```
启动mongodb
```
> db.adminCommand( { getParameter: 1, featureCompatibilityVersion: 1 } )
{ "featureCompatibilityVersion" : { "version" : "3.4" }, "ok" : 1 }
> db.adminCommand( { setFeatureCompatibilityVersion: "3.6" } )
{ "ok" : 1 }
> db.adminCommand( { getParameter: 1, featureCompatibilityVersion: 1 } )
{ "featureCompatibilityVersion" : { "version" : "3.6" }, "ok" : 1 }
```

升级后服务无法启动, 赋权

```bash
chown -R mongod:mongod /var/lib/mongo
chown -R mongod:mongod /var/log/mongodb
rm /var/run/mongodb/mongod.pid -f
```

## mtools

安装
```bash
pip install mtools
```

### mlogfilter
mlogfilter用于解析日志，如果有多个日志文件，mlogfilter按照时间戳合并
```bash
# 分析慢查询并以json格式导出并导入到test库的mycoll集合中
mlogfilter mongod.log --slow --json | mongoimport -d test -c mycoll
# 查看某个库的某个集合的慢查询, --slow可以指定慢查询时间为多少毫秒
mlogfilter /opt/logs/mongodb/*.log --slow  --json
mlogfilter mongod.log --namespace admin.\$cmd --slow 1000
# 查看某一个操作类型的慢查询，一次只能指定一个操作类型，可以是query,insert,update,delete,command,getmore
mlogfilter /opt/logs/mongodb/*.log --slow 1000 --namespace order.bill --operation query
# 根据某一个线程的查看慢查询
mlogfilter /opt/logs/mongodb/*.log --slow 1000 --namespace order.bill --operation query --thread conn1317475
# --parttern P  根据匹配条件查询日志 字段名称必须用双引号包围
mlogfilter mongod.log --pattern '{"_id": 1, "host": 1, "ns": 1}
# 根据关键字过滤日志
mlogfilter mongod.log --word assert warning error
# 返回所有9月份的日志
mlogfilter mongod.log --from Sep
# 返回5分钟之前的日志 
mlogfilter mongod.log --from "now -5min"
# 返回当天00:00:00 到当天02:00:00 的日志
mlogfilter mongod.log --from today --to +2hours
# 返回当天从9:30开始的日志
mlogfilter mongod.log --from today 9:30
```

### mloginfo
```bash 
# 显示日志的查询统计信息
mloginfo mongod.log --queries
# 对结果进行排序
mloginfo mongod.log --queries --sort count
mloginfo mongod.log --queries --sort sum

# 显示重启信息
mloginfo mongod.log --restarts
# 分类显示日志消息
mloginfo mongod.log --distinct
# 显示连接信息
mloginfo mongod.log --connections
# 显示复制集信息
mloginfo mongod.log --rsstate
```

### mplotqueries
mplotqueries是一个可以可视化MongoDB日志文件中的操作的工具。

-- group GROUP

group 值

* namespace  
* filename
* operation
* thread
* log2code
* pattern