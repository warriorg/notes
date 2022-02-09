# Install

## 准备安装文件

```bash
# 下载安装包
wget http://github.com/goharbor/harbor/releases/download/v1.10.10/harbor-online-installer-v1.10.10.tgz
# 解压
tar zxvf harbor-online-installer-v1.10.10.tgz
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