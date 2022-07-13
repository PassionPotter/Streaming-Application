FROM openjdk:8-jdk-slim

RUN mkdir /app
RUN mkdir /app/app-support-libs

WORKDIR /app

ADD ./target/spring-spark-sql-cassandra-0.0.1-SNAPSHOT.jar /app/spring-spark-sql-cassandra-0.0.1-SNAPSHOT.jar

ENV spark.master=local
ENV server.port=6060
ENV cassandra.host=localhost
ENV cassandra.port=9042
ENV app.support.libs.dir=/app/app-support-libs

ENTRYPOINT ["java", "-jar", "spring-spark-sql-cassandra-0.0.1-SNAPSHOT.jar"]