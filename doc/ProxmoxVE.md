# Proxmox VE

network interface 

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
    x# SNAT 内网转外网，当内部虚拟器需要访问外网时走这条规则
	post-up iptables -t nat -A POSTROUTING -s '192.168.100.0/24' -o wlp0s20f3 -j MASQUERADE
	post-down iptables -t nat -D POSTROUTING -s '192.168.100.0/24' -o wlp0s20f3 -j MASQUERADE
```

内网端口转发

``` bash
iptables -t nat -A PREROUTING -p tcp --dport 8030 -j DNAT --to-destination 192.168.100.254:8030
iptables -t nat -A POSTROUTING -p tcp --sport 8030 -s 192.168.100.254 -j SNAT --to-source 192.168.8.199:8030
```