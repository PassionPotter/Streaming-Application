# streaming-application-examples
This repository includes the examples  of projects based on streaming frameworks

This project includes below set of sub projects that are going to be part of ETL project just for the sake of learning purpose.

This sample data we will be using is referred from
    
    IMDb which is an online database of information related to films, television series, home videos, video games, and streaming content online – including cast, production crew and personal biographies, plot summaries, trivia, ratings, and fan and critical reviews.
    
    The data we use in this application was available at
    https://www.imdb.com/interfaces/


1. DataModel
    This is a maven module just contains the simple POJO's for the data and will be used in different streaming application within this project as and when required.

2. K8s
    This folder consist of yaml files that are responsible to create kafka cluster and cassandra cluster.

    yaml files have been segregated as per the technology we will be using

    kafka-on-kubernetes:
        This folders includes the yaml files that creates kubernetes artifacts such as deployment and services of kafka and zookeeper exposed to external world.

        inside below deployment file, invalid value for the property "KAFKA_ADVERTISED_HOST_NAME" has been added , one has to replace the valid ip address such as load balancer ip or one of the node in kubernetes cluster.

        ..\kafka-on-kubernetes\kafka-deployment.yaml

    cassandra-on-kubernetes:
        This folders includes the yaml files that creates kubernetes artifacts such as deployment and services of cassandra NoSQL Database exposesd to external world.



3. Kafka data producer 
    This Spring Boot project sends data to the distributed messaging server i.e. apache kafka. The data we send to the kafka topic is related to movies/series/episodes.

    The Kafka broker to which the data is being  sent will be the ultimate source of the data for the ETL project.

    Pre-requisite :
        create topic 
                kafka-topics.bat  --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic tbasic1

    with this project we have enabled the ways of data ingestion by exposing two different APIs.

      One Post API that adds a record to kafka as per the request body. Other API reads the sample file and adds each record/loads to the kafka topic.



    
    



