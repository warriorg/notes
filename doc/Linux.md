*  CPU上下文切换
  先把前一个任务的CPU上下文（也就是CPU寄存器和程序计数器)保存起来，然后加载新任务的上下文到这些寄存器和程序计数器，最后在跳转到程序计数器所指的新位置，运行新任务。
  1. 进程上下文切换
  2. 线程上下文切换
  3. 中断上下文切换
*  ==自愿上下文切换== 进程无法获取所需资源，导致的上下文切换。比如：I/O、内存等系统资源不足时，就会发生自愿上下文切换。
*  ==非自愿上下文切换== 则是指进程由于时间片已到等原因，被系统强制调度，进而发生的上下文切换。比如：大量进程都在争抢CPU时，就容易发生自愿上下文切换。



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



## 命令工具

### crontab 
定时任务

### uname 
显示内核信息
```bash
uname -a   # 显示内核信息

# 适用于所有的linux，包括Redhat、SuSE、Debian、Centos等发行版。
lsb_release -a  
# RedHat,CentOS
cat /etc/redhat-release
```

### uptime 系统平均负载
系统总共运行了多长时间和系统的平均负载
```bash
~# uptime
13:49:31 up 188 days,  4:27,  1 users,  load average: 0.01, 0.03, 0.00

13:49:31             # 系统当前时间
up 188 days,  4:27   # 主机已运行时间,时间越大，说明你的机器越稳定。
1 users               # 用户连接数，是总连接数而不是用户数
load average: 0.00, 0.00, 0.00         # 系统平均负载，统计最近1，5，15分钟的系统平均负载, 如果是1 的话，说明在1核CPU上，使用率是100%， 在2核心CPU上，50%！
```

### netstat
```bash
netstat -ltnp  			# 列出端口	
```

### ps
```bash
ps -aef
```
### ln

* 硬链接(Hard Link) 硬链接说白了是一个指针，指向文件索引节点，系统并不为它重新分配inode
```bash
ln [options] existingfile newfile	# sexistingfile 待建立链接文件的文件，newfile是新创建的链接文件
ln [options] existingfile-list directory
# -f 建立时，将同档案名删除.
# -i 删除前进行询问.
```
* 软链接(Soft Link), 软链接又称为符号链接（Symbolic link）。符号连接相当于Windows下的快捷方式。

```bash
ln -s abc cde 	# 建立abc 的软连接
ln abc cde 		# 建立abc的硬连接，
```

### &、ctrl + z、fg、bg、jobs、
* **&** 后台执行命令
* **ctrl + z** 将一个正在前台执行的命令放到后台，并且暂停
* **jobs** 查看当前有多少在后台运行的命令
* **fg** 将后台中的命令调至前台继续运行
* **bg** 将一个在后台暂停的命令，变成继续执行
```bash
$ htop
# 按下 ctrl+z htop 后台执行
[1]+  Stopped                 htop
$ jobs -l
[1]+ 46989 Stopped                 htop
$ bg
[1]+ htop &

[1]+  Stopped                 htop
$ fg
htop
```

### grep			
```base
$grep -5 'parttern' inputfile #打印匹配行的前后5行
$grep -C 5 'parttern' inputfile #打印匹配行的前后5行
$grep -A 5 'parttern' inputfile #打印匹配行的后5行
$grep -B 5 'parttern' inputfile #打印匹配行的前5行
```

### perf 

分析cpu性能问题

```bash
$ perf top
Samples: 833  of event 'cpu-clock', Event count (approx.): 97742399
Overhead  Shared Object       Symbol
   7.28%  perf                [.] 0x00000000001f78a4
   4.72%  [kernel]            [k] vsnprintf
   4.32%  [kernel]            [k] module_get_kallsym
   3.65%  [kernel]            [k] _raw_spin_unlock_irqrestore
...

```

* **Overhead** 是该符号的性能事件在所有的采样中的比例，用百分比来表示。

* **Shared** 是该函数或指令所在的动态共享对象，如内核、进程名、动态连结库名、内核模块名等。
* **Object** 动态共享对象的类型。比如[.] 表示用户空间的可执行程序、或者动态连结库，而[k]在表示内核空间。
* **Symbol** 符号名，也就是函数名。当函数名未知时， 用十六进制的地址来表示。

```bash
$ perf record # 按 Ctrl+C 终止采样
[ perf record: Woken up 1 times to write data ]
[ perf record: Captured and wrote 0.452 MB perf.data (6093 samples) ]

$ perf report # 展示类似于 perf top 的报告
```

