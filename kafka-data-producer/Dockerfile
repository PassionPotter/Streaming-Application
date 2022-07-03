FROM openjdk:8-jdk-slim

RUN mkdir /app
RUN mkdir /app/sample-data

WORKDIR /app

ADD ./target/kafka-data-producer-0.0.1-SNAPSHOT.jar /app/kafka-data-producer-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "kafka-data-producer-0.0.1-SNAPSHOT.jar"]