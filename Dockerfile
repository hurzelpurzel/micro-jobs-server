# https://www.baeldung.com/spring-boot-docker-images
FROM openjdk:17-alpine as builder
COPY ./ ./
RUN ./gradlew build
ARG JAR_FILE=build/*.jar
COPY ${JAR_FILE} app.jar
#RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:17-alpine
COPY --from=builder app.jar app.jar
ENTRYPOINT ["java", "-jar","app.jar"]
