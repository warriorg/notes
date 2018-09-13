###备份
```bash
mongodump -h dbhost -d dbname -o dbdirectory
```

###恢复

```bash
mongorestore -h dbhost -d dbname --directoryperdb dbdirectory

mongorestore -d dbname dbdirectory
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