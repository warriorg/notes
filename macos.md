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