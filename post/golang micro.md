# GoLang实现微服务

- 安装gRPc/protobuf - [教程在此](https://grpc.io/docs/quickstart/go.html)
- 安装Golang - [教程在此](https://golang.org/doc/install)

安装gRpc库

`brew install protobuf`

```bash
git clone https://github.com/grpc/grpc-go.git ~/.go/src/google.golang.org/grpc
git clone https://github.com/golang/net.git ~/.go/src/golang.org/x/net
git clone https://github.com/golang/text.git ~/.go/src/golang.org/x/text
go get -u github.com/golang/protobuf/{proto,protoc-gen-go}
git clone https://github.com/google/go-genproto.git ~/.go/src/google.golang.org/genproto

cd ~/.go/src/

go install google.golang.org/grpc  

# 设置环境变了,刚开始使用 brew install protoc-gen-go 安装后，无法生成proto的service部分
export $PATH=$PATH:$GOPATH/bin
```



使用 [go-micro](https://github.com/micro/go-micro)

安装 Go-Micro 依赖:

```bash
go get -u github.com/micro/protobuf/{proto,protoc-gen-go}
```



