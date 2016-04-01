##protobuf
下载地址 https://github.com/google/protobuf/releases

生成文件

```bash
protoc --java_out="src/generated/main" helloworld.proto
```
>*如果需要指定生成目录，目录一定要先建立出来*

>golan安装
>
>*go get -u github.com/golang/protobuf/{proto,protoc-gen-go}*
>不许要把protoc-gen-go的路径设置到环境变量中，必须要让protoc能找到`protoc --go_out=. helloworld.proto`