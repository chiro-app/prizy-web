FROM eclipse-temurin:17.0.3_7-jdk-focal as builder

WORKDIR /src

COPY . .

RUN chmod +x gradlew

RUN ./gradlew bootJar

FROM eclipse-temurin:17.0.3_7-jre-alpine

WORKDIR /app

COPY --from=builder /src/public-api/build/libs/public-api-*.jar prizy-web.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/prizy-web.jar"]