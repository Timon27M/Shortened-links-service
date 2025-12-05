FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/Shortened-links-service-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
