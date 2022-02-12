Nexus 是一个仓库管理器，并且极大的简化了自己内部仓库和外部仓库的访问。
​

支持组件

- Maven / Java
- NPM
- Nuget
- Helm
- Docker
- P2
- OBR
- APT
- Go 



# 安装
## 手动

安装前检查环境
[https://help.sonatype.com/repomanager3/product-information/system-requirements](https://help.sonatype.com/repomanager3/product-information/system-requirements)

从一下地址下载最新的安装文件。
[https://help.sonatype.com/repomanager3/product-information/download](https://help.sonatype.com/repomanager3/product-information/download)

```bash
tar xvzf nexus-3.37.3-02-unix.tar.gz -C /opt

cd /opt/nexus-3.37.3-02/bin 
# 使用下面命令启动nexus
./nexus {start|stop|run|run-redirect|status|restart|force-reload}

```
### 安装为服务
```bash
touch /etc/systemd/system/nexus.service
vim /etc/systemd/system/nexus.service
```
编辑文件内容
```bash
[Unit]
Description=nexus service
After=network.target
  
[Service]
Type=forking
LimitNOFILE=65536
ExecStart=/opt/nexus-3.37.3-02/bin/nexus start
ExecStop=/opt/nexus-3.37.3-02/bin/nexus stop
User=nexus
Restart=on-abort
TimeoutSec=600
  
[Install]
WantedBy=multi-user.target
```
启动服务
```bash
sudo systemctl daemon-reload
sudo systemctl enable nexus.service
sudo systemctl start nexus.service
```
默认的登录密码在  `/opt/sonatype-work/nexus3/admin.password`


## 使用docker compose
`docker-compose.yml`文件内容如下
```yaml
version: "3"

services:
  nexus:
    restart: always
    container_name: nexus
    image: sonatype/nexus3
    volumes:
      - nexus-data:/nexus-data
    ports:
      - "8081:8081"

volumes:
  nexus-data:
```
日常运维
```bash
# 启动
docker-compose up -d
# 关闭
docker-compose down 
# 备份
docker run --rm --volumes-from nexus -v $(pwd):/backup alpine tar cvf /backup/nexus-$(date +"%Y%m%d%H%M").tar /nexus-data

# 还原具体备份日期的文件
docker run --rm --volumes-from nexus -v $(pwd):/backup alpine sh -c "cd /nexus-data && tar xvf /backup/nexus-202202081212.tar --strip 1"

```
# 配置存储库
使用管理员账户登录后，进入配置页面
![image.png](https://cdn.nlark.com/yuque/0/2022/png/173778/1644299886908-02b64b10-6e11-4d39-950a-c0283fa2b749.png#clientId=ua1be7987-1869-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=1057&id=u031e517e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1057&originWidth=1920&originalType=binary&ratio=1&rotation=0&showTitle=false&size=165906&status=done&style=none&taskId=u2bf4bff4-6df7-447d-b8fd-3bb125618e6&title=&width=1920)


## 创建 Blob存储（非必须）
![image.png](https://cdn.nlark.com/yuque/0/2022/png/173778/1644300177137-76418a91-cc90-43b8-9d9a-9c828eaa0ea8.png#clientId=ua1be7987-1869-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=783&id=uce19a931&margin=%5Bobject%20Object%5D&name=image.png&originHeight=783&originWidth=896&originalType=binary&ratio=1&rotation=0&showTitle=false&size=91155&status=done&style=none&taskId=u955b69ff-23b4-4b20-8461-8614b969ef2&title=&width=896)


## 管理Repository
### Repository分类
#### Proxy Repository
代理仓库是一个连接到远程的仓库。所有的请求，如果在本地没有找到对应的组件，就会转发到远程的仓库。然后会缓存远端仓库返回的组件。对同一组件的后续请求将从本地存储中完成，因此消除了再次从远程存储库检索组件的网络带宽和时间开销。
#### Hosted Repository
本地仓库，跟官方仓库一样的私有化仓库
#### Repository Group
仓库组合，能够组合多个仓库为一个地址提供服务


### Repository 建立
#### 新建 Hosted Repository
Hosted Repository 用于发布公司内部的包
![image.png](https://cdn.nlark.com/yuque/0/2022/png/173778/1644455556552-dfd5038d-5cdc-458b-b727-b13e34f260f1.png#clientId=ua1be7987-1869-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=913&id=u777a705d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=913&originWidth=1232&originalType=binary&ratio=1&rotation=0&showTitle=false&size=136477&status=done&style=none&taskId=u0d98051b-4c9a-4960-9f65-f92d6030c1a&title=&width=1232)


#### 新建  Proxy Repository
![image.png](https://cdn.nlark.com/yuque/0/2022/png/173778/1644455661110-08005971-e08b-4128-9453-b21f28c58cea.png#clientId=ua1be7987-1869-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=969&id=uc6fdaaa5&margin=%5Bobject%20Object%5D&name=image.png&originHeight=969&originWidth=1345&originalType=binary&ratio=1&rotation=0&showTitle=false&size=163257&status=done&style=none&taskId=u0a33ec6f-a62f-4cfb-b66c-dfc5a076448&title=&width=1345)


#### 新建 Group Repositroy
![image.png](https://cdn.nlark.com/yuque/0/2022/png/173778/1644455712750-4be5769d-4e84-4f64-8970-608077287a7c.png#clientId=ua1be7987-1869-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=955&id=u1d81d62d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=955&originWidth=1341&originalType=binary&ratio=1&rotation=0&showTitle=false&size=136863&status=done&style=none&taskId=u86667eb9-048f-4161-9a4e-6868b5901e5&title=&width=1341)


#### 完成repostiroy的设置 
![image.png](https://cdn.nlark.com/yuque/0/2022/png/173778/1644455766037-99f18389-dd94-4dee-ac66-867bad8c3326.png#clientId=ua1be7987-1869-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=411&id=u174a65c8&margin=%5Bobject%20Object%5D&name=image.png&originHeight=411&originWidth=1595&originalType=binary&ratio=1&rotation=0&showTitle=false&size=78980&status=done&style=none&taskId=u6f5798d4-3cb3-493d-990a-d940949b7c7&title=&width=1595)
