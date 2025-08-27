# Kubernetes 1.33 部署

| 系统名        | IP              | 备注 |
| ------------- | --------------- | ---- |
| k8s-master    | 192.168.100.200 |      |
| k8s-node-01   | 192.168.100.201 |      |
| k8s-node-02   | 192.168.100.202 |      |

## 前置检查

```bash
hostnamectl set-hostname k8s-master

# hosts
# /etc/hosts
192.168.100.200 k8s-master
192.168.100.201 k8s-node-01 
192.168.100.202 k8s-node-02

# off swap
sed -ri 's/^([^#].*swap.*)$/#\1/' /etc/fstab && grep swap /etc/fstab && swapoff -a && free -h

# 获取系统唯一ID，kubelet会使用此ID来识别节点
cat /sys/class/dmi/id/product_uuid

cat <<EOF | tee /etc/sysctl.d/k8s.conf
vm.swappiness = 0
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-ip6tables = 1
net.ipv4.ip_forward = 1
EOF

cat >> /etc/modules-load.d/neutron.conf <<EOF
overlay
br_netfilter
EOF

# 加载模块
modprobe overlay
modprobe br_netfilter
# 让配置生效
sysctl -p

# 查看配置
sysctl net.bridge.bridge-nf-call-iptables net.bridge.bridge-nf-call-ip6tables net.ipv4.ip_forward
```

## 安装容器

```bash
apt-get update
apt-get install -y containerd
mkdir -p /etc/containerd
containerd config default | tee /etc/containerd/config.toml

```

### 设置容器

```bash
# 修改 /etc/containerd/config.toml 文件，将 SystemdCgroup 设置为 true
# 找到 [plugins."io.containerd.grpc.v1.cri".containerd.runtimes.runc.options] 部分
# 并确保 SystemdCgroup = true

sed -i 's/SystemdCgroup = false/SystemdCgroup = true/g' /etc/containerd/config.toml
```

```bash
# 重启
systemctl restart containerd
```

## 安装 kubeadm、kubelet 和 kubectl

kubeadm：用来初始化集群的指令。
kubelet：在集群中的每个节点上用来启动 Pod 和容器等。
kubectl：用来与集群通信的命令行工具。

```bash
apt-get update
apt-get install -y apt-transport-https ca-certificates curl gpg

curl -fsSL https://pkgs.k8s.io/core:/stable:/v1.33/deb/Release.key | gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg

echo 'deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] https://pkgs.k8s.io/core:/stable:/v1.33/deb/ /' | tee /etc/apt/sources.list.d/kubernetes.list

apt-get update
apt-get install -y kubelet kubeadm kubectl
apt-mark hold kubelet kubeadm kubectl

```

### 初始化集群

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
  advertiseAddress: 192.168.100.200 # 修改为k8s-master的IP地址
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
kubernetesVersion: 1.33.0 
networking:
  dnsDomain: cluster.local
  podSubnet: 10.244.0.0/16
  serviceSubnet: 10.96.0.0/12
# 新增
controlPlaneEndpoint: "192.168.100.200:6443" # 修改为k8s-master的IP地址
scheduler: {}
```

## 安装网络插件

### 安装 CNI 插件

```bash
ARCH=$(uname -m)
  case $ARCH in
    armv7*) ARCH="arm";;
    aarch64) ARCH="arm64";;
    x86_64) ARCH="amd64";;
  esac
mkdir -p /opt/cni/bin
curl -O -L https://github.com/containernetworking/plugins/releases/download/v1.7.1/cni-plugins-linux-$ARCH-v1.7.1.tgz
tar -C /opt/cni/bin -xzf cni-plugins-linux-$ARCH-v1.7.1.tgz
```

### 部署 flannel 插件

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

## 初始化 K8S 集群

```bash
kubeadm init --config kubeadm.yaml

# 复制kubeadm init命令输出的kubeadm join命令，用于后续节点加入集群
# 例如：kubeadm join 192.168.100.200:6443 --token <token> --discovery-token-ca-cert-hash sha256:<hash>

