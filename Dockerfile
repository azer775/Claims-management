# Use OpenJDK 21 as the base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/gestionsinistre-1.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
# EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
