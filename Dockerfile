FROM openjdk:8-jre-alpine
COPY music-rest/target/music-service-1.0-SNAPSHOT.jar .
CMD ["java","-jar","music-rest-1.0-SNAPSHOT.jar"]
