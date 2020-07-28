## Gin Swagger

使用gin-swagger为golang gin项目生成文档

```bash
go get -u github.com/swaggo/swag/cmd/swag   # download swag
```

查看swag 的初始化文档

```bash
swag init --help
NAME:
   swag init - Create docs.go

USAGE:
   swag init [command options] [arguments...]

OPTIONS:
   --generalInfo value, -g value       Go file path in which 'swagger general API Info' is written (default: "main.go")
   --dir value, -d value               Directory you want to parse (default: "./")
   --exclude value                     exclude directories and files when searching, comma separated
   --propertyStrategy value, -p value  Property Naming Strategy like snakecase,camelcase,pascalcase (default: "camelcase")
   --output value, -o value            Output directory for all the generated files(swagger.json, swagger.yaml and doc.go) (default: "./docs")
   --parseVendor                       Parse go files in 'vendor' folder, disabled by default (default: false)
   --parseDependency                   Parse go files in outside dependency folder, disabled by default (default: false)
   --markdownFiles value, --md value   Parse folder containing markdown files to use as description, disabled by default
   --parseInternal                     Parse go files in internal packages, disabled by default (default: false)
   --generatedTime                     Generate timestamp at the top of docs.go, true by default (default: false)
   --help, -h
```

初始化文档

```bash
swag init  # 需要当前目录有main.go
swag init -g serve.go    # 指定项目启动文件
```