export KUBECONFIG=/etc/kubernetes/admin.conf
# 为了方便后续操作，可以将KUBECONFIG添加到shell配置文件中
# echo "export KUBECONFIG=/etc/kubernetes/admin.conf" >> ~/.bashrc
# source ~/.bashrc

# 安装插件
kubectl apply -f kube-flannel.yml

# 加入集群 (此命令应从kubeadm init的输出中获取，以下为示例)
kubeadm join 192.168.100.200:6443 --token abcdef.0123456789abcdef \
        --discovery-token-ca-cert-hash sha256:c2714b9659fb192188db44520b146ca3f0b6ea741e45592fc4ed547823485357

kubectl get po -A -owide
```

### NODE清理重新加入

```bash
kubeadm reset      #初始化
rm -rf /etc/cni/net.d
systemctl daemon-reload      #重新加载
ip link set cni0 down && sudo ip link delete cni0
systemctl restart kubelet    #重启kubelet服务
systemctl restart containerd
iptables -F
```

## 部署 NFS

[NFS](../../doc/linux/nfs.md)

## 问题解决

### 检查配置

```bash
kubectl -n kube-system get cm kubeadm-config -o yaml
```

使用测试容器帮助排查问题

```bash
 kubectl run -i --tty --rm debug --image=busybox --restart=Never -- sh
```

### 处理 CoreDNS 无法启动的问题

```bash
➜  ~ kubectl get po -A -owide
NAMESPACE      NAME                                 READY   STATUS              RESTARTS   AGE   IP                NODE          NOMINATED NODE   READINESS GATES
kube-flannel   kube-flannel-ds-l7s7h                1/1     Running             0          47m   192.168.100.200   k8s-master    <none>           <none>
kube-flannel   kube-flannel-ds-mk7wr                1/1     Running             0          21m   192.168.100.201   k8s-node-01   <none>           <none>
kube-flannel   kube-flannel-ds-px85l                1/1     Running             0          15m   192.168.100.202   k8s-node-02   <none>           <none>
kube-system    coredns-674b8bbfcf-dr7x7             0/1     ContainerCreating   0          10m   <none>            k8s-node-01   <none>           <none>
kube-system    coredns-674b8bbfcf-xztxw             0/1     ContainerCreating   0          10m   <none>            k8s-node-02   <none>           <none>
kube-system    etcd-k8s-master                      1/1     Running             2          48m   192.168.100.200   k8s-master    <none>           <none>
kube-system    kube-apiserver-k8s-master            1/1     Running             2          48m   192.168.100.200   k8s-master    <none>           <none>
kube-system    kube-controller-manager-k8s-master   1/1     Running             0          48m   192.168.100.200   k8s-master    <none>           <none>
kube-system    kube-proxy-8vmjd                     1/1     Running             0          21m   192.168.100.201   k8s-node-01   <none>           <none>
kube-system    kube-proxy-dh94l                     1/1     Running             0          48m   192.168.100.200   k8s-master    <none>           <none>
kube-system    kube-proxy-knqjr                     1/1     Running             0          15m   192.168.100.202   k8s-node-02   <none>           <none>
kube-system    kube-scheduler-k8s-master            1/1     Running             2          48m   192.168.100.200   k8s-master    <none>           <none>
```

```bash
➜  ~ kubectl describe pod coredns-674b8bbfcf-dr7x7 -n kube-system

Priority Class Name:  system-cluster-critical
Service Account:      coredns
Node:                 k8s-node-01/192.168.100.201
Start Time:           Sat, 16 Aug 2025 06:41:06 +0800
Labels:               k8s-app=kube-dns
                      pod-template-hash=674b8bbfcf
Annotations:          <none>
Status:               Pending
IP:
IPs:                  <none>
Controlled By:        ReplicaSet/coredns-674b8bbfcf
Containers:
  coredns:
    Container ID:
    Image:         registry.k8s.io/coredns/coredns:v1.12.0
    Image ID:
    Ports:         53/UDP, 53/TCP, 9153/TCP
    Host Ports:    0/UDP, 0/TCP, 0/TCP
    Args:
      -conf
      /etc/coredns/Corefile
    State:          Waiting
      Reason:       ContainerCreating
    Ready:          False
    Restart Count:  0
    Limits:
      memory:  170Mi
    Requests:
      cpu:        100m
      memory:     70Mi
    Liveness:     http-get http://:8080/health delay=60s timeout=5s period=10s #success=1 #failure=5
    Readiness:    http-get http://:8181/ready delay=0s timeout=1s period=10s #success=1 #failure=3
    Environment:  <none>
    Mounts:
      /etc/coredns from config-volume (ro)
      /var/run/secrets/kubernetes.io/serviceaccount from kube-api-access-tq2kn (ro)
