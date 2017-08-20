FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD app-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=test", "/app.jar"]