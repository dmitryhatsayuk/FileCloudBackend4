FROM amazoncorretto:22

EXPOSE 8080

COPY target/FileCloud-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]