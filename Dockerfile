#Build app
FROM maven:3.8.5-openjdk-11-slim AS build
COPY src "/home/app/src"
COPY pom.xml "/home/app"
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:11.0-jre-slim
COPY --from=build /home/app/target/crappBackend-1.0.jar /crapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/crapp.jar"]