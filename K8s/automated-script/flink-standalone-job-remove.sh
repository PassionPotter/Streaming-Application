#!/bin/bash

# Declare an array of K8s artifacts
declare -a StringArray=(
   "job.batch/flink-jobmanager" 
   "service/flink-jobmanager" 
   "statefulset.apps/flink-taskmanager"
   "service/flink-taskmanager"
   "ConfigMap/flink-config"
   )
 
# Iterate the K8s artifacts array using for loop
for val in ${StringArray[@]}; do
   kubectl delete $val
   sleep 15s
done

