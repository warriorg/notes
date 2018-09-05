##90535
![image](images/app_store_1.png)

>###*solution*
>
>只要把提示的bundle里边带的CFBundleExecutable按照提示删除了就行

##Error ITMS-90168: “The binary you uploaded was invalid.”
>###*solution*
>```
>cd ~/.itmstransporter
rm update_check*
mv softwaresupport softwaresupport.bak
cd UploadTokens
rm *.token
>```