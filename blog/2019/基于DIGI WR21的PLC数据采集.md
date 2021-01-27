# 基于DIGI WR21的PLC数据采集

通过路由器，使用python脚本读取数据，转发到后台golang数据采集平台，数据采集平台通过数据清洗，然后把数据清洗成标准数据，通过gRpc传输到分析平台。后期会写一点golang 基于grpc的微服务设计。这次记录下前端WR21数据采集的过程



WR21的配置

**默认用户名密码** username/password

Interfaces -> Ethernet  设置网口
![wr21-1](./assets/wr21-1.png)
通过ftp/ssh上传python脚本，然后配置WR21

```python
pycfg 0 stderr2stdout on
Python
Python plc.py

# 命令设置自动启动
cmd 0 autocmd "Python plc.py"
config 0 save
```

通过web修改python脚本
![wr21-1](./assets/wr21-3.png)

通过web设置自启动脚本
![wr21-1](./assets/wr21-3.png)



