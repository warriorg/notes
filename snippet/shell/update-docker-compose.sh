#/bin/bash

workdir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

h2() { printf "\n%s\n" "$@"
}

update_module() {
	h2 "update $@"
	h2 "stop service"
	docker-compose stop $@
	echo y | docker-compose rm $@
	h2 "copy db changelog success"
	\cp -r $workdir/assets/$@/assets/db/changelog* $workdir/$@/assets/db/
	h2 "update db script"
	docker run --rm -v $workdir/$@/assets/db/:/liquibase/changelog hub.longnows.cn/library/liquibase:v4.8 update
	if [ $? -gt 0 ]
	then
		h2 "update db fail, service is stop"
		exit 0
	fi
	h2 "remove image"
	docker images | grep hub.longnows.cn/pro/$@-service  | awk '{print $3}' | xargs docker rmi -f
	h2 "start service"
	docker-compose up -d $@
}

echo $1

if [ "$1" = "all" ]
then
	update_module basic
	update_module common
	update_module nems
	update_module pms
elif [ ! -z "$1" ]
then
	update_module $1
else
	echo "please input update serive name"
fi