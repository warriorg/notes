# Kubernetes 1.30.1 部署

| 系统名        | IP              | 备注 |
| ------------- | --------------- | ---- |
| k8s-master    | 192.168.100.201 |      |
| k8s-node-02   | 192.168.100.202 |      |
| k8s-node-03   | 192.168.100.203 |      |

前置检查

```bash
# 关闭 swap
sed -ri 's/^([^#].*swap.*)$/#\1/' /etc/fstab && grep swap /etc/fstab && swapoff -a && free -h

cat /sys/class/dmi/id/product_uuid
```



安装容器

```bash
cat <<EOF | sudo tee /etc/sysctl.d/k8s.conf
vm.swappiness = 0
net.bridge.bridge-nf-call-iptables = 1
net.ipv4.ip_forward = 1
net.bridge.bridge-nf-call-ip6tables = 1
EOF

cat >> /etc/modules-load.d/neutron.conf <<EOF
br_netfilter
EOF

#加载模块
sudo modprobe br_netfilter
#让配置生效
sudo sysctl -p
```


安装docker

```bash
apt-get update
apt-get install ca-certificates curl
install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc
chmod a+r /etc/apt/keyrings/docker.asc

echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/debian \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
tee /etc/apt/sources.list.d/docker.list > /dev/null

apt-get update
apt-get install docker-ce docker-ce-cli containerd.io

cat > /etc/docker/daemon.json <<EOF
{
"registry-mirrors": [
],
 "exec-opts": ["native.cgroupdriver=systemd"],
 "data-root": "/var/lib/docker",
 "log-driver": "json-file",
 "log-opts": {
	 "max-size": "20m",
	 "max-file": "5"
	}
}
EOF

systemctl restart docker.service
systemctl enable docker.service
docker info
```

安装最新版本的kubeadm、kubelet 和 kubectl

```bash
sudo apt-get update
# apt-transport-https may be a dummy package; if so, you can skip that package
sudo apt-get install -y apt-transport-https ca-certificates curl gpg
# If the directory `/etc/apt/keyrings` does not exist, it should be created before the curl command, read the note below.
# sudo mkdir -p -m 755 /etc/apt/keyrings
curl -fsSL https://pkgs.k8s.io/core:/stable:/v1.30/deb/Release.key | sudo gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg
# This overwrites any existing configuration in /etc/apt/sources.list.d/kubernetes.list
echo 'deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] https://pkgs.k8s.io/core:/stable:/v1.30/deb/ /' | sudo tee /etc/apt/sources.list.d/kubernetes.list

sudo apt-get update
sudo apt-get install -y kubelet kubeadm kubectl
sudo apt-mark hold kubelet kubeadm kubectl
sudo systemctl enable --now kubelet
```

容器设置
```bash
containerd config default > /etc/containerd/config.toml
```

设置容器
```bash
[plugins."io.containerd.grpc.v1.cri".containerd.runtimes.runc]
  ...
  [plugins."io.containerd.grpc.v1.cri".containerd.runtimes.runc.options]
    SystemdCgroup = true
```

```bash
# 重启
sudo systemctl restart containerd
```

> SystemdCgroup 设置非常重要，否则容器会不停重启

初始化集群

```bash
# 生成初始化配置文件
kubeadm config print init-defaults > kubeadm.yaml
```

修改 `kubeadm.yaml`

```yaml
apiVersion: kubeadm.k8s.io/v1beta3
bootstrapTokens:
- groups:
  - system:bootstrappers:kubeadm:default-node-token
  token: abcdef.0123456789abcdef
  ttl: 24h0m0s
  usages:
  - signing
  - authentication
kind: InitConfiguration
localAPIEndpoint:
  advertiseAddress: 192.168.100.201
  bindPort: 6443
nodeRegistration:
  criSocket: unix:///var/run/containerd/containerd.sock
  imagePullPolicy: IfNotPresent
  # 修改为hostname
  name: k8s-master
  taints: null
---
apiServer:
  timeoutForControlPlane: 4m0s
apiVersion: kubeadm.k8s.io/v1beta3
certificatesDir: /etc/kubernetes/pki
clusterName: kubernetes
controllerManager: {}
dns: {}
etcd:
  local:
    dataDir: /var/lib/etcd
imageRepository: registry.k8s.io
kind: ClusterConfiguration
kubernetesVersion: 1.30.0
networking:
  dnsDomain: cluster.local
  podSubnet: 10.244.0.0/16
  serviceSubnet: 10.96.0.0/12
# 新增
controlPlaneEndpoint: "192.168.100.201:6443"
scheduler: {}
```

