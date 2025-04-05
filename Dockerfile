FROM openjdk:23-jdk-slim
WORKDIR /app
COPY target/online-banking-0.0.1-SNAPSHOT.jar /app/online-banking.jar
ENTRYPOINT ["java", "-jar", "online-banking.jar"]
