####用户设置
adduser： 会自动为创建的用户指定主目录、系统shell版本，会在创建时输入用户密码。

useradd：需要使用参数选项指定上述基本设置，如果不使用任何参数，则创建的用户无密码、无主目录、没有指定shell版本。

userdel 删除用户

```bash
adduser apple
useradd -g group apple #新建用户添加到组
passwd apple # 修改密码
```

组

```
groupadd group
usermod -G groupname username  #已有的用户增加工作组

```

从组中删除用户
编辑/etc/group 找到GROUP1那一行，删除用户
或者用命令

 ```
 gpasswd -d A GROUP
 ```

#### 磁盘
```bash
fdisk -l
mount /dev/vdb1 /mnt
df -h
```
##### 开机自动挂在
`edit /etc/fstab`

```bash
/dev/sda3      /mnt         ext4    defaults        1 1 
```
>*	第一列为设备号或该设备的卷标 	
>*	第二列为挂载点 	
>* 	第三列为文件系统 	
>*	第四列为文件系统参数 	
>*	第五列为是否可以用demp命令备份。0：不备份，1：备份，2：备份，但比1重要性小。设置了该参数后，Linux中使用dump命令备份系统的时候就可以备份相应设置的挂载点了。
>*	第六列为是否在系统启动的时候，用fsck检验分区。因为有些挂载点是不需要检验的，比如：虚拟内存swap、/proc等。0：不检验，1：要检验，2要检验，但比1晚检验，一般根目录设置为1，其他设置为2就可以了

#### SSH设置

```bash
vi /etc/ssh/sshd_config
service sshd restart
#or
service ssh restart
```

```bash
#sshd_config 文件
#端口
Port 22
#root 登录禁止
PermitRootLogin no
```

#####反向代理
1. 在被控端运行		
	
```bash
ssh -f -N -R 10000:localhost:22 username@主控端ip
#在主控端10000端口和被控端的22端口上建立了一个通道
#如果主控端的ssh端口號不是默認的還要加 –p port參數
```
2. 在主控端运行

```bash
ssh username@localhost -p 10000
#username是你被控端的username，10000就是刚才的那个端口号。
```
#####ssh 证书登录

```bash
ssh-keygen -t rsa
//scp ~/.ssh/id_rsa.pub root@114.55.148.240:~
scp -P 26611 /Users/warrior/id_rsa.pub  jinyi@114.55.148.240:/home/jinyi/id_rsa.pub
#remote
cat id_rsa.pub >>~/.ssh/authorized_keys
/etc/init.d/ssh restart

#连接
ssh -i ~/.ssh/id_rsa root@114.55.148.240

#edit /etc/ssh/ssh_config
#增加
IdentityFile ~/.ssh/id_rsa
//如果多个
IdentityFile ~/.ssh/some_rsa

ssh root@114.55.148.240
```

### grep			
```base
$grep -5 'parttern' inputfile #打印匹配行的前后5行
$grep -C 5 'parttern' inputfile #打印匹配行的前后5行
$grep -A 5 'parttern' inputfile #打印匹配行的后5行
$grep -B 5 'parttern' inputfile #打印匹配行的前5行
```

#### 更改目录权限(不包含文件)
`find Folder -type d -exec chmod 0777 {} +`

#### 查看各文件夹大小命令
`du -h --max-depth=1`

####查看进程，按内存从大到小

`ps -e -o "%C : %p : %z : %a"|sort -k5 -nr`

####查看进程，按CPU利用率从大到小排序
ps -e -o "%C : %p : %z : %a"|sort -nr

####查看剩余内存

free -m |grep "Mem" | awk "{print $2}"

####查看磁盘占用

df -h


###Supervisor 
> 一个管理进程的工具，可以随系统启动而启动服务，它还时刻监控服务进程，如果服务进程意外退出，Supervisor可以自动重启服务。

#### 查看SELinux状态及关闭SELinux
```bash
sestatus -v 			#如果SELinux status参数为enabled即为开启状态
getenforce          	#也可以用这个命令检查 Permissive 零时关闭
setenforce 0       		#设置SELinux 成为permissive模式 零时关闭
setenforce 1 			#设置SELinux 成为enforcing模式
```			
永久关闭    
`修改/etc/selinux/config 文件`   

```base
SELINUX=enforcing	#开启
SELINUX=disabled    #关闭
```

###查找被占用的端口					
```bash
lsof -i:8700 或者 lsof -i | grep 8700

```

### Ubuntu

```
update-manager # 升级系统 图形界面
sudo do-release-upgrade  # 升级系统
```


###Centos
```
yum install epel-release  #增加epel源
```

###防火墙
``` bash
systemctl stop firewalld.service #停止firewall
systemctl disable firewalld.service #禁止firewall开机启动
systemctl enable iptables.service #设置防火墙开机启动
systemctl restart iptables.service #重启防火墙使配置生效
```

###时间
设置时区 
	  
```bash
data -R  # 查看当前设置
sudo tzselect  # 选择时区 命令不存在使用 dpkg-reconfigure tzdata
sudo date -s    # 修改本地时间
sudo cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime    # 防止系统重启后时区改变
sudo ntpdate cn.pool.ntp.org        #命令更新时间
```