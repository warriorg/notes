# HomeLab


```bash
# doris
iptables -t nat -A PREROUTING -p tcp --dport 8030 -j DNAT --to-destination 192.168.100.254:8030
iptables -t nat -A POSTROUTING -p tcp --sport 8030 -s 192.168.100.254 -j SNAT --to-source 192.168.8.199:8030

# singlenode
iptables -t nat -A PREROUTING -p tcp --dport 65535 -j DNAT --to-destination 192.168.100.254:22
iptables -t nat -A POSTROUTING -p tcp --sport 65535 -s 192.168.100.254 -j SNAT --to-source 192.168.8.199:8030

# k8s
iptables -t nat -A PREROUTING -p tcp --dport 6443 -j DNAT --to-destination 192.168.100.201:6443
iptables -t nat -A POSTROUTING -p tcp --sport 6443 -s 192.168.100.201 -j SNAT --to-source 192.168.8.199:6443

# 开发 k8s 默认 NodePort 的端口
iptables -t nat -A PREROUTING -p tcp --dport 30000:32767 -j DNAT --to-destination 192.168.100.201
iptables -t nat -A POSTROUTING -p tcp --sport 30000:32767 -s 192.168.100.201 -j SNAT --to-source 192.168.8.199

iptables -t nat -nvL

# 保存nat的规则到指定文件
iptables-save -t nat -f  /etc/iptables/rules.nat
# 恢复配置
iptables-restore /etc/iptables/rules.nat
```
