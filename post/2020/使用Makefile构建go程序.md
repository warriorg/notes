# 使用Markfile开发GO程序

## Markfile

makefile是用于构建和运行软件应用程序的自动化工具。

## GO程序

首先，我们创建一个GO应用程序

```go
package main

import "fmt"

func main() {
	fmt.Println("Hello Markfile!!")
}
```



## 创建Markfile

```bash
build:
	go build -o bin/main main.go

run:
	go run main.go

compile:
	# 32-Bit Systems
	# FreeBDS
	GOOS=freebsd GOARCH=386 go build -o bin/main-freebsd-386 main.go
	# MacOS
	GOOS=darwin GOARCH=386 go build -o bin/main-darwin-386 main.go
	# Linux
	GOOS=linux GOARCH=386 go build -o bin/main-linux-386 main.go
	# Windows
	GOOS=windows GOARCH=386 go build -o bin/main-windows-386 main.go
  # 64-Bit
	# FreeBDS
	GOOS=freebsd GOARCH=amd64 go build -o bin/main-freebsd-amd64 main.go
	# MacOS
	GOOS=darwin GOARCH=amd64 go build -o bin/main-darwin-amd64 main.go
	# Linux
	GOOS=linux GOARCH=amd64 go build -o bin/main-linux-amd64 main.go
	# Windows
	GOOS=windows GOARCH=amd64 go build -o bin/main-windows-amd64 main.go
```

## 运行Markfile命令

### 构建命令

为我们的平台构建Go应用程序

```bash
make build
```

### 运行命令

运行go应用程序

```bash
make run
```

### 编译命令

为了针对不同的平台和操作系统编译Go 应用程序

```bash
make compile
```



## 参考

[跟我一起写Makefile](https://github.com/seisman/how-to-write-makefile)

https://harrisonbrock.blog/go-makefile/



