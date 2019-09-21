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
  --v=0: 指定输出日志的级别。
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

