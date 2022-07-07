#!/bin/bash

#install python
#sudo apt update && sudo apt install python2

#install java
#sudo apt-get install openjdk-8-jdk

# download and use cqlsh from https://downloads.datastax.com/#cqlsh

bash ./kafka-data-producer-remove.sh
sleep 15s

bash ./flink-standalone-job-remove.sh

sleep 15s

bash ./kafka-remove.sh
sleep 15s

bash ./cassandra-remove.sh
sleep 15s 
