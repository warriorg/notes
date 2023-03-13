# Linux服务器设置

## 设置交换区使用策略

```bash
# 默认60 设置为0表示最大限度使用物理内存，然后才是 swap空间
echo 0 > /proc/sys/vm/swappiness
```

## 设置最大打开文件数

* `cat /proc/sys/fs/file-max` 决定了整个系统能够打开的最大文件数量，建议file-max中的值设置为**内存大小以K为单位时的10%**
  * `cat /proc/sys/fs/file-nr` 显示当前系统打开的文件句柄状态、ulimit设置打开文件的句柄数、用户打开文件句柄数限制。

```bash
# /etc/sysctl.conf
fs.file-max = 6527711
```

```bash
# /etc/security/limits.conf
#
#Each line describes a limit for a user in the form:
#
#<domain>        <type>  <item>  <value>
*		soft	nofile   65535
*		hard	nofile   65535
```

