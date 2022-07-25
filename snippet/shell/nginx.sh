#!/bin/bash

if [[ $EUID -ne 0 ]]; then
	echo -e "Sorry, you need to run this as root"
	exit 1
fi

NGINX_VER=1.23.1
# Define options
NGINX_OPTIONS=${NGINX_OPTIONS:-"
	--prefix=/etc/nginx \
	--sbin-path=/usr/sbin/nginx \
	--conf-path=/etc/nginx/nginx.conf \
	--error-log-path=/var/log/nginx/error.log \
	--http-log-path=/var/log/nginx/access.log \
	--pid-path=/var/run/nginx.pid \
	--lock-path=/var/run/nginx.lock \
	--http-client-body-temp-path=/var/cache/nginx/client_temp \
	--http-proxy-temp-path=/var/cache/nginx/proxy_temp \
	--http-fastcgi-temp-path=/var/cache/nginx/fastcgi_temp \
	--user=nginx \
	--group=nginx \
	--with-cc-opt=-Wno-deprecated-declarations \
	--with-cc-opt=-Wno-ignored-qualifiers"}
# Define modules
NGINX_MODULES=${NGINX_MODULES:-"--with-threads \
	--with-file-aio \
	--with-http_ssl_module \
	--with-http_v2_module \
	--with-http_mp4_module \
	--with-http_auth_request_module \
	--with-http_slice_module \
	--with-http_stub_status_module \
	--with-http_realip_module \
	--with-http_sub_module"}

if [[ $HEADLESS != "y" ]]; then
	echo ""
	echo "Welcome to the nginx-autoinstall script."
	echo ""
	echo "What do you want to do?"
	echo "   1) Install or update Nginx"
	echo "   2) Uninstall Nginx"
	echo "   3) Exit"
	echo ""
	while [[ $OPTION != "1" && $OPTION != "2" && $OPTION != "3" && $OPTION != "4" && $OPTION != "5" ]]; do
		read -rp "Select an option [1-3]: " OPTION
	done
fi

case $OPTION in
1)
  # Cleanup
	# The directory should be deleted at the end of the script, but in case it fails
	rm -r /usr/local/src/nginx/ >>/dev/null 2>&1
	mkdir -p /usr/local/src/nginx/modules

  # Dependencies
	apt-get update
	apt-get install -y build-essential ca-certificates wget curl libpcre3 libpcre3-dev autoconf unzip automake libtool tar git libssl-dev zlib1g-dev uuid-dev lsb-release libxml2-dev libxslt1-dev cmake

  # Download and extract of Nginx source code
	cd /usr/local/src/nginx/ || exit 1
	wget -qO- http://nginx.org/download/nginx-${NGINX_VER}.tar.gz | tar zxf -
  # curl https://nginx.org/download/nginx-$NGINX_VER.tar.gz | tar -zx 
	cd nginx-${NGINX_VER} || exit 1
  
  # As the default nginx.conf does not work, we download a clean and working conf from my GitHub.
	# We do it only if it does not already exist, so that it is not overriten if Nginx is being updated
	if [[ ! -e /etc/nginx/nginx.conf ]]; then
		mkdir -p /etc/nginx
		cd /etc/nginx || exit 1
	fi

	cd /usr/local/src/nginx/nginx-${NGINX_VER} || exit 1

  	./configure $NGINX_OPTIONS $NGINX_MODULES
	make -j "$(nproc)"
	make install

  # remove debugging symbols
	strip -s /usr/sbin/nginx

  # Nginx installation from source does not add an init script for systemd and logrotate
	# Using the official systemd script and logrotate conf from nginx.org
	if [[ ! -e /lib/systemd/system/nginx.service ]]; then
		cd /lib/systemd/system/ || exit 1
    cat << EOF > nginx.service
# Stop dance for nginx
# =======================
#
# ExecStop sends SIGSTOP (graceful stop) to the nginx process.
# If, after 5s (--retry QUIT/5) nginx is still running, systemd takes control
# and sends SIGTERM (fast shutdown) to the main process.
# After another 5s (TimeoutStopSec=5), and if nginx is alive, systemd sends
# SIGKILL to all the remaining processes in the process group (KillMode=mixed).
#
# nginx signals reference doc:
# http://nginx.org/en/docs/control.html
#
[Unit]
Description=A high performance web server and a reverse proxy server
After=network.target nss-lookup.target

[Service]
Type=forking
PIDFile=/run/nginx.pid
ExecStartPre=/usr/sbin/nginx -t -q -g 'daemon on; master_process on;'
ExecStart=/usr/sbin/nginx -g 'daemon on; master_process on;'
# See https://github.com/angristan/nginx-autoinstall/issues/67
ExecStartPost=/bin/sleep 0.1
ExecReload=/usr/sbin/nginx -g 'daemon on; master_process on;' -s reload
ExecStop=-/sbin/start-stop-daemon --quiet --stop --retry QUIT/5 --pidfile /run/nginx.pid
TimeoutStopSec=5
KillMode=mixed

[Install]
WantedBy=multi-user.target
EOF

		# Enable nginx start at boot
		systemctl enable nginx
	fi

  # Nginx's cache directory is not created by default
	if [[ ! -d /var/cache/nginx ]]; then
		mkdir -p /var/cache/nginx
	fi

  if [[ ! -d /etc/nginx/conf.d ]]; then
		mkdir -p /etc/nginx/conf.d
	fi


  # Removing temporary Nginx and modules files
	rm -r /usr/local/src/nginx

  # We're done !
	echo "Installation done."
	exit

  ;;
2) # Uninstall nginx
  # We're done !
	echo "Uninstallation done."
  # Stop Nginx
	systemctl stop nginx

	# Removing Nginx files and modules files
	rm -r /usr/local/src/nginx \
		/usr/sbin/nginx* \
		/usr/local/bin/luajit* \
		/usr/local/include/luajit* \
		/etc/logrotate.d/nginx \
		/var/cache/nginx \
		/lib/systemd/system/nginx.service \
		/etc/systemd/system/multi-user.target.wants/nginx.service

	# Reload systemctl
	systemctl daemon-reload

	exit
  ;;
*)
  exit
  ;;
esac


