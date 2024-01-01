
## Install

适用于V8.x的安装版本
```bash
sudo apt-get update && sudo apt-get install kibana

# Run Kibana with `systemd`
sudo /bin/systemctl daemon-reload
sudo /bin/systemctl enable kibana.service
sudo systemctl start kibana.service

# edit /etc/kibana/kibana.yml
server.host: "0.0.0.0"
sudo systemctl restart kibana.service

# 浏览器访问 http://IP:5601

# Start Elasticsearch and generate an enrollment token for Kibana
/usr/share/elasticsearch/bin/elasticsearch-create-enrollment-token -s kibana

# 生成验证码
/usr/share/kibana/bin/kibana-verification-code
```

## 快速开始

### 查询界面
Dev Tools / Console