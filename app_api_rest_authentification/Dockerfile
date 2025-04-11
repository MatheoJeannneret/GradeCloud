FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/app_api_rest_authentification-0.0.1-SNAPSHOT.jar appapirestauthentification.jar
EXPOSE 8080
ENTRYPOINT exec java $JAVA_OPTS -jar appapirestauthentification.jar
ENV DATABASE_URL=jdbc:mysql://mysql:3306/db_utilisateur
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar appapirestauthentification.jar
