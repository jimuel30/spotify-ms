FROM openjdk:17-jdk
WORKDIR /app
COPY target/spotfy-ms-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]
