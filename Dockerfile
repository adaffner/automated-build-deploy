FROM jenkins/jenkins:2.124-alpine
LABEL maintainer "arthur.daffner@audi.de"
 
USER root
 
RUN apk update && apk --no-cache add \
        curl \
        git \
        tar \
        zip \
        unzip \
        make \
        m4 \
    && rm -rf /var/cache/apk/*
 
COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN install-plugins.sh $(cat /usr/share/jenkins/plugins.txt | tr '\n' ' ');
 
USER jenkins
ENTRYPOINT ["/sbin/tini", "--", "/usr/local/bin/jenkins.sh"]
