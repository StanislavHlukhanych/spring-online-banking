FROM maven:3.8.5-openjdk-17 AS builder
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
COPY --from=builder /target/online-banking-0.0.1-SNAPSHOT.jar online-banking.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "online-banking.jar"]