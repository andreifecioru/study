FROM stack_base

# Supervidor
COPY files/conf/supervisord.conf /etc/supervisord.conf

# Hadoop
ENV HADOOP_VERSION=2.10.1
ENV HADOOP_HOME=/opt/hadoop
ENV HADOOP_INSTALL=$HADOOP_HOME
ENV HADOOP_COMMON_HOME=$HADOOP_HOME
ENV HADOOP_MAPRED_HOME=$HADOOP_HOME
ENV HADOOP_HDFS_HOME=$HADOOP_HOME
ENV YARN_HOME=$HADOOP_HOME
ENV PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

RUN curl http://ftp.unicamp.br/pub/apache/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz | tar -xzf - -C /usr/local/
RUN rm -fR /usr/local/hadoop-${HADOOP_VERSION}/share/doc \
           /usr/local/hadoop-${HADOOP_VERSION}/share/hadoop/common/jdiff \
    && ln -s /usr/local/hadoop-${HADOOP_VERSION}/ /opt/hadoop \
    && mv /usr/local/hadoop-${HADOOP_VERSION}/lib/native/libhadoop.so /usr/local/hadoop-${HADOOP_VERSION}/lib/native/libhadoop.so.disabled

# Configuration files
COPY files/conf/hadoop-env.sh $HADOOP_HOME/etc/hadoop/hadoop-env.sh
COPY files/conf/core-site.xml $HADOOP_HOME/etc/hadoop/core-site.xml
COPY files/conf/hdfs-site.xml $HADOOP_HOME/etc/hadoop/hdfs-site.xml
COPY files/conf/mapred-site.xml $HADOOP_HOME/etc/hadoop/mapred-site.xml
COPY files/conf/yarn-site.xml $HADOOP_HOME/etc/hadoop/yarn-site.xml

# Format namenode
RUN hdfs namenode -format

# Bootstrap script
COPY files/entrypoint.sh /entrypoint.sh

WORKDIR /root

# Hdfs ports
EXPOSE 50010 50020 50070 50075 50090 8020 9000

# Mapred ports
EXPOSE 10020 19888

# Yarn ports
EXPOSE 8030 8031 8032 8033 8040 8042 8088

#Other ports
EXPOSE 49707 22 2122

ENTRYPOINT ["/entrypoint.sh"]

