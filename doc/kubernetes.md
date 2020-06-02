

# 介绍

## 集群架构

* 主节点 承载着Kubernetes控制和管理整个集群系统的控制面板
* 工作节点 运行用户实际部署的应用

![image-20200512185216239](assets/images/image-20200512185216239.png)

### 控制面板

控制面板用于控制集群并使它工作。它包含多个组件，组件可以运行在单个主节点上或者通过副本分别部署在多个主节点以确保高可用性。这些组件是：

* KubernetesAPI服务器，你和其他控制面板组件都要和它通信

* Scheculer，它调度你的应用（为应用的每个可部署组件分配一个工作节点）

* ControllerManager，它执行集群级别的功能，如复制组件、持续跟踪工作节点、处理节点失败等

* etcd，一个可靠的分布式数据存储，它能持久化存储集群配置控制面板的组件持有并控制集群状态，但是它们不运行你的应用程序。这是由工作节点完成的。

### 工作节点

工作节点是运行容器化应用的机器。运行、监控和管理应用服务的任务是由以下组件完成的：

* Docker、rtk或其他的容器类型
* Kubelet，它与API服务器通信，并管理它所在节点的容器
* Kubernetes Service Proxy（kubeproxy），它负责组件之间的负载均衡网络流量


# 基本理论

## 虚拟化

## OpenStack & KVM

### OpenStack

OpenStack是一个云操作系统，通过数据中心可控制大型的计算、存储、网络等资源池。所有的管理通过前端界面管理员就可以完成，同样也可以通过web接口让最终用户部署资源。

### KVM

基于内核的虚拟机 Kernel-based Virtual Machine（KVM）是一种内建于 Linux® 中的开源虚拟化技术。具体而言，KVM 可帮助您将 Linux 转变为虚拟机监控程序，使主机计算机能够运行多个隔离的虚拟环境，即虚拟客户机或虚拟机（VM）。



## docker

[docker](./deocker.md)



## 容器编排




# 开始使用Kubernetes 和 Docker

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

