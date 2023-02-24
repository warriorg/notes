# Linux服务器设置



```bash
# 默认60 设置为0表示最大限度使用物理内存，然后才是 swap空间
echo 0 > /proc/sys/vm/swappiness
```

