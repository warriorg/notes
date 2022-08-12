# [Prometheus](https://prometheus.io/)

## 简介

### 基础架构

![prometheus-architecture](../assets/images/prometheus-architecture.svg)



## 基本概念



# Exporter

## mysqld-exporter

```bash
CREATE USER 'exporter'@'localhost' IDENTIFIED BY 'password' WITH MAX_USER_CONNECTIONS 3;
GRANT PROCESS, REPLICATION CLIENT, SELECT ON *.* TO 'exporter'@'localhost';
```



