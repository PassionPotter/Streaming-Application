#!/bin/bash
#1. add taskmanager
#2. add jobmanager
#3. add producer

# Declare an array of K8s artifacts
declare -a flinkRedeploy=(     
    "../flink-jobs/flink-taskmanager-statefulset-titles.yaml"
    "../flink-jobs/kafka-source-flink-consumer-titles-job.yaml"
    "../kafka-producers/kafka-data-producer.yaml"
     )

# Iterate the K8s artifacts array using for loop
for val in ${flinkRedeploy[@]}; do
   kubectl create -f $val
   sleep 15s
done