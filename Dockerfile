#FROM eclipse-temurin:21-jre
#
#WORKDIR /app
#
#COPY target/Shortened-links-service-1.0-SNAPSHOT.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY pom.xml .
RUN mvn -q dependency:go-offline

COPY src ./src
RUN mvn -q clean package -DskipTests


# ---- Runtime stage ----
FROM eclipse-temurin:21-jre

WORKDIR /app

# копируем JAR из стадии сборки
COPY --from=build /app/target/*-shaded.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
