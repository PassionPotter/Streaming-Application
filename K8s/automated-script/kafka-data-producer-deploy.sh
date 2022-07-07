#!/bin/bash

# Declare an array of K8s artifacts
declare -a StringArray=(         
    "../kafka-producers/kafka-produce-volume.yaml"
    "../kafka-producers/kafka-data-produce-volumeclaim.yaml"
    "../kafka-producers/kafka-producer-configmap.yaml"
    "../kafka-producers/kafka-data-producer-service.yaml"
    "../kafka-producers/kafka-data-producer.yaml"
     )

# Iterate the K8s artifacts array using for loop
for val in ${StringArray[@]}; do
   kubectl create -f $val
   sleep 15s
done

