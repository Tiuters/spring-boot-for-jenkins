FROM openjdk:17-alpine
VOLUME /tmp
COPY target/spring-boot-for-jenkins-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]
