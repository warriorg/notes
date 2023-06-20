# Minio

## mc

### alias

manage server credentials in configuration file

```bash
# 设置一个服务器的配置
mc alias set dev http://192.168.0.203:9000 minioadmin minioadmin
# 查看服务器文件
mc ls dev
```