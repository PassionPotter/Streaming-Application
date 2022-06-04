# streaming-application-examples
This repository includes the examples  of projects based on streaming frameworks

This project includes below set of sub projects that are going to be part of ETL project just for the sake of learning purpose.

This sample data we will be using is referred from
    
    IMDb which is an online database of information related to films, television series, home videos, video games, and streaming content online – including cast, production crew and personal biographies, plot summaries, trivia, ratings, and fan and critical reviews.
    
    The data we use in this application was available at
    https://www.imdb.com/interfaces/


1. DataModel
    This is a maven module just contains the simple POJO's for the data and will be used in different streaming application within this project as and when required.

2. Kafka data producer 
    This Spring Boot project sends data to the distributed messaging server i.e. apache kafka. The data we send to the kafka topic is related to movies/series/episodes.

    The Kafka broker to which the data is being  sent will be the ultimate source of the data for the ETL project.

    Pre-requisite :
        create topic 
                kafka-topics.bat  --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic tbasic1
    
    



