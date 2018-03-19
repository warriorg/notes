macos 命令行安装 ipa

苹果发神经后itunes已经无法在安装app了，如果有知道的兄弟可以告诉我啊

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

```
brew uninstall ideviceinstaller
brew uninstall libimobiledevice
brew install --HEAD libimobiledevice
brew link --overwrite libimobiledevice
brew install --HEAD  ideviceinstaller
brew link --overwrite ideviceinstaller
sudo chmod -R 777 /var/db/lockdown/
```
