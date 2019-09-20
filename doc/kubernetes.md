## Install

### Install with Homebrew on macOS

```bash
brew install kubernetes-cli
kubectl version
```

### Minikube

```bash
brew cask install minikube
```



### kubeadm



### docker desktop 

#### 启用k8s 

![macos_docker_k8s_install](./assets/images/macos_docker_k8s_install.png)

安装完毕后，如果勾选了 Show system containers 选项，那么使用如下的 Docker 命令，能看到自动安装的 Kubernetes 相关容器

```bash
docker container ls --format "table{{.Names}}\t{{.Image }}\t{{.Command}}"
```

在安装过程中，Docker 为我们安装了 kubectl 控制命令

```bash
kubectl get namespaces
# kubectl get posts --namespace kube-system
```

#### Install Kubernetes Dashboard

```bash 
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v1.10.1/src/deploy/recommended/kubernetes-dashboard.yaml

# 查看部署的容器和服务
kubectl get deployments --namespace kube-system
kubectl get services --namespace kube-system

# 使用 kubectl 提供的 Proxy 服务来访问Dashboard
kubectl proxy
# 地址
# http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/

# 若果出错，可以尝试编辑 kubernetes-dashboard 服务
# https://github.com/kubernetes/dashboard/wiki/Accessing-Dashboard---1.7.X-and-above
kubectl -n kube-system edit service kubernetes-dashboard
```

#### [Creating sample user](https://github.com/kubernetes/dashboard/wiki/Creating-sample-user)

使用token的方式访问

```bash
kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | grep admin-user | awk '{print $1}')
```

### [将 Docker Compose 文件转换为 Kubernetes 资源](https://v1-12.docs.kubernetes.io/zh/docs/tasks/configure-pod-container/translate-compose-kubernetes/)

#### macos

```bash
brew install kompose
# 或者下面命令
curl -L https://github.com/kubernetes/kompose/releases/download/v1.16.0/kompose-darwin-amd64 -o kompose

# 部署到 Kubernetes
kompose up
# 从 Kubernetes 删除部署的实例
kompose down
# 要将 docker-compose.yml 转换为 kubectl 可用的文件
kompose convert 
# kubectl 进行创建
kubectl create -f frontend-service.yaml
# 查看部署的服务的详情
kubectl get deployment,svc,pods,pvc
```





### Install Kubeadm 

#### Ubuntu

添加kubeadm的源

```bash
$ curl https://mirrors.aliyun.com/kubernetes/apt/doc/apt-key.gpg | apt-key add -
$ cat <<EOF >/etc/apt/sources.list.d/kubernetes.list
deb https://mirrors.aliyun.com/kubernetes/apt/ kubernetes-xenial main
EOF

$ apt-get update
$ apt-get install -y docker.io kubeadm   # 安装 docker, kubeadm
# 在安装kubeadm过程中,kubeadm、kubelet、kubectl、kubernetes-cni 一并安装好
```

##### 部署Kubernetes Master节点

编写一个yaml配置文件

```yaml
apiVersion: kubeadm.k8s.io/v1beta1
kind: InitConfiguration
controllerManager:
  horizontal-pod-autoscaler-use-rest-clients: "true"
  horizontal-pod-autoscaler-sync-period: "10s"
  node-monitor-grace-period: "10s"
apiServer:
  runtime-config: "api/all=true"
kubernetesVersion: "v1.13.4"
```

```bash
$ kubeadm init --config kubeadm.yaml
```



## 概念

### Kubernetes 对象

在 Kubernetes 系统中，*Kubernetes 对象* 是持久化的实体。Kubernetes 使用这些实体去表示整个集群的状态。特别地，它们描述了如下信息：

- 哪些容器化应用在运行（以及在哪个 Node 上）
- 可以被应用使用的资源
- 关于应用运行时表现的策略，比如重启策略、升级策略，以及容错策略

#### 对象规约（Spec）与状态（Status）

每个 Kubernetes 对象包含两个嵌套的对象字段，它们负责管理对象的配置：对象 *spec* 和 对象 *status* 。 *spec* 是必需的，它描述了对象的 *期望状态（Desired State）* —— 希望对象所具有的特征。 *status* 描述了对象的 *实际状态（Actual State）* ，它是由 Kubernetes 系统提供和更新的。

#### 必需字段

在想要创建的 Kubernetes 对象对应的 `.yaml` 文件中，需要配置如下的字段：

- `apiVersion` - 创建该对象所使用的 Kubernetes API 的版本
- `kind` - 想要创建的对象的类型
- `metadata` - 帮助识别对象唯一性的数据，包括一个 `name` 字符串、UID 和可选的 `namespace`

