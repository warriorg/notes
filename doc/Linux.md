## 目录结构

* **/bin** 最经常使用的命令
* **/boot** 启动Linux时使用的一些核心文件，包括一些连接文件以及镜像文件。
* **/dev** 存放的是Linux的外部设备，在Linux中访问设备的方式和访问文件的方式是相同的。
* **/etc** 系统管理所需要的配置文件和子目录
* **/home** 用户的主目录
* **/lib** 存放着系统最基本的动态连接共享库
* **/lost+found** 这个目录一般情况下是空的，当系统非法关机后，这里就存放了一些文件
* **/media** linux系统会自动识别一些设备，例如U盘、光驱等等，当识别后，linux会把识别的设备挂载到这个目录下。
* **/mnt** 让用户临时挂载别的文件系统的
* **/opt** 给主机额外安装软件所摆放的目录。比如你安装一个ORACLE数据库则就可以放到这个目录下。默认是空的。
* **/proc** 一个虚拟的目录，它是系统内存的映射，我们可以通过直接访问这个目录来获取系统信息
* **/root** 目录为系统管理员
* **/sbin** s就是Super User的意思，这里存放的是系统管理员使用的系统管理程序
* **/selinux** Redhat/CentOS所特有的目录，Selinux是一个安全机制，类似于windows的防火墙，但是这套机制比较复杂，这个目录就是存放selinux相关的文件的。
* **/srv** 该目录存放一些服务启动之后需要提取的数据。
* **/sys** sysfs文件系统集成了下面3种文件系统的信息：针对进程信息的proc文件系统、针对设备的devfs文件系统以及针对伪终端的devpts文件系统。
* **/tmp** 存放一些临时文件
* **/usr** 用户的很多应用程序和文件都放在这个目录下，类似于windows下的program files目录

  * **/usr/bin** 系统用户使用的应用程序。

  * **/usr/sbin** 超级用户使用的比较高级的管理程序和系统守护程序。

  * **/usr/src** 内核源代码默认的放置目录。
* **/var** 这个目录中存放着在不断扩充着的东西，我们习惯将那些经常被修改的目录放在这个目录下。包括各种日志文件
* **/run** 是一个临时文件系统，存储系统启动以来的信息

## 常用命令

```bash
uname -a   # 显示内核信息
```


## 用户设置
adduser： 会自动为创建的用户指定主目录、系统shell版本，会在创建时输入用户密码。

useradd：需要使用参数选项指定上述基本设置，如果不使用任何参数，则创建的用户无密码、无主目录、没有指定shell版本。

userdel 删除用户

```bash
adduser apple
useradd -g group apple #新建用户添加到组
passwd apple # 修改密码

cat /etc/passwd # 查看所有用户
cat /etc/group # 查看所有组
w  # 查看活跃用户
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

 ## 网络
 ### DNS
 /etc/resolv.conf

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

## SSH设置

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

### 反向tunnel
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
​	  
```bash
data -R  # 查看当前设置
sudo tzselect  # 选择时区 命令不存在使用 dpkg-reconfigure tzdata
sudo date -s    # 修改本地时间
sudo cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime    # 防止系统重启后时区改变
sudo ntpdate cn.pool.ntp.org        #命令更新时间
```