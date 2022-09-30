# loki

## Client

### promtail



### docker

#### Install
```bash
docker plugin install grafana/loki-docker-driver:latest --alias loki --grant-all-permissions
```

#### update
```bash
docker plugin disable loki --force
docker plugin upgrade loki grafana/loki-docker-driver:latest --grant-all-permissions
docker plugin enable loki
systemctl restart docker
```

#### uninstall
```bash
docker plugin disable loki --force
docker plugin rm loki
```

####  Configuring
```bash
docker run --log-driver=loki \
    --log-opt loki-url="http://loki:3100/loki/api/v1/push" \
    --log-opt loki-retries=5 \
    --log-opt loki-batch-size=400 \
    mingrammer/flog
```

##### docker-compose
```yaml
version: "3"

x-loggin: &default-logging
  driver: loki
  options:
    loki-url: http://192.168.1.94:3100/loki/api/v1/push
    loki-batch-size: "500"
    loki-pipeline-stages: |
      - regex:
          expression: '(?P<level>(TRACE|DEBUG|INFO|WARN|ERROR))'
      - labels:
          level:

services:
  flog: # 测试日志
    image: mingrammer/flog
    logging:
      driver: loki
      options:
        loki-url: http://loki:3100/loki/api/v1/push
    command: -f json -d 1s -l
```
