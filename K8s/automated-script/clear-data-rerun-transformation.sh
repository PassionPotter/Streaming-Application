#!/bin/bash

bash remove-producer-flink-consumer.sh

#2. clear cassandra data
bash clear-cassandra-data.sh CQLSH_PATH=/mnt/e/Softwares/apache-cassandra-3.11.13/ CASSANDRA_HOST=${LOADBALANCER_IP} CASSANDRA_PORT=9042
sleep 15s

#3. delete and recreate topics
bash kafka-delete-recreate-topic.sh KAFKA_HOME=/mnt/e/Softwares/kafka_2.13-2.8.1 ZOOKEEPER=${LOADBALANCER_IP}:2181 BOOTSTRAP_SERVER=${LOADBALANCER_IP}:9092
sleep 15s

bash add-producer-flink-consumer.sh

