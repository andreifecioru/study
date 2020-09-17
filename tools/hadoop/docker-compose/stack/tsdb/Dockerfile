FROM stack_base

ENV TSDB_VERSION=2.4.0
ENV GNUPLOT_VERSION=5.2.4
ENV ALPINE_PACKAGES="rsyslog make wget libgd libpng libjpeg libwebp libjpeg-turbo cairo pango lua"
ENV BUILD_PACKAGES="build-base autoconf automake git python cairo-dev pango-dev gd-dev lua-dev readline-dev libpng-dev libjpeg-turbo-dev libwebp-dev"

RUN apk add ${ALPINE_PACKAGES} --repository http://dl-cdn.alpinelinux.org/alpine/v3.0/testing/

RUN mkdir -p /opt/opentsdb
WORKDIR /opt/opentsdb/

# Add build deps, build opentsdb, and clean up afterwards.
RUN apk add --virtual builddeps \
      ${BUILD_PACKAGES} \
    && wget --no-check-certificate \
      -O v${TSDB_VERSION}.zip \
      https://github.com/OpenTSDB/opentsdb/archive/v${TSDB_VERSION}.zip \
    && unzip v${TSDB_VERSION}.zip \
    && rm v${TSDB_VERSION}.zip \
    && cd /opt/opentsdb/opentsdb-${TSDB_VERSION} \
    && echo "tsd.http.request.enable_chunked = true" >> src/opentsdb.conf \
    && echo "tsd.http.request.max_chunk = 1000000" >> src/opentsdb.conf

# Install OpenTSDB
COPY files/patch_build.sh /opt/opentsdb/opentsdb-${TSDB_VERSION}/patch_build.sh
RUN cd /opt/opentsdb/opentsdb-${TSDB_VERSION} \
    && bash ./patch_build.sh \
    && mkdir build \
    && cp -r third_party ./build \
    && ./build.sh \
    && cp build-aux/install-sh build/build-aux \
    && cd build \
    && make install \
    && cd / \
    && rm -rf /opt/opentsdb/opentsdb-${TSDB_VERSION} \
    && ln -s /usr/local/share/opentsdb/etc/opentsdb /etc/opentsdb \
    && rm /etc/opentsdb/opentsdb.conf \
    && mkdir /opentsdb-plugins

# Install GNU-plot
RUN cd /tmp \
    && wget https://sourceforge.net/projects/gnuplot/files/gnuplot/${GNUPLOT_VERSION}/gnuplot-${GNUPLOT_VERSION}.tar.gz \
    && tar xzf gnuplot-${GNUPLOT_VERSION}.tar.gz \
    && cd gnuplot-${GNUPLOT_VERSION} \
    && ./configure \
    && make install \
    && cd /tmp && rm -rf /tmp/gnuplot-${GNUPLOT_VERSION} && rm /tmp/gnuplot-${GNUPLOT_VERSION}.tar.gz

RUN apk del builddeps && rm -rf /var/cache/apk/*

# Configuration files
ADD files/conf/opentsdb.conf /etc/opentsdb/opentsdb.conf
ADD files/start_opentsdb.sh /opt/bin/
ADD files/entrypoint.sh /entrypoint.sh

# Fix ENV variables in installed scripts
RUN for i in /opt/bin/start_opentsdb.sh; \
    do \
        sed -i "s#::TSDB_VERSION::#$TSDB_VERSION#g;" $i; \
    done

EXPOSE 4242

CMD ["/entrypoint.sh"]
