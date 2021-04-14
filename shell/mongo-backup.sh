#!/bin/bash
#backup MongoDB

# 可以增加定时任务,做到定期备份
# chmod +x ~/crontab/mongodb_backup.sh       # 增加执行权限
# vi /etc/crontab
# 0 2 * * * root ~/crontab/mongodb_backup.sh   # 每天凌晨02:00以 root 身份运行备份数据库的脚本


# mongodump命令路径
DUMP=mongodump
# 临时备份目录
OUT_DIR=/Users/warrior/Downloads/data/backup/mongodb/now
# 备份存放路径
TAR_DIR=/Users/warrior/Downloads/data/backup/mongodb/
# 获取当前系统时间
DATE=`date +%Y_%m_%d_%H_%M`
# 数据库账号
DB_USER=user
# 数据库密码
DB_PASS=123
# DAYS=30代表删除30天前的备份，即只保留近30天的备份
DAYS=30
# 最终保存的数据库备份文件
TAR_BAK="mongodb_bak_$DATE.tar.gz"

# 检查目录是否村子，不存在就新建
if [[ ! -d "$OUT_DIR" ]]; then
    mkdir -p $OUT_DIR 
fi

cd $OUT_DIR
rm -rf $OUT_DIR/*
mkdir -p $OUT_DIR/$DATE

# 备份数据库
$DUMP -d cws -o $OUT_DIR/$DATE
# 压缩
tar -zcvf $TAR_DIR/$TAR_BAK $OUT_DIR/$DATE
# 删除30天前的备份文件 注意+号的位置
find $TAR_DIR/ -mtime +$DAYS -delete

exit