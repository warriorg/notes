# Install

## 准备安装文件

```bash
# 下载安装包
wget https://github.com/goharbor/harbor/releases/download/v2.4.1/harbor-online-installer-v2.4.1.tgz
# 解压
tar zxvf harbor-online-installer-v2.4.1.tgz
```

## 配置 harbor.yml

设置 harbor.yml 文件，配置 harbor 的各项参数，如下：

```ymal

# The IP address or hostname to access admin UI and registry service.
# DO NOT use localhost or 127.0.0.1, because Harbor needs to be accessed by external clients.
hostname: harbor.example.com

# http related config
http:
  # port for http, default is 80. If https enabled, this port will redirect to https port
  port: 80

# 如果没有证书，整体注释下面的部分
# https related config
# https:
  # https port for harbor, default is 443
  # port: 443
  # The path of cert and key files for nginx
  # certificate: /your/certificate/path
  # private_key: /your/private/key/path

# Uncomment external_url if you want to enable external proxy
# And when it enabled the hostname will no longer used
# external_url: https://reg.mydomain.com:8433

# The initial password of Harbor admin
# It only works in first time to install harbor
# Remember Change the admin password from UI after launching Harbor.
# harbor 默认 admin 密码
harbor_admin_password: Harbor12345

```

## 安装 harbor

```bash
./install.sh
```

`http://harbor.example.com/` 登录，管理员账号 admin 密码 Harbor12345


# 设置

## 新建项目
![](./assets/images/245bcacfaa518a101692d6dbebd57e17.png)


# 镜像发布

配置 docker 忽视不安全的 registry  linux `/etc/docker/daemon.json` windows 上为 `C:\ProgramData\Docker\config\daemon.json`

```json
{
	"registry-mirrors":["https://registry.cn-hangzhou.aliyuncs.com"],
	"insecure-registries":["192.168.2.138:80"]
}
```

启用不安全的registry后，Docker将执行以下步骤

* 首先，尝试使用 https 访问 registry
    * 如果HTTPS协议可用，但证书无效，忽略证书错误。
    * 如果HTTPS不可用，请退回到HTTP。

重新启动 docker 使配置生效

## 测试发布镜像到 harbor

```bash
# 登录harbor
docker login http://192.168.2.138:80    
# 为 image 设置 tag
docker tag busybox 192.168.2.138:80/library/busybox:1.0
# push 镜像到harbor
push 192.168.2.138:80/library/busybox:1.0
```