> perf top 和 perf record 加上 -g 参数，开启调用关系的采样，方便我们根据调用链来分析性能问题

### vmstat

vmstat工具的使用是通过两个数字参数来完成的，第一个参数是采样的时间间隔数，单位是秒，第二个参数是采样的次数

```bash
# 以 10 个线程运行 5 分钟的基准测试，模拟多线程切换
$ sysbench --threads=10 --max-time=300 threads run

# 每隔 1 秒输出 1 组数据（需要 Ctrl+C 才结束)
$ vmstat 1
# 每隔 5 秒输出 1 组数据, 共输出2组
$ vmstat 5 2
```

* **r** 表示运行队列(就是说多少个进程真的分配到CPU)，我测试的服务器目前CPU比较空闲，没什么程序在跑，当这个值超过了CPU数目，就会出现CPU瓶颈了。这个也和top的负载有关系，一般负载超过了3就比较高，超过了5就高，超过了10就不正常了，服务器的状态很危险。top的负载类似每秒的运行队列。如果运行队列过大，表示你的CPU很繁忙，一般会造成CPU使用率很高。
* **b** 表示阻塞的进程,这个不多说，进程阻塞。
* **swpd** 虚拟内存已使用的大小，如果大于0，表示你的机器物理内存不足了，如果不是程序内存泄露的原因，那么你该升级内存了或者把耗内存的任务迁移到其他机器。
* **free**   空闲的物理内存的大小，我的机器内存总共8G，剩余3415M。
* **buff**   Linux/Unix系统是用来存储，目录里面有什么内容，权限等的缓存，我本机大概占用300多M
* **cache** cache直接用来记忆我们打开的文件,给文件做缓冲，我本机大概占用300多M(这里是Linux/Unix的聪明之处，把空闲的物理内存的一部分拿来做文件和目录的缓存，是为了提高 程序执行的性能，当程序使用内存时，buffer/cached会很快地被使用。)
* **si**  每秒从磁盘读入虚拟内存的大小，如果这个值大于0，表示物理内存不够用或者内存泄露了，要查找耗内存进程解决掉。我的机器内存充裕，一切正常。
* **so**  每秒虚拟内存写入磁盘的大小，如果这个值大于0，同上。
* **bi**  块设备每秒接收的块数量，这里的块设备是指系统上所有的磁盘和其他块设备，默认块大小是1024byte，我本机上没什么IO操作，所以
* 一直是0，但是我曾在处理拷贝大量数据(2-3T)的机器上看过可以达到140000/s，磁盘写入速度差不多140M每秒
* **bo** 块设备每秒发送的块数量，例如我们读取文件，bo就要大于0。bi和bo一般都要接近0，不然就是IO过于频繁，需要调整。
* **in** 每秒CPU的中断次数，包括时间中断
* **cs** 每秒上下文切换次数，例如我们调用系统函数，就要进行上下文切换，线程的切换，也要进程上下文切换，这个值要越小越好，太大了，要考虑调低线程或者进程的数目,例如在apache和nginx这种web服务器中，我们一般做性能测试时会进行几千并发甚至几万并发的测试，选择web服务器的进程可以由进程或者线程的峰值一直下调，压测，直到cs到一个比较小的值，这个进程和线程数就是比较合适的值了。系统调用也是，每次调用系统函数，我们的代码就会进入内核空间，导致上下文切换，这个是很耗资源，也要尽量避免频繁调用系统函数。上下文切换次数过多表示你的CPU大部分浪费在上下文切换，导致CPU干正经事的时间少了，CPU没有充分利用，是不可取的。
* **us** 用户CPU时间，我曾经在一个做加密解密很频繁的服务器上，可以看到us接近100,r运行队列达到80(机器在做压力测试，性能表现不佳)。
* **sy** 系统CPU时间，如果太高，表示系统调用时间长，例如是IO操作频繁。
* **id**  空闲 CPU时间，一般来说，id + us + sy = 100,一般我认为id是空闲CPU使用率，us是用户CPU使用率，sy是系统CPU使用率。
* **wt** 等待IO CPU时间。

### pidstat
查看每个进程的详细情况
* -w 输出进程切换指标
* -wt 输出线程上下文切换指标
* -u 输出CPU使用指标
```bash
# 每隔5秒输出1组数据 (需要 Ctrl + C结束)
$ pidstat -w 5
Linux 3.10.0-862.14.4.el7.x86_64 (instance-xisc86cd) 	12/14/2018 	_x86_64_	(2 CPU)

03:45:37 PM   UID       PID   cswch/s nvcswch/s  Command
03:45:42 PM     0         1      0.80      0.00  systemd
03:45:42 PM     0         9     40.12      0.00  rcu_sched

```
* cswch/s (voluntary context switches) 每秒自愿上下文切换的次数
* nvcswch/s (non voluntary context switches) 每秒非自愿上下文切换的次数 

