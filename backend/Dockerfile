FROM maven:3.8.6-openjdk-17-slim AS build

WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Download all required dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Package the application
RUN mvn package -DskipTests

# Create the final image
FROM openjdk:17-slim

WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/*.jar app.jar

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=docker

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
