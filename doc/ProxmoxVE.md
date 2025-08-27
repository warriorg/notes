# Proxmox VE

连接有限，进行安装系统，最好悠闲，否则，需要手动去下载文件，挂载磁盘，离线安装
`wpasupplicant` 和 `wireless-tools`





/etc/network/interface

密码获取 https://www.wireshark.org/tools/wpa-psk.html
或者
```bash
wpa_passphrase "wifi名称" "wifi密码
```

```bash
auto lo
iface lo inet loopback

auto wlp0s20f3
iface wlp0s20f3 inet dhcp
	#wpa-ssid ASUS-516-5G
	#wpa-psk 8494304e9b262a2d37a8307947a1e42e772fb79982629eee298c36d594e24da6
	wpa-ssid GL-MT3000-516
	wpa-psk f710e4aa6b93d11805348ce5bb4dd5328587fab47d652bf880be0d7edc4d2885

iface enp0s31f6 inet manual

auto vmbr0
iface vmbr0 inet static
	address 192.168.100.1/24
	bridge-ports none
	bridge-stp off
	bridge-fd 0
	bridge-vlan-aware yes
	bridge-vids 2-4094
	# 打开IP转发
	post-up   echo 1 > /proc/sys/net/ipv4/ip_forward
	# 代理 ARP 允许一个主机在一个子网上代表另一个主机响应 ARP 请求，ARP 是一种用于将 IP 地址解析为 MAC 地址的协议
 	post-up echo 1 > /proc/sys/net/ipv4/conf/eno1/proxy_arp

	# 转发IPv4流量到虚拟机，使虚拟机与外网联通。
    # SNAT 内网转外网，当内部虚拟器需要访问外网时走这条规则
	post-up iptables -t nat -A POSTROUTING -s '192.168.100.0/24' -o wlp0s20f3 -j MASQUERADE
	post-down iptables -t nat -D POSTROUTING -s '192.168.100.0/24' -o wlp0s20f3 -j MASQUERADE
```

内网端口转发

``` bash
iptables -t nat -A PREROUTING -p tcp --dport 8030 -j DNAT --to-destination 192.168.100.254:8030
iptables -t nat -A POSTROUTING -p tcp --sport 8030 -s 192.168.100.254 -j SNAT --to-source 192.168.8.199:8030


iptables -t nat -nvL

# 保存nat的规则到指定文件
iptables-save -t nat -f  /etc/iptables/rules.nat
# 恢复配置
iptables-restore /etc/iptables/rules.nat
```


## clash

安装 clash， 开启 tun 代理模式内部虚拟机为透明代理上网