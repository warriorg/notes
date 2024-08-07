#!/bin/bash

readonly repo=registry.cn-hangzhou.aliyuncs.com/google_containers
readonly k8s_repo=registry.k8s.io

KUBE_VERSION=v1.30.1

sync_one(){
	docker pull ${repo}/${1}:${2}
	docker tag ${repo}/${1}:${2} ${k8s_repo}/${1}:${2}
	docker save ${k8s_repo}/${1}:${2} -o ${1}.tar
	docker rmi  ${repo}/${1}:${2} 
}



images=(kube-apiserver:$KUBE_VERSION\
        kube-controller-manager:$KUBE_VERSION \
        kube-scheduler:$KUBE_VERSION\
        kube-proxy:$KUBE_VERSION \
        coredns/coredns:v1.11.1 \
        pause:3.9 \
        etcd:3.5.12-0)

for image in ${images[@]} ; do
	IFS=':' read -r -a array <<< "$image"
	sync_one ${array[0]} ${array[1]}
done