安装        
```bash
brew install node  #macos 下安装
```

更换NPM镜像

```bash
npm config set registry https://registry.npm.taobao.org
```

设置代理

`npm config set proxy http://127.0.0.1:1087`

移除带来

````bash
npm config delete http-proxy
npm config delete https-proxy

npm config rm proxy
npm config rm https-proxy

set HTTP_PROXY=null
set HTTPS_PROXY=null
``
````