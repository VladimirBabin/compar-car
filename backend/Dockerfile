FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy Gradle files
COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

RUN ./gradlew dependencies

COPY src ./src

RUN chmod 755 gradlew
RUN chmod 755 gradlew.bat

# Build the application
RUN ./gradlew build -x test --no-daemon

# Create a non-root user
RUN addgroup --system javauser && adduser --system --ingroup javauser javauser

# Change ownership of the app directory
RUN chown -R javauser:javauser /app

# Switch to non-root user
USER javauser

# Expose port
EXPOSE 8090

# Run the application
CMD ["java", "-jar", "build/libs/compar-car-backend-0.0.1-SNAPSHOT.jar"] 
