# Use Java 17 base image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy everything from your project to /app
COPY . .

# Build the project using Maven wrapper
RUN ./mvnw clean package -DskipTests

# Run the JAR from target folder
CMD ["java", "-jar", "target/event-notification-system-0.0.1-SNAPSHOT.jar"]

# Expose port 8080
EXPOSE 8080
