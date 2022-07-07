# streaming-application-examples
This repository includes the examples  of projects based on streaming frameworks

This project includes below set of sub projects that are going to be part of ETL project just for the sake of learning purpose.

This sample data we will be using is referred from IMDb which is an online database of information related to films, television series, home videos, video games, and streaming content online – including cast, production crew and personal biographies, plot summaries, trivia, ratings, and fan and critical reviews.
 The data we use in this application was available at **https://www.imdb.com/interfaces/**


 ## 1. DataModel ## 
   This is a maven module just contains the simple POJO's for the data and will be used in different streaming application within this project as and when required.
 ## 2. K8s ##
   This folder consist of yaml files that are responsible to create kafka cluster and cassandra cluster.yaml files have been segregated as per the technology we will be using

 ### kafka-on-kubernetes: ###
  This folders includes the yaml files that creates kubernetes artifacts such as below
* Services:

	* zoo1 : This service exposes zookeeper service for the kafka.	
	* kafka-service : This service exposes kafka service 		
        
* Deployments:

	* zookeeper-deploy : deployment of zookeeper orchestration for kafka brokers		
	* kafka-broker0 : deployment of kafka distributed message queue
		
        - Note: deployment file ..\kafka-on-kubernetes\kafka-deployment.yaml, checked-in with placeholder **${LOADBALANCER_IP}** value for the property "KAFKA_ADVERTISED_HOST_NAME" has been added , one has to replace the valid ip address such as load balancer ip or one of the node in kubernetes cluster.

        - create kafka topics :

            	kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic tbasic4		    
            	kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic trating4        

### cassandra-on-kubernetes: ###
  This folders includes the yaml files that creates kubernetes artifacts such as below
* Services:

	* cassandra-service : This service exposes cassandra service for variouse clients
	
* Deployments:

	* cassandra : deployment of cassandra nosql database
        
### standalone-flink-job: ###    
  This folder includes the yaml files that creates kubernetes artifacts such as below
* Config Maps:

	*  flink-config : configurations for below
	     flink-conf.yaml
             log4j.console
         
* Services:
     
	* flink-jobmanager : flink job manager service 
         
	* flink-taskmanager : flink task manager service 

         
* StatefulSets:

	* flink-taskmanager: Flink TaskManager as a statefulset that executes the flink job
         
* Jobs:

	* flink-jobmanager: Flink JobManager Standalone Job from the --job-classname passed

### kafka-producer: ###
  This folder includes the yaml files that creates kubernetes artifacts such as below
* Config Maps:

	*  kafka-producer-config : configurations for below
	     application.properties
	     
* Persistent Volume and Persistent Volume claim:	     
	
	* kafka-produce-persistent-volume : PV for kafka data producer where load files can be accessed
	
	* kafka-produce-volume-claim: PVC for kafka data producer where load files can be accessed
         
* Services:

	* kafka-producer-service: This service exposes kafka data producer spring boot services for variouse clients

* Deployments:

	* cassandra : deployment of kafka data producer spring boot application

### load-emitter: ###
It includes sample Jmeter jmx file for loading the data from same folder generting reports to result folder. sample bat file to run the jmeter file has been added.
From the jmeter/bin folder it can be executed as below.
		
		jmeter -n -t Kafka-Data-Load.jmx

### automation-script: ###
It includes set of bash shell scripts to deploy and remove individual components of K8s artifacts. The primary bash shell script is clean-deployment.sh.
Before running this from any server one must ensure below pre-requisites already installed

	#install python
	#sudo apt update && sudo apt install python2
	#install java
	#sudo apt-get install openjdk-8-jdk
	# CQLSH PATH needs to be added to main clean-deployment.sh file
	# download and use cqlsh from https://downloads.datastax.com/#cqlsh

#### Note: one must add $$LOADBANACER_IP} and run the script
	bash clean-deployment.sh




## 3. Kafka data producer ##
This Spring Boot project sends data to the distributed messaging server i.e. apache kafka. The data we send to the kafka topic is related to movies/series/episodes. The Kafka broker to which the data is being  sent will be the ultimate source of the data for this ETL project. This project have  the ways of data ingestion by exposing two different APIs

* Send Single Record:
  
   /produce/title/basic 
   
   /produce/title/rating

* BulkLoad from TSV file:
  
  /produce/title/basic/all
  
  /produce/title/rating/all

* Pre-requisite : 
   
   Kafka Service should be running and the Kafka Topics must be created beforehand.    


## 4. kafka-flink-consumer-cassandra-sink ##
 This flink apllication has a flink job that 
 a.  reads data from custom source i.e. kafka-broker0.
 b.  Two streams reding data from two different topics storing data into cassandra sink.
 c.  Two streams joined together on the common key and resulting joined stream forming data with few fields    combined together ending into cassandra store.
    
 * Pre-requisite:        
   
   * kafka running with topics created
   
   * cassandra running with keyspace and tables created

## 5. Steps: ##

1. create K8's Services 

    - Kafka Services
    
       		kubectl create -f ../K8s/kafka-on-kubernetes/zookeeper-service.yaml
       		kubectl create -f ../K8s/kafka-on-kubernetes/kafka-service.yaml

    - Cassandra Service
    
       		kubectl create -f ../K8s/cassandra-on-kubernetes/cassandra-service.yaml
    
    - Flink Service
    
       		kubectl create -f ../K8s/standalone-flink-job/flink-taskmanager-service.yaml
       		kubectl create -f ../K8s/standalone-flink-job/flink-jobmanager-service.yaml
    
    - Kafka Producer Service
    
    		kubectl create -f ../K8s/kafka-producers/kafka-producer-configmap.yaml
    		kubectl create -f ../K8s/kafka-producers/kafka-produce-volume.yaml
    		kubectl create -f ../K8s/kafka-producers/kafka-data-produce-volumeclaim.yaml
    		kubectl create -f ../K8s/kafka-producers/kafka-data-producer-service.yaml
    		kubectl create -f ../K8s/kafka-producers/kafka-data-producer.yaml
		
		

