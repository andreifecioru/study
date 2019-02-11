FROM alpine

RUN apk update && apk upgrade \
    && apk add openssh rsync curl procps vim bash ncurses bash-completion \
    && rm -rf /var/cache/apk/*

# SSH Key Passwordless
RUN /usr/bin/ssh-keygen -A \
    && ssh-keygen -q -N "" -t rsa -f /etc/ssh/id_rsa \
    && cp /etc/ssh/id_rsa.pub /etc/ssh/authorized_keys
RUN mkdir -p /root/.ssh \
    && cp /etc/ssh/ssh_config /root/.ssh/config \
    && cp /etc/ssh/authorized_keys /root/.ssh/authorized_keys \
    && cp /etc/ssh/id_rsa /root/.ssh/id_rsa
RUN chmod 700 /root/.ssh \
    && chmod 600 /root/.ssh/config \
    && chmod 600 /root/.ssh/id_rsa \
    && chmod 600 /root/.ssh/authorized_keys
RUN sed -i '/StrictHostKeyChecking/s/ask/no/g' /etc/ssh/ssh_config \
    && sed -i '/StrictHostKeyChecking/s/#//g' /etc/ssh/ssh_config

# glibc
ENV GLIB_VERSION 2.25-r0
RUN apk --update add ca-certificates \
    && curl -Ls https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIB_VERSION}/glibc-${GLIB_VERSION}.apk > /tmp/glibc-${GLIB_VERSION}.apk \
    && apk add --allow-untrusted /tmp/glibc-${GLIB_VERSION}.apk

# Java
RUN JAVA_VERSION_MAJOR=8 && \
    JAVA_VERSION_MINOR=141 && \
    JAVA_VERSION_BUILD=15 && \
    JAVA_PACKAGE=jdk && \
    URL=336fa29ff2bb4ef291e347e091f7f4a7 && \
    mkdir -p /opt && \
    curl -jkSLH "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/${JAVA_VERSION_MAJOR}u${JAVA_VERSION_MINOR}-b${JAVA_VERSION_BUILD}/${URL}/${JAVA_PACKAGE}-${JAVA_VERSION_MAJOR}u${JAVA_VERSION_MINOR}-linux-x64.tar.gz \
    | tar -xzf - -C /opt \
    && ln -s /opt/jdk1.${JAVA_VERSION_MAJOR}.0_${JAVA_VERSION_MINOR} /opt/jdk \
    && rm -rf /opt/jdk/*src.zip \
           /opt/jdk/lib/missioncontrol \
           /opt/jdk/lib/visualvm \
           /opt/jdk/lib/*javafx* \
           /opt/jdk/jre/plugin \
           /opt/jdk/jre/bin/javaws \
           /opt/jdk/jre/bin/jjs \
           /opt/jdk/jre/bin/orbd \
           /opt/jdk/jre/bin/pack200 \
           /opt/jdk/jre/bin/policytool \
           /opt/jdk/jre/bin/rmid \
           /opt/jdk/jre/bin/rmiregistry \
           /opt/jdk/jre/bin/servertool \
           /opt/jdk/jre/bin/tnameserv \
           /opt/jdk/jre/bin/unpack200 \
           /opt/jdk/jre/lib/javaws.jar \
           /opt/jdk/jre/lib/deploy* \
           /opt/jdk/jre/lib/desktop \
           /opt/jdk/jre/lib/*javafx* \
           /opt/jdk/jre/lib/*jfx* \
           /opt/jdk/jre/lib/amd64/libdecora_sse.so \
           /opt/jdk/jre/lib/amd64/libprism_*.so \
           /opt/jdk/jre/lib/amd64/libfxplugins.so \
           /opt/jdk/jre/lib/amd64/libglass.so \
           /opt/jdk/jre/lib/amd64/libgstreamer-lite.so \
           /opt/jdk/jre/lib/amd64/libjavafx*.so \
           /opt/jdk/jre/lib/amd64/libjfx*.so \
           /opt/jdk/jre/lib/ext/jfxrt.jar \
           /opt/jdk/jre/lib/ext/nashorn.jar \
           /opt/jdk/jre/lib/oblique-fonts \
           /opt/jdk/jre/lib/plugin.jar \
           /tmp/* /var/cache/apk/*

ENV JAVA_HOME=/opt/jdk
ENV PATH=${PATH}:${JAVA_HOME}/bin:${JAVA_HOME}/sbin

ENV HBASE_HOME       /usr/local/hbase
ENV HBASE_CONF_DIR   ${HBASE_HOME}/conf
ENV HBASE_LOG_DIR    /var/log/hbase
ENV HBASE_TMP_DIR    /tmp/hbase

ENV HBASE_VERSION=1.4.9

RUN set -x \
    && mirror_url=$( \
        wget -q -O - http://www.apache.org/dyn/closer.cgi/hbase/ \
        | sed -n 's#.*href="\(ftp://mirrors.[^"]*\)".*#\1#p' \
        | head -n 1 \
    ) \
    && echo "Downloading from: ${mirror_url}" \
    && wget -q -O - ${mirror_url}/${HBASE_VERSION}/hbase-${HBASE_VERSION}-bin.tar.gz \
        | tar -xzf - -C /usr/local

RUN set -x \
    && ln -s /usr/local/hbase-${HBASE_VERSION} ${HBASE_HOME} \
    && adduser -D  -g '' -s /sbin/nologin -u 1000 docker \
    && for user in hadoop hbase; do \
         adduser -D -g '' -s /sbin/nologin ${user}; \
       done \
    && for user in root hbase docker; do \
         adduser ${user} hadoop; \
       done \       
    && mkdir -p \
        ${HBASE_TMP_DIR} \
        ${HBASE_LOG_DIR} \
    && chmod -R 755 \
        ${HBASE_TMP_DIR} \
        ${HBASE_LOG_DIR} \
    && chown -R hbase:hadoop \
        ${HBASE_TMP_DIR} \
        ${HBASE_LOG_DIR}  \      
    && rm -rf ${HBASE_HOME}/docs

COPY conf/* ${HBASE_CONF_DIR}/

# Bootstrap script
COPY start.sh /etc/start.sh
RUN chmod +x /etc/start.sh

ENV PATH=${PATH}:${HBASE_HOME}/bin

WORKDIR ${HBASE_HOME}

EXPOSE 16000 16010 16020 16030 

ENTRYPOINT [ "/etc/start.sh" ]