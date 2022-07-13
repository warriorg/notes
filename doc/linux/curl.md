# curl

## cheat sheet



### 测速

```bash
curl -so /dev/null -w "\ndnslookup: %{time_namelookup} | connect: %{time_connect} | appconnect: %{time_appconnect} | pretransfer: %{time_pretransfer} | starttransfer: %{time_starttransfer} | total: %{time_total} | size: %{size_download}\n" https://baidu.com
```

