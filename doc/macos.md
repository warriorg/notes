### 重置NVRAM

  1. 将 Mac 关机 
  2. 重新开机，并在开机同时按住以下四个按键Option、Command、P 和 R不要松手。 
  3. 您可以在大约 20 秒后松开这些按键，在此期间您的 Mac 可能会有亮屏和暗屏的跳转。 （如果您的MAC有启动音，请在Duang两声后再松手）

### 重置SMC

 配有 Apple T2 芯片的 Mac 笔记本电脑 先尝试以下操作： 选取苹果菜单 >“关机”。 在 Mac 关机后，按住电源按钮 10 秒钟。 松开电源按钮，然后等待几秒钟。 再次按下电源按钮以开启 Mac。 如果上述操作无法解决问题，请按照以下步骤操作： 选取苹果菜单 >“关机”。 在 Mac 关机后，按住右 Shift 键、左 Option 键和左 Control 键 7 秒钟。然后，在按住电源按钮的同时继续按住这些按键 7 秒钟。 松开所有三个按键和电源按钮，然后等待几秒钟。 再次按下电源按钮以开启 Mac。



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

### macos 命令行安装 ipa

[ideviceinstaller](https://github.com/libimobiledevice/ideviceinstaller) 
> 使用这个开源项目可以在macos下安装app

```bash
brew install ideviceinstaller			
# 使用 brew 安装 ideviceinstaller
idevice_id -l 
# 查看链接的设备id
ideviceinstaller -l
# 获取设备上所有app的bundle id
ideviceinstaller -i demo.ipa
# 安装app到iphone
ideviceinstaller -u appBundle_id
# 卸载

```

安装后， 运行 `ideviceinstaller -i demo.ipa` 出现 `Could not connect to lockdownd. Exiting.` 这个，可以看看这个 [issues](https://github.com/libimobiledevice/ideviceinstaller/issues/48)

我使用下面这个方式成功解决

```bash
brew uninstall ideviceinstaller
brew uninstall libimobiledevice
brew install --HEAD libimobiledevice
brew link --overwrite libimobiledevice
brew install --HEAD  ideviceinstaller
brew link --overwrite ideviceinstaller
sudo chmod -R 777 /var/db/lockdown/
```


### 重装vscode
```bash
rm -rf ~/.vscode
rm -rf ~/Library/Application Support/Code
```

### 查看静态路由表
```bash
netstat -rn |grep default
```

### 快捷键
**Command-L** 功能和火狐的 F6 类似，聚焦到地址栏，直接修改内容。
**Command-D** 在保存文件的时候，可以快速跳到桌面。
**Command+Shift+.** 显示所有隐藏文件


### 软件

* **OpenBoard** 白板