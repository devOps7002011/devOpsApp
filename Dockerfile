FROM openjdk:8-jre-alpine
COPY music-service/target/music-service-1.0-SNAPSHOT.jar .
CMD ["java","-jar","music-service-1.0-SNAPSHOT.jar"]