#!/bin/bash

# Declare an array of K8s artifacts
declare -a StringArray=(     
    "../flink-jobs/flink-configuration-configmap.yaml" 
    "../flink-jobs/flink-taskmanager-service.yaml"
    "../flink-jobs/flink-taskmanager-statefulset-titles.yaml"
    "../flink-jobs/flink-jobmanager-service.yaml"
    "../flink-jobs/kafka-source-flink-consumer-titles-job.yaml"
     )

# Iterate the K8s artifacts array using for loop
for val in ${StringArray[@]}; do
   kubectl create -f $val
   sleep 15s
done