安装网络插件

https://github.com/flannel-io/flannel?tab=readme-ov-file#deploying-flannel-manually

```bash
wget https://github.com/flannel-io/flannel/releases/latest/download/kube-flannel.yml
```

修改 namespace and pod cidr段为上面init时的cidr段：
```
net-conf.json: |
  {
    "Network": "10.244.0.0/16",
    "EnableNFTables": false,
    "Backend": {
      "Type": "vxlan"
    }
  }
```



```bash
mkdir -p /opt/cni/bin
curl -O -L https://github.com/containernetworking/plugins/releases/download/v1.2.0/cni-plugins-linux-amd64-v1.2.0.tgz
tar -C /opt/cni/bin -xzf cni-plugins-linux-amd64-v1.2.0.tgz
```

```bash
kubeadm init --config kubeadm.yaml

export KUBECONFIG=/etc/kubernetes/admin.conf
# 安装插件
kubectl apply -f kube-flannel.yml

# 加入集群
kubeadm join 192.168.100.201:6443 --token abcdef.0123456789abcdef \
	--discovery-token-ca-cert-hash sha256:171f820b8ad1cb4867d379fd8aa662138100817db272ca4ced7ebab4d578e42c

kubectl get po -A -owide
```


node 清理重新加入
```bash
kubeadm reset      #初始化
rm -rf /etc/cni/net.d
systemctl daemon-reload      #重新加载
ip link set cni0 down && sudo ip link delete cni0
systemctl restart kubelet    #重启kubelet服务
systemctl restart containerd
iptables -F
```


问题解决

```bash
# 检查配置
kubectl -n kube-system get cm kubeadm-config -o yaml
```

* 问题排查

```bash
systemctl status kubelet
journalctl -xeu kubelet
```

* container runtime is not running: output: time="2024-07-12T18:41:03+08:00" level=fatal msg="validate service connection: validate CRI v1 runtime API for endpoint \"unix:///run/containerd/containerd.sock\": rpc error: code = Unimplemented desc = unknown service runtime.v1.RuntimeService"


Solution

```bash
rm /etc/containerd/config.toml
systemctl restart containerd
```

* Failed to create pod sandbox: rpc error: code = Unknown desc = failed to get sandbox image "k8s.gcr.io/pause:3.8": failed to pull image "k8s.gcr.io/pause:3.2": failed to pull and unpack image "k8s.gcr.io/pause:3.8": failed to resolve reference "k8s.gcr.io/pause:3.8": failed to do request: Head

```bash
ctr -n k8s.io i tag registry.aliyuncs.com/google_containers/pause:3.8 registry.k8s.io/pause:3.8
```

* failed to verify certificate: x509: certificate is valid for 10.96.0.1, 192.168.100.201, not 10.10.10.199

`/etc/kubernetes/pki`

```bash
# 查看证书内容
openssl x509 -noout -text -in apiserver.crt
# 生成密钥对
openssl genrsa -out apiserver.key 2048	
openssl req -new -key apiserver.key -subj "/CN=kube-apiserver," -out apiserver.csr

#  新增 apiserver.ext文件，包含所有的地址列表，ip:xxxx即为要包含的新节点的ip， 内容如下：
subjectAltName = DNS:wudang,DNS:kubernetes,DNS:kubernetes.default,DNS:kubernetes.default.svc, DNS:kubernetes.default.svc.cluster.local, IP:10.96.0.1, IP:192.168.100.201, IP:192.168.8.1, IP:10.10.10.199

# 使用ca根证书生成新crt
openssl x509 -req -in apiserver.csr -CA /etc/kubernetes/pki/ca.crt -CAkey /etc/kubernetes/pki/ca.key -CAcreateserial -out apiserver.crt -days 3650 -extfile apiserver.ext

# 重启 k8s apiserver
kubectl delete pod kube-apiserver-k8s-master -n kube-system
```