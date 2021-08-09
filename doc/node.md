# 安装        
```bash
brew install node  #macos 下安装
```

## apt

```bash
# Using Ubuntu
curl -fsSL https://deb.nodesource.com/setup_current.x | sudo -E bash -
sudo apt-get install -y nodejs

# Using Debian, as root
curl -fsSL https://deb.nodesource.com/setup_current.x | bash -
apt-get install -y nodejs
```





## 更换NPM镜像

```bash
npm config set registry https://registry.npm.taobao.org
```

## 设置代理

`npm config set proxy http://127.0.0.1:1087`

## 移除代理

````bash
npm config delete http-proxy
npm config delete https-proxy

npm config rm proxy
npm config rm https-proxy

set HTTP_PROXY=null
set HTTPS_PROXY=null
````

## npm 命令

```bash
npm root -g  # 查看全局包位置
npm config set prefix '目标目录'  # 修改全局包位置 
npm uninstall -g xxx   # 卸载命令 
# 检查过是的npm包
npm outdated 
```

Webpack4 和 Babel