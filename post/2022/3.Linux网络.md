---
date: 2022-09-01
---
# Linux 网络监控

## 流量监控

### cheatsheets

```bash
ip -s -h link   # 查看各网卡的总流量
dstat -tn    # 统计网络总流量
ss -nt   # 查看tcp连接
sudo iftop -nN -i eth0  # 实时查看eth0网卡的各个连接和网速
sudo nethogs -d 2 eth0  # 每2秒刷新流经eth0网卡的进程流量信息
```