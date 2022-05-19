FROM openjdk:11
COPY "./target/crappBackend-1.0latest.jar" "crapp.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","crapp.jar"]
