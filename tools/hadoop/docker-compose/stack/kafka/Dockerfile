FROM stack_base

ENV KAFKA_VERSION=2.8.0
ENV SCALA_VERSION=2.12
ENV PATH=/sbin:/opt/kafka/bin/:$PATH

RUN mkdir -p /opt \
  && mirror=$(curl --stderr /dev/null https://www.apache.org/dyn/closer.cgi\?as_json\=1 | jq -r '.preferred') \
  && curl -sSL "${mirror}kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz" | tar -xzf - -C /opt
RUN mv /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION} /opt/kafka \
  && adduser -DH -s /sbin/nologin kafka \
  && chown -R kafka: /opt/kafka \
  && rm -rf /tmp/*

COPY files/conf/server.properties /opt/kafka/config/
COPY files/conf/zookeeper.properties /opt/kafka/config/
COPY files/entrypoint.sh /entrypoint.sh
COPY files/*.sh /

WORKDIR /opt/kafka

VOLUME ["/tmp/kafka-logs"]

EXPOSE 9092

ENTRYPOINT ["/entrypoint.sh"]
CMD ["kafka-server-start.sh", "/opt/kafka/config/server.properties"]
