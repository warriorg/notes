```bash
# 把所有未追踪的文件添加到版本库
svn st | awk '{if ( $1 == "?") { print $2}}' | xargs svn add
```

