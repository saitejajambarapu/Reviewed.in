# -----------------------------
# Stage 1: Build the application
# -----------------------------
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

# Set working directory inside container
WORKDIR /app

# Copy only pom.xml first (so docker cache can be reused if source code changes)
COPY pom.xml .

# Download all dependencies and plugins ahead of time
# This step is cached unless pom.xml changes
RUN mvn dependency:go-offline

# Now copy the application source code
COPY src ./src

# Build the application jar
RUN mvn package


# -----------------------------
# Stage 2: Run the application
# -----------------------------
FROM eclipse-temurin:21-jre-alpine

# Set working directory in runtime container
WORKDIR /app

# Copy only the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Inform docker container listens on 8080
EXPOSE 8080

# Start the Spring Boot jar
CMD ["java", "-jar", "app.jar"]























# FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
# WORKDIR /app
# COPY pom.xml .
# COPY src ./src
# RUN mvn -DskipTests package

# FROM eclipse-temurin:21-jre-alpine
# WORKDIR /app
# COPY --from=build /app/target/*.jar app.jar
# EXPOSE 8080
# CMD ["java","-jar","app.jar"]









# FROM maven:3.9.4-eclipse-temurin-21-alpine

# WORKDIR /app

# COPY ./target/SecurityApp-0.0.1-SNAPSHOT.jar .

# EXPOSE 8080

# CMD ["java", "-jar", "SecurityApp-0.0.1-SNAPSHOT.jar"]





