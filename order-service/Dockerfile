# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre as runtime

VOLUME /tmp
WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/target/order-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8084

# Run the application
ENTRYPOINT ["java", "-Dio.netty.transport.noNative=true", "-jar", "/app/app.jar"]

