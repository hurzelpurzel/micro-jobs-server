# https://www.baeldung.com/spring-boot-docker-images
FROM openjdk:17-alpine as builder
COPY ./ ./
RUN ./gradlew build --no-daemon
ARG JAR_FILE=build/libs/micro-jobs-server-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
#RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:17-alpine
COPY --from=builder app.jar app.jar
ENTRYPOINT ["java", "-jar","app.jar"]
