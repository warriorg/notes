# gRPC

## 介绍





## protobuf

下载地址 https://github.com/google/protobuf/releases
下载C++版本

mac install

```bash
 brew install autoconf automake libtool 
 
 #如果下载的是git源码则执行配置脚步
 ./autogen.sh
 #进行安装
 ./configure --prefix=/usr/local/protobuf
 make
 make check
 make install

```


生成文件

```bash
protoc --java_out=/Users/username/code/grpc/grpc-test/src/generated/main/ helloworld.proto
```
>*如果需要指定生成目录，目录一定要先建立出来*

>golan安装
>
>*go get -u github.com/golang/protobuf/{proto,protoc-gen-go}*
>不需要把protoc-gen-go的路径设置到环境变量中，必须要让protoc能找到`protoc  helloworld.proto --go_out=plugins=grpc:.`

Protocol Buffers 语法

```proto
syntax = "proto3";

message SearchRequest {
  string query = 1;
  int32 page_number = 2;
  int32 result_per_page = 3;
}
```

分配标识号

正如上述文件格式，在消息定义中，每个字段都有唯一的一个标识符。这些标识符是用来在消息的二进制格式中识别各个字段的，一旦开始使用就不能够再改 变。注：[1,15]之内的标识号在编码的时候会占用一个字节。[16,2047]之内的标识号则占用2个字节。所以应该为那些频繁出现的消息元素保留 [1,15]之内的标识号。切记：要为将来有可能添加的、频繁出现的标识号预留一些标识号。

最小的标识号可以从1开始，最大到229 - 1, or 536,870,911。不可以使用其中的[19000－19999]的标识号， Protobuf协议实现中对这些进行了预留。如果非要在.proto文件中使用这些预留标识号，编译时就会报警。