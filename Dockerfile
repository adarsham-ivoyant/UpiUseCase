FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM amd64/amazoncorretto:21
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/*.jar UpiUseCase.jar
ENTRYPOINT ["java","-jar","UpiUseCase.jar"]