#!/bin/bash

# Declare an array of K8s artifacts
declare -a flinkDeployed=(
   "job.batch/flink-jobmanager" 
   "statefulset.apps/flink-taskmanager"
   )
 
# Iterate the K8s artifacts array using for loop
for val in ${flinkDeployed[@]}; do
   kubectl delete $val
   sleep 15s
done

bash clear-cassandra-data.sh CQLSH_PATH=/mnt/e/Softwares/apache-cassandra-3.11.13/ CASSANDRA_HOST=${LOADBALANCER_IP} CASSANDRA_PORT=9042
sleep 15s


# Declare an array of K8s artifacts
declare -a flinkRedeploy=(     
    "../flink-jobs/flink-taskmanager-statefulset-titles.yaml"
    "../flink-jobs/kafka-source-flink-consumer-titles-job.yaml"
     )

# Iterate the K8s artifacts array using for loop
for val in ${flinkRedeploy[@]}; do
   kubectl create -f $val
   sleep 15s
done

