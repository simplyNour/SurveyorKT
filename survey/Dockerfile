FROM maven:3.6.3-openjdk-17-slim AS build

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

FROM openjdk:17

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/survey-3.2.0.jar app.jar

CMD ["java", "-jar", "app.jar"]
