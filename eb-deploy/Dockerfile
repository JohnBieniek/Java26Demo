FROM amazoncorretto:26-alpine-jdk

WORKDIR /app

COPY *.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "--enable-preview", "--add-modules", "jdk.incubator.vector", "-jar", "/app/app.jar"]
