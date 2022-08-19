FROM bellsoft/liberica-openjdk-alpine:11
COPY build/libs/desk-sharing-app-spring-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/app.jar"]
