# Use an official OpenJDK image as a base image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the host machine to the container
COPY target/gestion-station-ski-1.0.jar /app/ski.jar

# Expose port 8089 to allow external access
EXPOSE 8089

# Run the application
CMD ["java", "-jar", "ski.jar"]
