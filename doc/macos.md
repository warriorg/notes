##brew

本地安装包

```bash
brew --cache 
# 输出本地缓存 一般位置 ~/Library/Caches/Homebrew
# 将下载下来文件mv到缓存路径 download目录
# 重命名成没有下载下来的文件名 xxx--gradle-4.10-all.zip
brew upgrade gradle

```


###host file path

/private/etc/hosts
/etc/hosts

### 禁止apache开机启动
```bash
sudo launchctl unload -w /System/Library/LaunchDaemons/org.apache.httpd.plist   
```

### 开启 系统偏好设置 -> 安全性与隐私 ->  任何来源
```bash
sudo spctl --master-disable
```


###快速升级
```bash
softwareupdate -l
softwareupdate -i -a
```

### 远程唤醒其他设备
```
brew instal wakeonlan
wakeonlan 00:11:32:49:00:FB
```

### 显示所有隐藏文件
`Command+Shift+.`