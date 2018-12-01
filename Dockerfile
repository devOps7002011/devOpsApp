FROM openjdk:8-alpine

ADD target/musicservice.jar .

CMD java -jar musicservice.jar