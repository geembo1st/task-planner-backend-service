# Стадия сборки
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests


# Стадия выполнения
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/task-tracker-backend.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
