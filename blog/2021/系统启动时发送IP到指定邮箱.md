
# 系统启动时发送IP到指定邮箱

## 新建发送邮件的脚本

```python
#!/usr/bin/python3

import socket
import os
import json
import smtplib
from email.header import Header
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from time import asctime


def send_an_email(email_content): # email_content是一个字符串
	mail_host = "smtp.qiye.163.com" 
	mail_user = "xxxxx@xxxx.com" # 邮箱地址
	mail_auth_code = "passwod" # 邮箱密码
	mail_sender = mail_user # 用mail_user 作为发送人
	mail_receivers = "xxxxx@xxx.com" # 收件人邮箱地址	
	mail_msg = "{} at {}".format(email_content, asctime())
	message = MIMEText(mail_msg, 'html', 'utf-8')
	message['From'] = Header(mail_sender)  # 寄件人
	message['Subject'] = Header("IP地址")  # 邮件标题
	print("message is {}".format(message.as_string())) # debug用
	smtp = smtplib.SMTP_SSL(mail_host)
	smtp.connect(mail_host,465)

	smtp.login(mail_user, mail_auth_code) # 登陆
	smtp.sendmail(mail_sender, mail_receivers, message.as_string()) # 真正发送邮件就是这里

def get_temp_ip(current_ip):
    ip_json_path = "/home/warriorg/app/script/ip.json"
    if not os.path.exists(ip_json_path):
        print("No {}, dump it.".format(ip_json_path))
        with open(ip_json_path, 'w') as jo:
            json.dump(current_ip, jo)
            return True, current_ip

    else:
        with open(ip_json_path, 'r') as jo:
            origin_ip = json.load(jo)
        if origin_ip == current_ip:
            print("Current ip {} do not change, no need to send".format(current_ip))
            return False, current_ip
        else:
            print("The ip updated from {} to {}, update it.".format(origin_ip, current_ip))
            os.remove(ip_json_path)
            with open(ip_json_path, 'w') as jo:
                json.dump(current_ip, jo)
                return True, current_ip

def get_host_ip():
    """
    查询本机ip地址
    :return: ip
    """
    ip = ''
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.connect(('www.baidu.com', 80))
        ip = s.getsockname()[0]
    finally:
        s.close()
        return ip

def get_ip():
	global_ip = get_host_ip()
	whether_to_send, send_ip = get_temp_ip(global_ip)
	send_ip = json.dumps(send_ip)
	return whether_to_send, send_ip

if __name__ == "__main__":
    whether_to_send, global_ips = get_ip()
    if whether_to_send:
        send_an_email(global_ips)
    exit(0)
    # else:
        # print("wait and no send")

```

## 新建启动服务
`/lib/systemd/system/sendip.service`
```bash
[Unit]
Description=startup send ip to email
After=systemd-networkd-wait-online.service
Wants=systemd-resolved.service

[Service]
ExecStart=/home/users/app/script/send_ip_to_mailbox.py
Type=oneshot
Restart=on-failure
RestartSec=30
RemainAfterExit=yes

[Install]
WantedBy=multi-user.target
```

让 SystemD 重新加载配置文件，然后设置开机启动

```bash
sudo systemctl daemon-reload
sudo systemctl enable sendip.service
```