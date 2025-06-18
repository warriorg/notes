# docker pull --platform linux/amd64 openjdk:17-jdk-oracle
# docker tag openjdk:17-jdk-oracle hub.longnows.cn/library/openjdk:17-jdk-oracle-amd64
# docker push hub.longnows.cn/library/openjdk:17-jdk-oracle-amd64

# docker pull --platform linux/arm64/v8 openjdk:17-jdk-oracle
# docker tag openjdk:17-jdk-oracle hub.longnows.cn/library/openjdk:17-jdk-oracle-arm64
# docker push hub.longnows.cn/library/openjdk:17-jdk-oracle-arm64

# docker manifest create -a hub.longnows.cn/library/openjdk:17-jdk-oracle \
#   hub.longnows.cn/library/openjdk:17-jdk-oracle-amd64 \
#   hub.longnows.cn/library/openjdk:17-jdk-oracle-arm64

# docker manifest annotate hub.longnows.cn/library/openjdk:17-jdk-oracle hub.longnows.cn/library/openjdk:17-jdk-oracle-amd64 --os linux --arch amd64
# docker manifest annotate hub.longnows.cn/library/openjdk:17-jdk-oracle hub.longnows.cn/library/openjdk:17-jdk-oracle-arm64 --os linux --arch arm64

# docker manifest push hub.longnows.cn/library/openjdk:17-jdk-oracle

pull_platform(){
  docker pull --platform $1 $2
  docker tag openjdk:17-jdk-oracle hub.longnows.cn/library/$3
  docker push hub.longnows.cn/library/$3
}

manifest() {
  docker manifest rm hub.longnows.cn/library/openjdk:17-jdk-oracle 2> /dev/null
  docker manifest create -a hub.longnows.cn/library/$1 hub.longnows.cn/library/openjdk:$2 hub.longnows.cn/library/openjdk:$4
  docker manifest annotate hub.longnows.cn/library/$1 hub.longnows.cn/library/$2 --os linux --arch $3
  docker manifest annotate hub.longnows.cn/library/$1 hub.longnows.cn/library/$4 --os linux --arch $5
  docker manifest push hub.longnows.cn/library/$1
}

pull_platform linux/amd64 openjdk:17-jdk-oracle openjdk:17-jdk-oracle-amd64
sleep 5s
pull_platform linux/arm64/v8 openjdk:17-jdk-oracle openjdk:17-jdk-oracle-arm64
manifest openjdk:17-jdk-oracle openjdk:17-jdk-oracle-amd64 amd64 openjdk:17-jdk-oracle-arm64 arm64