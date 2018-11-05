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

