####用户设置
adduser： 会自动为创建的用户指定主目录、系统shell版本，会在创建时输入用户密码。

useradd：需要使用参数选项指定上述基本设置，如果不使用任何参数，则创建的用户无密码、无主目录、没有指定shell版本。

userdel 删除用户

```bash
adduser apple
useradd -g group apple #新建用户添加到组
passwd apple
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

ssh 证书登录

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
