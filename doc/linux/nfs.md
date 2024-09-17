# NFS

## 安装

```bash
# 安装NFS服务
sudo apt-get install nfs-kernel-server
# 创建共享目录
sudo mkdir -p /nfsdata/
# 设置权限
sudo chmod -R 777 /nfsdata
# ubuntu为当前用户
sudo chown ubuntu:ubuntu /nfsdata/ -R
# 编译NFS配置we你按
sudo vim /etc/exports
```

##  /etc/exports
```bash
# *:表示允许任何网段 IP 的系统访问该 NFS 目录
# no_root_squash:访问nfs server共享目录的用户如果是root的话，它对该目录具有root权限
# no_all_squash:保留共享文件的UID和GID（默认）
# rw:可读可写
# sync:请求或者写入数据时，数据同步写入到NFS server的硬盘中后才会返回，默认选项
# secure:NFS客户端必须使用NFS保留端口（通常是1024以下的端口），默认选项。
# insecure:允许NFS客户端不使用NFS保留端口（通常是1024以上的端口）

/nfsdata/ *(rw,sync,no_root_squash,insecure)
# k8s
/nfsdata/ 192.168.100.0/24(rw,sync,no_root_squash,insecure)
```

```bash
# 下面命令慎用，导致客户端被挂起
sudo systemctl restart rpcbind
sudo systemctl restart nfs-server
sudo systemctl status nfs-server

# 最好使用下面命令来重新加载配置

# -a 全部挂载或者全部卸载
# -r 重新挂载
# -u 卸载某一个目录
# -v 显示共享目录
sudo exportfs -arv
# 显示挂载目录
sudo exportfs -v
```

## k8s 部署

在 k8s master上，所有节点安装client

```bash
apt install nfs-common

wget https://raw.githubusercontent.com/kubernetes-sigs/nfs-subdir-external-provisioner/master/deploy/rbac.yaml
wget https://raw.githubusercontent.com/kubernetes-sigs/nfs-subdir-external-provisioner/master/deploy/deployment.yaml
wget https://raw.githubusercontent.com/kubernetes-sigs/nfs-subdir-external-provisioner/master/deploy/class.yaml

# 注意修改里面的namespace, 如果不需要，则忽略
kubectl apply -f rbac.yaml
kubectl apply -f deployment.yaml
kubectl apply -f class.yaml




```


## 注意

* 尽量指定主机名或IP或IP段最小化授权可以访问NFS 挂载的资源的客户端；注意如果在k8s集群中配合nfs-client-provisioner使用的话，这里需要指定pod的IP段，否则nfs-client-provisioner pod无法启动，报错 mount.nfs: access denied by server while mounting
* 经测试参数insecure必须要加，否则客户端挂载出错mount.nfs: access denied by server while mounting
* NFS服务不能随便重启，要重启，就需要先去服务器上，把挂载的目录卸载下来
