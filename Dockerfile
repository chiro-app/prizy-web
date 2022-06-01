FROM eclipse-temurin:17.0.1_12-jdk-alpine as build

WORKDIR /workspace/app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean build

FROM eclipse-temurin:17.0.1_12-jre-alpine

ARG DEPENDENCY=/workspace/app/public-api/build/libs

COPY --from=build ${DEPENDENCY}/*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]