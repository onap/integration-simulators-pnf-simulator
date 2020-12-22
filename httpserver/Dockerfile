FROM httpd:2.4

RUN apt-get update && apt-get -y install nano vim libapache2-mod-php php php-curl

RUN a2enmod mpm_prefork
RUN a2dismod mpm_event
RUN a2enmod rewrite
RUN a2enmod ssl

ENV APACHE_LOG_DIR /var/log/apache2
ENV APACHE_LOCK_DIR /var/lock/apache2
ENV APACHE_PID_FILE /var/run/apache2.pid

ADD resources/.htpasswd /usr/local/apache2/passwd/.htpasswd
ADD resources/.htaccess /usr/local/apache2/htdocs/.htaccess
ADD resources/upload.php /usr/local/apache2/conf/upload.php
ADD resources/apache-config.conf /etc/apache2/sites-enabled/000-default.conf
ADD resources/apache2.conf /etc/apache2/apache2.conf
ADD resources/cert/ /etc/apache2/certs/
