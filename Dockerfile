FROM gradle:7.4.2-jdk8 AS gradle-build
ARG TOKEN
RUN git clone https://$TOKEN@github.com/usdot-fhwa-stol/CARMASensitive.git 
RUN ls -la && pwd
FROM maven:3.8.5-jdk-8-slim AS mvn-build
ADD . /root

# Run the Maven build
RUN cd /root/fedgov-cv-parent \
    && mvn install -DskipTests
RUN cd /root/fedgov-cv-lib-asn1c \
    && mvn install -DskipTests
RUN cd /root/fedgov-cv-mapencoder \
    && mvn install -DskipTests
RUN cd /root/fedgov-cv-message-builder \
    && mvn install -DskipTests
RUN cd /root/fedgov-cv-ISDcreator-webapp \
    && mvn install -DskipTests
RUN cd /root/fedgov-cv-TIMcreator-webapp \
    && mvn install -DskipTests
RUN jar cvf /root/private-resources.war -C /root/private-resources .
RUN jar cvf /root/root.war -C /root/root .

FROM jetty:9.4.46-jre8-slim
# Install the generated WAR files
COPY --from=mvn-build /root/fedgov-cv-ISDcreator-webapp/target/isd.war /var/lib/jetty/webapps 
COPY --from=mvn-build /root/fedgov-cv-TIMcreator-webapp/target/tim.war /var/lib/jetty/webapps
COPY --from=mvn-build /private-resources.war /var/lib/jetty/webapps
COPY --from=mvn-build /root/root.war /var/lib/jetty/webapps

# Create third_party_lib directory and copy the shared libraries to it
RUN mkdir -p /var/lib/jetty/webapps/third_party_lib
COPY --from=mvn-build /root/fedgov-cv-lib-asn1c/third_party_lib/libasn1c.so /var/lib/jetty/webapps/third_party_lib
COPY --from=mvn-build /root/fedgov-cv-lib-asn1c/third_party_lib/libasn1c_x64.so /var/lib/jetty/webapps/third_party_lib
COPY --from=mvn-build /root/fedgov-cv-lib-asn1c/third_party_lib/libasn1c_x86.so /var/lib/jetty/webapps/third_party_lib

#Create env file
USER root
ENV LD_LIBRARY_PATH /var/lib/jetty/webapps/third_party_lib
RUN ldconfig

RUN cd /var/lib/jetty \
    && echo 'log4j2.version=2.23.1' >> start.d/logging-log4j2.ini
RUN java -jar $JETTY_HOME/start.jar --create-files

RUN java -jar $JETTY_HOME/start.jar --add-to-start=https
COPY --from=gradle-build /home/gradle/CARMASensitive/maptool/keystore* /var/lib/jetty/etc
COPY --from=gradle-build /home/gradle/CARMASensitive/maptool/ssl.ini /var/lib/jetty/start.d/