### stress, sysstat

系统压力测试工具

```bash
# ubuntu 安装
$ apt install stress sysstat
```
#### 1. mpstat
多核 CPU 性能分析工具，用来实时查看每个 CPU 的性能指标，以及所有 CPU 的平均指标。
#### 2. pidstat
实时查看进程的 CPU、内存、I/O 以及上下文切换等性能指标。
```bash
# 模拟一个cpu使用100%的情况
$ stress --cpu 1 --timeout 600
# 模拟 I/O 压力，即不停地执行 sync
$ stress -i 1 --timeout 60
# 模拟的是 8 个进程
$ stress -c 8 --timeout 600

# -P ALL 表示监控所有 CPU，后面数字 5 表示间间隔 5 秒后输出一组数据
$ mpstat -P ALL 5

# 哪个进程导致了 CPU 使用率为 100%，间隔 5 秒后输出一组数据 
$ pidstat -u 5 1
```

### 用户设置

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

```bash
groups 								#查看当前用户的组
groups user 						# 查看用户的组
groupadd group
usermod -G groupname username  		#已有的用户增加工作组

```

从组中删除用户
编辑/etc/group 找到GROUP1那一行，删除用户
或者用命令

 ```
 gpasswd -d A GROUP
 ```

### 网络



### mtr
比ping屌

###Supervisor 
> 一个管理进程的工具，可以随系统启动而启动服务，它还时刻监控服务进程，如果服务进程意外退出，Supervisor可以自动重启服务。



###查找被占用的端口					
```bash
lsof -i:8700 或者 lsof -i | grep 8700
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

### 语言

配置文件地址 **/etc/locale.conf**

```bash
# centos 设置语言
localectl set-locale LANG=en_US.utf8
```

### Font
```bash
fc-list							# fc-list查看已安装的字体
fc-list :lang=zh				# 查看中文字体
fc-cache -vf					# 扫描字体目录并生成字体信息的缓存
```

### wget

```bash
# 下载oracle jdk
wget -c --header "Cookie: oraclelicense=accept-securebackup-cookie" https://download.oracle.com/otn-pub/java/jdk/8u191-b12/2787e4a523244c269598db4e85c51e0c/jdk-8u191-li
```

### 磁盘
```bash
fdisk -l
mount /dev/vdb1 /mnt
df -h
```
#### 开机自动挂在
`edit /etc/fstab`

```bash
/dev/sda3      /mnt         ext4    defaults        1 1 
```
*	第一列为设备号或该设备的卷标 	
*	第二列为挂载点 	
*	第三列为文件系统 	
*	第四列为文件系统参数 	
*	第五列为是否可以用demp命令备份。0：不备份，1：备份，2：备份，但比1重要性小。设置了该参数后，Linux中使用dump命令备份系统的时候就可以备份相应设置的挂载点了。
*	第六列为是否在系统启动的时候，用fsck检验分区。因为有些挂载点是不需要检验的，比如：虚拟内存swap、/proc等。0：不检验，1：要检验，2要检验，但比1晚检验，一般根目录设置为1，其他设置为2就可以了

### 更改目录权限(不包含文件)
`find Folder -type d -exec chmod 0777 {} +`

### 查看各文件夹大小命令
`du -h --max-depth=1`

###查看进程，按内存从大到小

`ps -e -o "%C : %p : %z : %a"|sort -k5 -nr`

###查看进程，按CPU利用率从大到小排序
`ps -e -o "%C : %p : %z : %a"|sort -nr`

###查看剩余内存
`free -m |grep "Mem" | awk "{print $2}"`

###查看磁盘占用
`df -h`

## SSH设置

```bash
vi /etc/ssh/sshd_config
service sshd restart
#or
service ssh restart
```

```bash
#sshd_config 文件

Port 22		#端口
PermitRootLogin no 	#root 登录禁止
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
# scp ~/.ssh/id_rsa.pub root@xxx.xxx.xxx.xxx:~
scp -P 26611 /Users/warrior/id_rsa.pub  jinyi@xxx.xxx.xxx.xx:/home/jinyi/id_rsa.pub
#remote
cat id_rsa.pub >>~/.ssh/authorized_keys
/etc/init.d/ssh restart

