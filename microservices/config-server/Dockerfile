# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen ligera para ejecutar
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# 🔹 Copiar también las configuraciones (clave)
COPY src/main/resources/configurations /configurations

# Configura el puerto expuesto
EXPOSE 8888

# Usa el perfil docker explícitamente
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
