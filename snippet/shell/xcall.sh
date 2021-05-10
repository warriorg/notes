#!/bin/bash

pcount=$#
if ((pcount==0)); then
	echo no args;
	exit;
fi


for ((host=1; host<4; host++)); do
	echo ------------------node0$host-------------
	ssh root@node0$host "source /etc/profile;$@"
done