#连接
ssh -i ~/.ssh/id_rsa root@xxx.xxx.xxx.xxx

#edit /etc/ssh/ssh_config
#增加
IdentityFile ~/.ssh/id_rsa
//如果多个
IdentityFile ~/.ssh/some_rsa

ssh root@114.55.148.240
```

ssh 登录脚本

```bash
#!/usr/bin/expect -f 
# expect是一个基于Tcl的用于自动交互操作的工具语言，它适合用来编写需要交互的自动化脚本

set host ip	
set port 28916
set user root
set password password
set timeout -1

# spawn用来启动一个新的进程
spawn ssh $user@$host -p $port
# expect用来等待期望的字符串, 可以是正则表达式
# expect会一直等待下去，除非收到的字符串与预期的格式匹配，或者到了超期时间
expect "*password:*"

# 用来发送一个字符串
send "$password\r"
# 等待接受文件结束符
interact

expect eof

```
### scp
### sftp
### sshfs

挂载远程目录到本地

## Ubuntu
```bash
update-manager # 升级系统 图形界面
sudo do-release-upgrade  # 升级系统
```
#### 启用root
```bash
sudo passwd root       # 设置密码
su
```


## Centos

### yum
yum 的配置文件分为两部分：main 和repository

* main 部分定义了全局配置选项，整个yum 配置文件应该只有一个main。常位于/etc/yum.conf 中。
* repository 部分定义了每个源/服务器的具体配置，可以有一到多个。常位于/etc/yum.repo.d 目录下的各文件中
```bash
yum install epel-release  		# 增加epel源
yum -y list java*   			# 搜索安装包
```

### 查看SELinux状态及关闭SELinux
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

### 主机名
`/etc/sysconfig/network`
```bash
hostname=hostname
```

### DNS
`/etc/resolv.conf`
```bash
DNS1=114.114.114.114
```

设置静态IP

`vim /etc/sysconfig/network-scripts/ifcfg-ens33`

```bash
TYPE=Ethernet
PROXY_METHOD=none
BROWSER_ONLY=no
BOOTPROTO=static  # 设置为static
DEFROUTE=yes
IPV4_FAILURE_FATAL=no
IPV6INIT=yes
IPV6_AUTOCONF=yes
IPV6_DEFROUTE=yes
IPV6_FAILURE_FATAL=no
IPV6_ADDR_GEN_MODE=stable-privacy
NAME=ens33
UUID=5aa4d499-39ff-43dc-86c9-e049ec71e2bc
DEVICE=ens33
ONBOOT=yes			# 开机启动
# 设置静态ip地址
IPADDR=192.168.2.230		
GATEWAY=192.168.2.1		
NETMASK=255.255.255.0
```



## tmux

```bash
tmux							# start new
tmux new -s myname   			# start new with session name
tmux a  						#  (or at, or attach)  attach
tmux a -t myname				# attach to named
tmux ls							# list sessions
tmux kill-session -t myname		# kill session
# Kill all the tmux sessions
tmux ls | grep : | cut -d. -f1 | awk '{print substr($1, 0, length($1)-1)}' | xargs kill
```

In tmux, hit the prefix `ctrl+b` (my modified prefix is ctrl+a) and then:
#### Sessions

```
:new<CR>  new session
s  list sessions
$  name session
```

#### Windows (tabs)

```
c  create window
w  list windows
n  next window
p  previous window
f  find window
,  name window
&  kill window
```

#### Panes (splits)

```
%  vertical split
"  horizontal split

