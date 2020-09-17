FROM stack_base

ENV HBASE_VERSION=1.4.13
ENV HBASE_HOME=/usr/local/hbase
ENV HBASE_CONF_DIR=${HBASE_HOME}/conf
ENV HBASE_LOG_DIR=/var/log/hbase
ENV HBASE_TMP_DIR=/tmp/hbase
ENV PATH=${PATH}:${HBASE_HOME}/bin

RUN set -x \
    && mirror_url=$( \
        wget -q -O - http://www.apache.org/dyn/closer.cgi/hbase/ \
        | sed -n 's#.*href="\(https://mirrors.[^"]*\)".*#\1#p' \
        | head -n 1 \
    ) \
    && echo "Downloading from: ${mirror_url}" \
    && wget -q -O - ${mirror_url}/${HBASE_VERSION}/hbase-${HBASE_VERSION}-bin.tar.gz \
        | tar -xzf - -C /usr/local

RUN set -x \
    && ln -s /usr/local/hbase-${HBASE_VERSION} ${HBASE_HOME} \
    && for user in hadoop hbase; do \
         adduser -D -g '' -s /sbin/nologin ${user}; \
       done \
    && for user in root hbase; do \
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

# Configuration files
COPY files/hbase-* ${HBASE_CONF_DIR}/

# Bootstrap scripts
COPY files/create_tsdb_tables.sh /create_tsdb_tables.sh
COPY files/entrypoint.sh /entrypoint.sh

WORKDIR ${HBASE_HOME}

EXPOSE 16000 16010 16020 16030

ENTRYPOINT [ "/entrypoint.sh" ]
