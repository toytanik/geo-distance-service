FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the build context to the container
COPY target/geo-distance-service.jar app.jar

# Expose the port that your application is listening on (change the port number if necessary)
EXPOSE 8080

# Set the command to run your application when the container starts
CMD ["java", "-jar", "app.jar"]
