FROM maven:3.8.5-openjdk-19-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:19-jdk-slim
COPY --from=build /target/niquestore-0.0.1-SNAPSHOT.jar niquestore.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","niquestore.jar"]