#!/bin/bash

bash ./remove-deployment.sh 

bash ./kafka-deploy.sh
sleep 15s

bash ./cassandra-deploy.sh
sleep 15s

bash cassandra-update.sh CQLSH_PATH=/mnt/e/Softwares/apache-cassandra-3.11.13/ CASSANDRA_HOST=${LOADBALANCER_IP} CASSANDRA_PORT=9042
sleep 15s

bash kafka-update.sh KAFKA_HOME=/mnt/e/Softwares/kafka_2.13-2.8.1 ZOOKEEPER=${LOADBALANCER_IP}:2181 BOOTSTRAP_SERVER=${LOADBALANCER_IP}:9092
sleep 15s

bash flink-standalone-job-deploy.sh
sleep 15s

bash kafka-data-producer-deploy.sh
sleep 15s

