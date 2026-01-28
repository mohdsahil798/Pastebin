# =========================
# 1️⃣ Build Stage
# =========================
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy the full project
COPY . .

# Build the Spring Boot application
RUN mvn clean package -DskipTests


# =========================
# 2️⃣ Run Stage
# =========================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render uses PORT env variable internally)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
