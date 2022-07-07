#!/bin/bash

# Declare an array of K8s artifacts
declare -a StringArray=(
   "deployment.apps/kafka-data-producer" 
   "service/kafka-producer-service" 
   "pvc/kafka-produce-volume-claim"
   "pv/kafka-produce-persistent-volume"
   "ConfigMap/kafka-producer-config"
   )
 
# Iterate the K8s artifacts array using for loop
for val in ${StringArray[@]}; do
   kubectl delete $val
   sleep 15s
done

