# Step 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Build the image
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Instalar curl (necesario para el healthcheck)
RUN apk add --no-cache curl

COPY --from=build /app/target/*.jar app.jar

# Step 3: Configure the port
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
