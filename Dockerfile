FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY build/libs/TaskHubCore-0.0.1-SNAPSHOT.jar /app/TaskHubCore-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/TaskHubCore-0.0.1-SNAPSHOT.jar"]
