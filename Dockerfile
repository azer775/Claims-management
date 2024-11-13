# Use OpenJDK 21 as the base image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/gestionsinistre-1.0.1.jar app.jar
COPY wait-for-it.sh /app/

# Ensure that the wait-for-it.sh script is executable
RUN chmod +x /app/wait-for-it.sh

# Expose the port your application runs on
EXPOSE 8089

# Run the application
CMD ["java", "-jar", "app.jar"]
