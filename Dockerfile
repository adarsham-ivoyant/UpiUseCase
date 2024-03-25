FROM amd64/amazoncorretto:21
LABEL authors="sagar"
EXPOSE 8080
COPY target/UpiUseCase-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]