o  swap panes
q  show pane numbers
x  kill pane
+  break pane into window (e.g. to select text by mouse to copy)
-  restore pane from window
⍽  space - toggle between layouts
<prefix> q (Show pane numbers, when the numbers show up type the key to goto that pane)
<prefix> { (Move the current pane left)
<prefix> } (Move the current pane right)
<prefix> z toggle pane zoom
```

#### Sync Panes

You can do this by switching to the appropriate window, typing your Tmux prefix (commonly Ctrl-B or Ctrl-A) and then a colon to bring up a Tmux command line, and typing:

```
:setw synchronize-panes
```

You can optionally add on or off to specify which state you want; otherwise the option is simply toggled. This option is specific to one window, so it won’t change the way your other sessions or windows operate. When you’re done, toggle it off again by repeating the command. [tip source](http://blog.sanctum.geek.nz/sync-tmux-panes/)

#### Resizing Panes

You can also resize panes if you don’t like the layout defaults. I personally rarely need to do this, though it’s handy to know how. Here is the basic syntax to resize panes:

```
PREFIX : resize-pane -D (Resizes the current pane down)
PREFIX : resize-pane -U (Resizes the current pane upward)
PREFIX : resize-pane -L (Resizes the current pane left)
PREFIX : resize-pane -R (Resizes the current pane right)
PREFIX : resize-pane -D 20 (Resizes the current pane down by 20 cells)
PREFIX : resize-pane -U 20 (Resizes the current pane upward by 20 cells)
PREFIX : resize-pane -L 20 (Resizes the current pane left by 20 cells)
PREFIX : resize-pane -R 20 (Resizes the current pane right by 20 cells)
PREFIX : resize-pane -t 2 20 (Resizes the pane with the id of 2 down by 20 cells)
PREFIX : resize-pane -t -L 20 (Resizes the pane with the id of 2 left by 20 cells)
```

#### Copy mode:

Pressing PREFIX [ places us in Copy mode. We can then use our movement keys to move our cursor around the screen. By default, the arrow keys work. we set our configuration file to use Vim keys for moving between windows and resizing panes so we wouldn’t have to take our hands off the home row. tmux has a vi mode for working with the buffer as well. To enable it, add this line to .tmux.conf:

```
setw -g mode-keys vi
```

With this option set, we can use h, j, k, and l to move around our buffer.

To get out of Copy mode, we just press the ENTER key. Moving around one character at a time isn’t very efficient. Since we enabled vi mode, we can also use some other visible shortcuts to move around the buffer.

For example, we can use "w" to jump to the next word and "b" to jump back one word. And we can use "f", followed by any character, to jump to that character on the same line, and "F" to jump backwards on the line.

```
   Function                vi             emacs
   Back to indentation     ^              M-m
   Clear selection         Escape         C-g
   Copy selection          Enter          M-w
   Cursor down             j              Down
   Cursor left             h              Left
   Cursor right            l              Right
   Cursor to bottom line   L
   Cursor to middle line   M              M-r
   Cursor to top line      H              M-R
   Cursor up               k              Up
   Delete entire line      d              C-u
   Delete to end of line   D              C-k
   End of line             $              C-e
   Goto line               :              g
   Half page down          C-d            M-Down
   Half page up            C-u            M-Up
   Next page               C-f            Page down
   Next word               w              M-f
   Paste buffer            p              C-y
   Previous page           C-b            Page up
   Previous word           b              M-b
   Quit mode               q              Escape
   Scroll down             C-Down or J    C-Down
   Scroll up               C-Up or K      C-Up
   Search again            n              n
   Search backward         ?              C-r
   Search forward          /              C-s
   Start of line           0              C-a
   Start selection         Space          C-Space
   Transpose chars                        C-t
```

#### Misc

```
d  detach
t  big clock
?  list shortcuts
:  prompt
```

#### Configurations Options

```
# Mouse support - set to on if you want to use the mouse
* setw -g mode-mouse off
* set -g mouse-select-pane off
* set -g mouse-resize-pane off
* set -g mouse-select-window off

# Set the default terminal mode to 256color mode
set -g default-terminal "screen-256color"

# enable activity alerts
setw -g monitor-activity on
set -g visual-activity on

# Center the window list
set -g status-justify centre

# Maximize and restore a pane
unbind Up bind Up new-window -d -n tmp \; swap-pane -s tmp.1 \; select-window -t tmp
unbind Down
bind Down last-window \; swap-pane -s tmp.1 \; kill-window -t tmp
```

#### Resources

- [tmux: Productive Mouse-Free Development](http://pragprog.com/book/bhtmux/tmux)
- [How to reorder windows](http://superuser.com/questions/343572/tmux-how-do-i-reorder-my-windows)



## Script

### JAVA Application start

```bash
nohup java -jar frameworkapi.jar --spring.config.local=E:/app/jg/application-test.yml --spring.profiles.active=test >/dev/null 2>&1 &
echo $!>pid
```

### JAVA Application stop
```bash 
kill `cat pid`
```

### 自动备份mongodb
[mongodb_back.sh](assets/files/mongodb_backup.sh)

```bash
#!/bin/bash
#backup MongoDB

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
tar -zcvf $TAR_DIR/$TAR_BAK $DATE
# 删除30天前的备份文件
find $TAR_DIR/ -mtime + $DAYS -delete

exit
```
```bash
# 设置定时任务
chmod +x ~/crontab/mongodb_backup.sh       # 增加执行权限
vi /etc/crontab
0 2 * * * root ~/crontab/mongodb_backup.sh   # 每天凌晨02:00以 root 身份运行备份数据库的脚本
```