FROM openjdk:8-alpine

ADD target/music-service-1.0-SNAPSHOT.jar .

CMD java -jar music-service-1.0-SNAPSHOT.jar