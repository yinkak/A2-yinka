# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY /Users/YK/Desktop/demo/pom.xml .
COPY /Users/YK/Desktop/demo/src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal image with the application JAR
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/yinka-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
