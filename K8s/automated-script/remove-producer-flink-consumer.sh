#!/bin/bash
# Declare an array of K8s artifacts
#1. remove producer
#2. remove jobmanager
#3. remove taskmanager
declare -a flinkDeployed=( 
   "deployment.apps/kafka-data-producer"   
   "job.batch/flink-jobmanager" 
   "statefulset.apps/flink-taskmanager"
   )
 
# Iterate the K8s artifacts array using for loop
for val in ${flinkDeployed[@]}; do
   kubectl delete $val
   sleep 15s
done