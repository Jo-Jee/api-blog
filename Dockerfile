FROM openjdk:8 AS builder
COPY . .

RUN ./gradlew bootJar

FROM openjdk:8

ENV TZ=Asia/Seoul

COPY --from=builder build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
