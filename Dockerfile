# Use a base image with JDK
FROM eclipse-temurin:17-jdk

# Set working directory inside the container
WORKDIR /app

# Copy all project files into the container
COPY . /app

# Install Ant (to build the project)
RUN apt update && apt install -y ant

# Build the project using Ant, show output for debugging
RUN ant jar || { echo 'Ant build failed'; exit 1; }

# Check if the JAR file was created and list the build directory
RUN ls -l build
