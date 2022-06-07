# SSH

```bash
vi /etc/ssh/sshd_config
service sshd restart
#or
service ssh restart

utmpdump /var/log/wtmp | cat   # 列出适用ssh访问的地址和时间
```

```bash
#sshd_config 文件

Port 22		#端口
PermitRootLogin no 	#root 登录禁止
```

## 反向tunnel

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

## ssh 证书登录

```bash
ssh-keygen -t rsa
# 通过ssh-copy-id copy
ssh-copy-id -i ./.ssh/id_rsa.pub root@xxx.xxx.xxx.xxx   # copy证书去远程主机

# windows
type $env:USERPROFILE\.ssh\id_rsa.pub | ssh root@192.168.157.129 "cat >> .ssh/authorized_keys"

# 通过scp拷贝
scp ~/.ssh/id_rsa.pub root@xxx.xxx.xxx.xxx:~
scp -P 26611 ./ssh/id_rsa.pub  jinyi@xxx.xxx.xxx.xx:/home/jinyi/id_rsa.pub
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

### 调试SSH

在服务器上运行 `/usr/sbin/sshd -d -p 2222` 开启调试模式
在客户端上使用 `ssh -vvT -p 2222 root@192.168.1.2`连接


### 配置证书后无法访问

```bash
chown root /root
chown root /root/.ssh
```



## scp

> OpenSSH 项目表示，他们认为 scp 协议已经过时，不灵活，而且不容易修复，然后他们继而推荐使用 sftp 或 rsync 来进行文件传输。

## rsync



## sftp

## sshfs

挂载远程目录到本地

## ssh-agent

ssh-agent是一种控制用来保存公钥身份验证所使用的私钥的程序，其实ssh-agent就是一个密钥管理器，运行ssh-agent以后，使用ssh-add将私钥交给ssh-agent保管，其他程序需要身份验证的时候可以将验证申请交给ssh-agent来完成整个认证过程。通过使用ssh-agent就可以很方便的在不的主机间进行漫游了，假如我们手头有三台server：host1、host2、host3且每台server上到保存了本机(owner)的公钥，因此我可以通过公钥认证登录到每台主机,但是这三台server之间并没有并没有保存彼此的公钥，而且我也不可能将自己的私钥存放到server上(不安全)，因此彼此之间没有公钥进行认证（可以密码认证，但是这样慢，经常输密码，烦且密码太多容易忘）。但是如果我们启用ssh-agent，问题就可以迎刃而解了

### 实战

```bash
eval `ssh-agent`  # 启动
ssh-add ~/.ssh/id_rsa  # add 密钥

# 全局修改 每一台服务器也要修改
echo "ForwardAgent yes" >> /etc/ssh/ssh_config

# 用户级修改
vim ~/.ssh/config
Host *
	ForwardAgent yes
　　
# 增加全局脚本 /etc/profile.d/ssh-agent.sh 
#!/bin/sh
if [ -f ~/.agent.env ]; then
      . ~/.agent.env >/dev/null
      if ! kill -0 $SSH_AGENT_PID >/dev/null 2>&1; then
              echo “Stale agent file found. Spawning new agent…”
              eval `ssh-agent |tee ~/.agent.env`
              ssh-add
      fi
else
      echo “Starting ssh-agent…”
      eval `ssh-agent |tee ~/.agent.env`
      ssh-add
fi
```

