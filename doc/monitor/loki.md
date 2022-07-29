# loki

## Client

### Docker

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

services:
  flog:
    image: mingrammer/flog
    logging:
      driver: loki
      options:
        loki-url: http://loki:3100/loki/api/v1/push
    command: -f json -d 1s -l
```