[github](https://github.com/kubernetes/dashboard)

```bash 
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-rc2/aio/deploy/recommended.yaml

# 查看部署的容器和服务
kubectl get deployments --namespace kubernetes-dashboard
kubectl get services --namespace kubernetes-dashboard

# 使用 kubectl 提供的 Proxy 服务来访问Dashboard
kubectl proxy
# 地址
# http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/
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



### 部署容器存储插件

#### Rook

##### 部署 Rook Operator

部署rook, [github](https://github.com/rook/rook)地址。

```bash 
cd cluster/examples/kubernetes/ceph
kubectl create -f common.yaml
kubectl create -f operator.yaml

## verify the rook-ceph-operator is in the `Running` state before proceeding
kubectl -n rook-ceph get pod
```

##### 创建 Rook Ceph 集群

```yaml
# cluster-test.yaml
apiVersion: ceph.rook.io/v1
kind: CephCluster
metadata:
  name: rook-ceph
  namespace: rook-ceph
spec:
  cephVersion:
    image: ceph/ceph:v14.2.4-20190917
    allowUnsupported: false
  dataDirHostPath: /Users/warrior/code/k8s/volume
  mon:
    count: 1
    allowMultiplePerNode: false
  dashboard:
    enabled: true
    ssl: true
  monitoring:
    enabled: false  # requires Prometheus to be pre-installed
    rulesNamespace: rook-ceph
  network:
    hostNetwork: false
```

##### Ceph Dashboard

```yaml
apiVersion: v1
kind: Service
metadata:
  name: rook-ceph-mgr-dashboard-external-http
  namespace: rook-ceph
  labels:
    app: rook-ceph-mgr
    rook_cluster: rook-ceph
spec:
  ports:
  - name: dashboard
    port: 7000
    protocol: TCP
    targetPort: 7000
  selector:
    app: rook-ceph-mgr
    rook_cluster: rook-ceph
  sessionAffinity: None
  type: NodePort
```

查看创建的dashboard这个 Service 服务

```bash
kubectl get service -n rook-ceph
```

![rook-ceph-mgr-dashboard](./assets/images/rook-ceph-mgr-dashboard.jpg)

访问地址`http://localhost:31695`, Rook 创建了一个默认的用户 admin，并在运行 Rook 的命名空间中生成了一个名为 `rook-ceph-dashboard-admin-password` 的 Secret，运行以下命令获取密码。

```bash
kubectl -n rook-ceph get secret rook-ceph-dashboard-password -o jsonpath="{['data']['password']}" | base64 --decode && echo
```





### 创建、运行及共享容器镜像

#### 运行一个hello world容器

##### 背后的原理

![image-20200512190107940](assets/images/image-20200512190107940.png)

#### 为镜像创建Dockerfile

```dockerfile
FROM node:7										# 定义镜像的起始内容
ADD app.js /app.js						# 把 app.js 从本地文件夹加到镜像的根目录。保持文件名
ENTRYPOINT ["node", "app.js"] # 运行node命令
```

#### 构建容器镜像

![image-20200512190647676](assets/images/image-20200512190647676.png)

##### 镜像的构建过程

构建过程不是由Docker客户端进行的，而是将整个目录的文件上传到Docker守护进程并在那里进行的。Docker客户端和守护进程不要求在同一台机器上。

> 不要在构建目录中包含任何不需要的文件，这样会减慢构建的速度——尤其当Docker守护进程运行在一个远端机器的时候。

##### 镜像分层

![image-20200512190920937](assets/images/image-20200512190920937.png)





# 概念

## Kubernetes 对象

### 简介

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



## Pod

*Pod* 是 Kubernetes 应用程序的基本执行单元，即它是 Kubernetes 对象模型中创建或部署的最小和最简单的单元。Pod 表示在集群上运行的进程。






### Service

### Volume

### Namespace

## Controller

### Deployment

为 Pods 和ReplicaSet 提供声明式更新

### DaemonSet

### StatefulSet

### ReplicaSet

### Job



# 实战

### 部署第一个容器应用

**nginx-deployment.yaml**

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 2
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.17.0
        ports:
        - containerPort: 80
        volumeMounts:
        - mountPath: "/usr/share/nginx/html"
          name: nginx-vol
      volumes:
      - name: nginx-vol 
        hostPath: 
          path: /Users/warrior/www/bom
```

```bash
kubectl apply -f nginx-deployment.yaml  	# 部署容器，或者修改yaml文件后更新容器
kubectl get pods -l app=nginx			# 检查容器运行的状态
kubectl describe pod nginx-deployment-74dc4b47f6-7886h  # 查看容器的细节
kubectl exec -it nginx-deployment-74dc4b47f6-7886h -- /bin/bash  # 进入容器
kubectl delete -f nginx-deployment.yaml   # 删除容器
```





### 

## kubectl 命令

### 所有命令继承的选项

```bash
  --alsologtostderr[=false]: 同时输出日志到标准错误控制台和文件。
  --certificate-authority="": 用以进行认证授权的.cert文件路径。
  --client-certificate="": TLS使用的客户端证书路径。
  --client-key="": TLS使用的客户端密钥路径。
  --cluster="": 指定使用的kubeconfig配置文件中的集群名。
  --context="": 指定使用的kubeconfig配置文件中的环境名。
  --insecure-skip-tls-verify[=false]: 如果为true，将不会检查服务器凭证的有效性，这会导致你的HTTPS链接变得不安全。
  --kubeconfig="": 命令行请求使用的配置文件路径。
  --log-backtrace-at=:0: 当日志长度超过定义的行数时，忽略堆栈信息。
  --log-dir="": 如果不为空，将日志文件写入此目录。
  --log-flush-frequency=5s: 刷新日志的最大时间间隔。
  --logtostderr[=true]: 输出日志到标准错误控制台，不输出到文件。
  --match-server-version[=false]: 要求服务端和客户端版本匹配。
  --namespace="": 如果不为空，命令将使用此namespace。
  --password="": API Server进行简单认证使用的密码。
  -s, --server="": Kubernetes API Server的地址和端口号。
  --stderrthreshold=2: 高于此级别的日志将被输出到错误控制台。
  --token="": 认证到API Server使用的令牌。
  --user="": 指定使用的kubeconfig配置文件中的用户名。
  --username="": API Server进行简单认证使用的用户名。
  --v=0: 指定输出日志的chr级别。
  --vmodule=: 指定输出日志的模块，格式如下：pattern=N，使用逗号分隔。
```




### apply

通过文件名或控制台输入，对资源进行配置。 如果资源不存在，将会新建一个。

可以使用 JSON 或者 YAML 格式。

```bash
kubectl apply -f FILENAME
```

#### 示例

```bash
# 将pod.json中的配置应用到pod
kubectl apply -f ./pod.json

# 将控制台输入的JSON配置应用到Pod
cat pod.json | kubectl apply -f -
```

#### 选项

```bash
 -f, --filename=[]: 包含配置信息的文件名，目录名或者URL。
     --include-extended-apis[=true]: If true, include definitions of new APIs via calls to the API server. [default true]
  -o, --output="": 输出模式。"-o name"为快捷输出(资源/name).
      --record[=false]: 在资源注释中记录当前 kubectl 命令。
  -R, --recursive[=false]: Process the directory used in -f, --filename recursively. Useful when you want to manage related manifests organized within the same directory.
      --schema-cache-dir="~/.kube/schema": 非空则将API schema缓存为指定文件，默认缓存到'$HOME/.kube/schema'
      --validate[=true]: 如果为true，在发送到服务端前先使用schema来验证输入。
```





### run

创建并运行一个指定的可复制的镜像。 创建一个`deployment`或者`job`来管理创建的容器。

```bash
kubectl run NAME --image=image [--env="key=value"] [--port=port] [--replicas=replicas] [--dry-run=bool] [--overrides=inline-json] [--command] -- [COMMAND] [args...]
```

#### 示例

```bash
# 启动一个 Nginx 实例。
kubectl run nginx --image=nginx

# 启动一个 hazelcast 单个实例，并开放容器的5701端口。
kubectl run hazelcast --image=hazelcast --port=5701

# 运行一个 hazelcast 单个实例，并设置容器的环境变量"DNS_DOMAIN=cluster" and "POD_NAMESPACE=default"。
kubectl run hazelcast --image=hazelcast --env="DNS_DOMAIN=cluster" --env="POD_NAMESPACE=default"

# 启动一个 replicated 实例去复制 nginx。
kubectl run nginx --image=nginx --replicas=5

# 试运行。不创建他们的情况下，打印出所有相关的 API 对象。
kubectl run nginx --image=nginx --dry-run

# 用可解析的 JSON 来覆盖加载 `deployment` 的 `spec`，来运行一个 nginx 单个实例。
kubectl run nginx --image=nginx --overrides='{ "apiVersion": "v1", "spec": { ... } }'

# 运行一个在前台运行的 busybox 单个实例，如果退出不会重启。
kubectl run -i --tty busybox --image=busybox --restart=Never

# 使用默认命令来启动 nginx 容器，并且传递自定义参数(arg1 .. argN)给 nginx。
kubectl run nginx --image=nginx -- <arg1> <arg2> ... <argN>

# 使用不同命令或者自定义参数来启动 nginx 容器。
kubectl run nginx --image=nginx --command -- <cmd> <arg1> ... <argN>

# 启动 perl 容器来计算 bpi(2000) 并打印出结果。
kubectl run pi --image=perl --restart=OnFailure -- perl -Mbignum=bpi -wle 'print bpi(2000)'
```

#### 选项

```bash
      --attach[=false]: 如果为true, 那么等 pod 开始运行之后，链接到这个 pod 和运行 'kubectl attach ...'一样。默认是 false，除非设置了 '-i/--interactive' 默认才会是 true。
      --command[=false]: 如果为 true 并且有其他参数，那么在容器中运行这个'command'，而不是默认的'args'。
      --dry-run[=false]: 如果为 true，则仅仅打印这个对象，而不会执行命令。
      --env=[]: 设置容器的环境变量。
      --expose[=false]: 如果为 true， 会为这个运行的容器创建一个公开的 service。
      --generator="": The name of the API generator to use.  Default is 'deployment/v1beta1' if --restart=Always, otherwise the default is 'job/v1'.  This will happen only for cluster version at least 1.2, for olders we will fallback to 'run/v1' for --restart=Always, 'run-pod/v1' for others.
      --hostport=-1: The host port mapping for the container port. To demonstrate a single-machine container.
      --image="": 用来运行的容器镜像。
      --include-extended-apis[=true]: If true, include definitions of new APIs via calls to the API server. [default true]
  -l, --labels="": pod 的标签。
      --leave-stdin-open[=false]: If the pod is started in interactive mode or with stdin, leave stdin open after the first attach completes. By default, stdin will be closed after the first attach completes.
      --limits="": The resource requirement limits for this container.  For example, 'cpu=200m,memory=512Mi'
      --no-headers[=false]: 当使用默认输出格式时不打印标题栏。
  -o, --output="": Output format. One of: json|yaml|wide|name|go-template=...|go-template-file=...|jsonpath=...|jsonpath-file=... See golang template [http://golang.org/pkg/text/template/#pkg-overview] and jsonpath template [http://releases.k8s.io/release-1.2/docs/user-guide/jsonpath.md].
      --output-version="": Output the formatted object with the given group version (for ex: 'extensions/v1beta1').
      --overrides="": An inline JSON override for the generated object. If this is non-empty, it is used to override the generated object. Requires that the object supply a valid apiVersion field.
      --port=-1: The port that this container exposes.  If --expose is true, this is also the port used by the service that is created.
      --record[=false]: Record current kubectl command in the resource annotation.
  -r, --replicas=1: Number of replicas to create for this container. Default is 1.
      --requests="": The resource requirement requests for this container.  For example, 'cpu=100m,memory=256Mi'.  Note that server side components may assign requests depending on the server configuration, such as limit ranges.
      --restart="Always": The restart policy for this Pod.  Legal values [Always, OnFailure, Never].  If set to 'Always' a deployment is created for this pod, if set to 'OnFailure', a job is created for this pod, if set to 'Never', a regular pod is created. For the latter two --replicas must be 1.  Default 'Always'
      --rm[=false]: If true, delete resources created in this command for attached containers.
      --save-config[=false]: If true, the configuration of current object will be saved in its annotation. This is useful when you want to perform kubectl apply on this object in the future.
      --service-generator="service/v2": The name of the generator to use for creating a service.  Only used if --expose is true
      --service-overrides="": An inline JSON override for the generated service object. If this is non-empty, it is used to override the generated object. Requires that the object supply a valid apiVersion field.  Only used if --expose is true.
  -a, --show-all[=false]: When printing, show all resources (default hide terminated pods.)
      --show-labels[=false]: When printing, show all labels as the last column (default hide labels column)
      --sort-by="": If non-empty, sort list types using this field specification.  The field specification is expressed as a JSONPath expression (e.g. '{.metadata.name}'). The field in the API resource specified by this JSONPath expression must be an integer or a string.
  -i, --stdin[=false]: Keep stdin open on the container(s) in the pod, even if nothing is attached.
      --template="": Template string or path to template file to use when -o=go-template, -o=go-template-file. The template format is golang templates [http://golang.org/pkg/text/template/#pkg-overview].
  -t, --tty[=false]: Allocated a TTY for each container in the pod.
```

#### 

