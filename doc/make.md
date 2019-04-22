# Make

控制工程中通过源码生成可执行文件和其他相关文件的工具。Make 通过 Makefile 获取如何编译、链接和安装清理工程的信息。

[跟我一起写Makefile](https://github.com/seisman/how-to-write-makefile)

## Makefile
### 语法

#### 注释

井号（#）在Makefile中表示注释。

#### echoing

正常情况下，make会打印每条命令，然后再执行，这就叫做回声（echoing）。在命令的前面加上@，就可以关闭回声。

由于在构建过程中，需要了解当前在执行哪条命令，所以通常只在注释和纯显示的echo命令前面加上@。

```bash
test:
    @# 这是测试
    @echo TODO
```

## 常见错误

### Makefile:6: *** missing separator.  Stop.

在makefile中，命令行要以tab键开头，在windows下和linux下，可能由于编辑器不同，我们设置的tab不同。这样就容易引发上面的问题。所以，解决办法是在命令行开头加tab。

