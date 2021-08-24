# Install

## 单机部署4节点

FISCO BCOS提供了`build_chain.sh`脚本帮助用户快速搭建FISCO BCOS联盟链.

`build_chain.sh`脚本用于快速生成一条链中节点的配置文件，脚本依赖于`openssl`请根据自己的操作系统安装`openssl 1.0.2`以上版本。脚本的源码位于[github源码](https://github.com/FISCO-BCOS/FISCO-BCOS/blob/master/tools/build_chain.sh) 或者使用最新发布的[release](https://github.com/FISCO-BCOS/FISCO-BCOS/releases)版本

```bash
./build_chain.sh -l 127.0.0.1:4 				# 部署一个4节点的联盟链 			
```

[参考](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/manual/build_chain.html)



```bash
sudo apt install -y openssl curl
cd ~ && mkdir -p fisco && cd fisco
curl -#LO https://github.com/FISCO-BCOS/FISCO-BCOS/releases/download/v2.7.2/build_chain.sh && chmod u+x build_chain.sh
bash build_chain.sh -l 127.0.0.1:4 -p 30300,20200,8545
# 其中-p选项指定起始端口，分别是p2p_port,channel_port,jsonrpc_port

# 启动所有节点
bash nodes/127.0.0.1/start_all.sh
# 检查进程是否启动
ps -ef | grep -v grep | grep fisco-bcos
# 查看节点node0链接的节点数
tail -f nodes/127.0.0.1/node0/log/log*  | grep connected
# 检查是否在共识
tail -f nodes/127.0.0.1/node0/log/log*  | grep +++
# 正常情况会不停输出++++Generating seal，表示共识正常。

# 安装java
sudo add-apt-repository ppa:linuxuprising/java
sudo apt update
sudo apt install oracle-java16-installer
# 移除安装java
sudo add-apt-repository --remove ppa:linuxuprising/java
sudo apt-get remove oracle-java16-installer

# 部署控制台
curl -LO https://github.com/FISCO-BCOS/console/releases/download/v2.7.2/download_console.sh && bash download_console.sh
# 拷贝控制台配置文件
cp -n console/conf/config-example.toml console/conf/config.toml
# 配置控制台证书
cp -r nodes/127.0.0.1/sdk/* console/conf/
# 启动并使用控制台
cd ~/fisco/console && bash start.sh
```

# Java SDK

## 常见问题

### Algorithm constraints check failed on key EC with size of 256bits

在`JAVA_HOME\conf\security\java.security`中修改`jdk.certpath.disabledAlgorithms`和`jdk.disabled.namedCurves`

# webbase 

## install

> ubuntu

```bash
sudo apt-get install -y python3-pip
sudo pip3 install PyMySQL

wget https://osp-1257653870.cos.ap-guangzhou.myqcloud.com/WeBASE/releases/download/v1.5.2/webase-deploy.zip
unzip webase-deploy.zip
cd webase-deploy
# 修改 common.properties
fisco.dir=no 		# 使用内嵌的节点

http://localhost:5000
# admin/Abcd1234
```



服务部署后，需要对各服务进行启停操作，可以使用以下命令

```bahs
# 一键部署
部署并启动所有服务        python3 deploy.py installAll
停止一键部署的所有服务    python3 deploy.py stopAll
启动一键部署的所有服务    python3 deploy.py startAll
# 各子服务启停
启动FISCO-BCOS节点:      python3 deploy.py startNode
停止FISCO-BCOS节点:      python3 deploy.py stopNode
启动WeBASE-Web:          python3 deploy.py startWeb
停止WeBASE-Web:          python3 deploy.py stopWeb
启动WeBASE-Node-Manager: python3 deploy.py startManager
停止WeBASE-Node-Manager: python3 deploy.py stopManager
启动WeBASE-Sign:        python3 deploy.py startSign
停止WeBASE-Sign:        python3 deploy.py stopSign
启动WeBASE-Front:        python3 deploy.py startFront
停止WeBASE-Front:        python3 deploy.py stopFront
# 可视化部署
部署并启动可视化部署的所有服务  python3 deploy.py installWeBASE
停止可视化部署的所有服务  python3 deploy.py stopWeBASE
启动可视化部署的所有服务  python3 deploy.py startWeBASE
```



#### 问题

登陆界面没有验证码，登陆界面提示WeBASE-Node-Manager系统异常

因为数据库使用mysql8 修改 `/webase-node-mgr/conf/application.yml` 文件中的`url`字段，在最后添加`&useSSL=false`



# WeEvent

## Bash Install

https://weeventdoc.readthedocs.io/zh_CN/latest/install/quickinstall.html

```bash
wget https://github.com/WeBankFinTech/WeEvent/releases/download/v1.6.0/weevent-1.6.0.tar.gz
tar -zxf weevent-1.6.0.tar.gz

```



# 源码

## libethcore

区块的核心代码
