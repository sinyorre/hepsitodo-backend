#FROM couchbase

FROM couchbase/server

RUN chmod +x /entrypoint.sh
COPY configure.sh /opt/couchbase
RUN chmod +x /opt/couchbase/configure.sh

CMD ["/opt/couchbase/configure.sh"]