2. Create K8s Workloads

    - Zookeeper Deployment
    
       		kubectl create -f ../K8s/kafka-on-kubernetes/zookeeper-deploy.yaml

    - Kafka Broker Deployment
    
        	kubectl create -f ../K8s/kafka-on-kubernetes/kafka-deployment.yaml

    - Cassandra Deployment
    
       		kubectl create -f ../K8s/kafka-on-kubernetes/cassandra-deployment.yaml


3. Pre-requisite steps

    create kafka topics using console-producer CLI
    create cassandra keyspaces and tables using cqlsh CLI    
    
    * create cassandra keyspaces and tables:

       * create keyspace:
			
                	CREATE KEYSPACE result WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
                	CREATE KEYSPACE example WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
				

        * create tables:

                 		CREATE TABLE IF NOT EXISTS example.tbasic1 (
   			    		tconst text,
			    		titleType text,
			    		primaryTitle text,
			    		originalTitle text,
			    		adult boolean,
			    		startYear int,
   			    		endYear text,
			    		runtimeMinutes int,
			    		genres text,
   			    		PRIMARY KEY((titleType,genres),startYear,primaryTitle)
    		    		);

			    	CREATE TABLE IF NOT EXISTS example.trating1 (
   			    		tconst text,
			    		averageRating double,
			    		numVotes bigint,   				
   			    		PRIMARY KEY(averageRating)
    		    		);


			    	CREATE TABLE result.basicwithrating1 (
			    		titleType text,
			    		primaryTitle text,
			    		adult boolean,
			    		startYear int,
			    		genres text,
			    		averageRating double,
				    	numVotes bigint,
 				    	PRIMARY KEY ((titleType, startYear,averageRating),genres)
			    	);

4. Maven Build "DataModel"
    
    	mvn clean install -DskipTests

6. Maven Build "kafka-data-producer" and  Run  SpringBoot Application
    
   		mvn clean package -DskipTests   
		../kafka-data-producer>docker build -f Dockerfile -t title-kafka-producer:t1 .
    

5. Maven Build "kafka-flink-consumer-cassandra-sink" and create docker image from docker file
    
    	mvn clean package -DskipTests
        ../kafka-flink-consumer-cassandra-sink> docker build -f Dockerfile -t flinkwithtitles:t2 .

6. Create K8s Workloads
    
    - ConfigMap/flink-config
       
       	 	kubectl create -f ../K8s/standalone-flink-job/flink-configuration-configmap.yaml

    - TaskManager StatefulSet Deployment
        
        	kubectl create -f ../K8s/standalone-flink-job/flink-taskmanager-statefulset-titles.yaml

    - Jobmanager Standalone Job
    
        	kubectl create -f ../K8s/standalone-flink-job/kafka-source-flink-consumer-titles-job.yaml

7. Flink Dashboad with Standalone Job running will be available on ***http://${LOADBALANCER_IP}:8081/#/overview***


## 6. Execution Results ##

* Kafka Records
	
	 -  tbasic4 

	    
	    ![kafka-tbasic4](https://user-images.githubusercontent.com/25034326/177815859-ab383cb5-e63f-4dcf-b49b-bf3bddfda3ee.JPG)
	    	    
	 -  trating4 
	    
	    ![kafka-trating4](https://user-images.githubusercontent.com/25034326/177816139-f29670c1-3bb6-4c21-9f02-4e910f7a795b.JPG)


* Kafka-Data-Producer
	
	- load-emitter
	
	![jmeter-load-ingestion-aggregate-report](https://user-images.githubusercontent.com/25034326/177817195-bdef63a8-4a03-47e4-ae29-5bfe202a3416.JPG)
	
	
	![jmeter-load-ingestion-summary-report](https://user-images.githubusercontent.com/25034326/177817335-9866a35b-b0b1-43b8-b871-0f0b1bb38332.JPG)

	
	- java mission control
	
	
	![jmc-kafka-data-producer](https://user-images.githubusercontent.com/25034326/177817436-57abbf58-30cd-4e09-bd3f-b4c5d0813f5d.jpg)
	
	

* Flink Dashboard

	
	- Data Flow From streaming Job
	 
	 ![flink-job-dashboard](https://user-images.githubusercontent.com/25034326/177816285-71733af0-bd68-4ae5-be2f-2c4dba12c151.JPG)


* Cassandra Result
	
   	- tbasic1  
   	
   	 ![cassandra-tbasic1](https://user-images.githubusercontent.com/25034326/177816475-ca7617ab-7dd6-4452-997a-f428516d4603.JPG)

	 
   	- trating1 
   		 
	![cassandra-trating1](https://user-images.githubusercontent.com/25034326/177816628-f3c58eb9-4289-4430-99af-1c24965032b2.JPG)

   	- basicwithrating1
   	 
	 ![cassandra-basicwithrating1](https://user-images.githubusercontent.com/25034326/177816708-db245ebd-b073-4594-838a-dadf2082f0f6.JPG)
	 