Conditions:
  Type                        Status
  PodReadyToStartContainers   False
  Initialized                 True
  Ready                       False
  ContainersReady             False
  PodScheduled                True
Volumes:
  config-volume:
    Type:      ConfigMap (a volume populated by a ConfigMap)
    Name:      coredns
    Optional:  false
  kube-api-access-tq2kn:
    Type:                    Projected (a volume that contains injected data from multiple sources)
    TokenExpirationSeconds:  3607
    ConfigMapName:           kube-root-ca.crt
    Optional:                false
    DownwardAPI:             true
QoS Class:                   Burstable
Node-Selectors:              kubernetes.io/os=linux
Tolerations:                 CriticalAddonsOnly op=Exists
                             node-role.kubernetes.io/control-plane:NoSchedule
                             node.kubernetes.io/not-ready:NoExecute op=Exists for 300s
                             node.kubernetes.io/unreachable:NoExecute op=Exists for 300s
Events:
  Type     Reason                  Age                    From               Message
  ----     ------                  ----                   ----               -------
  Normal   Scheduled               3m29s                  default-scheduler  Successfully assigned kube-system/coredns-674b8bbfcf-dr7x7 to k8s-node-01
  Warning  FailedCreatePodSandBox  3m28s                  kubelet            Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "23dbdf98e436556eb9e80633a5f7ba79109196101692392df11b075902c5b4e8": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Warning  FailedCreatePodSandBox  3m28s                  kubelet            Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "cf2696da5c73225190c324e71edc8d61f3049ca130fbd167845268a97e7c80c9": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Warning  FailedCreatePodSandBox  3m27s                  kubelet            Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "359bea505e49b142a41342dc0aeac6e383f0111635815675a6a81109e983bd21": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Warning  FailedCreatePodSandBox  3m26s                  kubelet            Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "55b80b31878538766655d5fefef61b1c8b37f629896c2c59d23e50d1ded5f32a": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Warning  FailedCreatePodSandBox  3m25s                  kubelet            Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "655a56b483d809564bdd37c1b0e85d97610d34113e9f34869ade600f7d351204": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Warning  FailedCreatePodSandBox  3m24s                  kubelet            Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "a86e0db31c99e1aa80e924e6f6210b4a75864d688ccd51b9d388d8de9c485d41": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Warning  FailedCreatePodSandBox  3m23s                  kubelet            Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "9b66de3851998a5a7e52a5037193ac74ac09a5f6d959091a40e008d2ad160a73": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Warning  FailedCreatePodSandBox  3m22s                  kubelet            Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "b180b37b3361005e89e6da9d2a3c3a693ab9359d7361e7aa5c48346b9eb842fe": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Warning  FailedCreatePodSandBox  3m21s                  kubelet            Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "6eb688083f6edb21aae38e6745a20b624a3d4d8cc94d4d4179d8552b6a60018e": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Warning  FailedCreatePodSandBox  3m5s (x16 over 3m20s)  kubelet            (combined from similar events): Failed to create pod sandbox: rpc error: code = Unknown desc = failed to setup network for sandbox "d08560eec16726b26252fbdb213552b5d90643e82ffb84ac62235f95efc12299": plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
  Normal   SandboxChanged          3m4s (x25 over 3m28s)  kubelet            Pod sandbox changed, it will be killed and re-created.
```

```bash
# fix plugin type="flannel" failed (add): failed to find plugin "flannel" in path [/usr/lib/cni]
ln -s /opt/cni/bin/ /usr/lib/cni
kubectl delete pods coredns-674b8bbfcf-dr7x7 -n kube-system
```

