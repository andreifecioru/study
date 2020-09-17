FROM openjdk:8-alpine

RUN apk update && apk upgrade \
    && apk add openrc openssh rsync curl procps supervisor vim bash ncurses bash-completion jq docker coreutils su-exec \
    && rm -rf /var/cache/apk/*

RUN mkdir -p /run/openrc \
    && touch /run/openrc/softlevel \
    && rc-update add sshd

# SSH Key Passwordless
RUN /usr/bin/ssh-keygen -A \
    && ssh-keygen -q -N "" -t rsa -f /etc/ssh/id_rsa \
    && cp /etc/ssh/id_rsa.pub /etc/ssh/authorized_keys \
    && mkdir -p /root/.ssh \
    && cp /etc/ssh/ssh_config /root/.ssh/config \
    && cp /etc/ssh/authorized_keys /root/.ssh/authorized_keys \
    && cp /etc/ssh/id_rsa /root/.ssh/id_rsa \
    && chmod 700 /root/.ssh \
    && chmod 600 /root/.ssh/config \
    && chmod 600 /root/.ssh/id_rsa \
    && chmod 600 /root/.ssh/authorized_keys \
    && sed -i '/StrictHostKeyChecking/s/ask/no/g' /etc/ssh/ssh_config \
    && sed -i '/StrictHostKeyChecking/s/#//g' /etc/ssh/ssh_config \
    && sed -i "s/#PubkeyAuthentication/PubkeyAuthentication/g" /etc/ssh/sshd_config \
    && sed -i "s/#PasswordAuthentication yes/PasswordAuthentication no/g" /etc/ssh/sshd_config \
    && sed -i -e 's/^root:!:/root::/' /etc/shadow

RUN ln -s /usr/lib/jvm/java-1.8-openjdk /opt/jdk

# glibc
ENV GLIB_VERSION 2.25-r0
RUN apk --update add ca-certificates \
    && curl -Ls https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIB_VERSION}/glibc-${GLIB_VERSION}.apk > /tmp/glibc-${GLIB_VERSION}.apk \
    && apk add --allow-untrusted /tmp/glibc-${GLIB_VERSION}.apk

RUN mkdir -p /tools

COPY files/* /tools
