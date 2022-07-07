#!/bin/bash

# Declare an array of K8s artifacts
declare -a StringArray=(
    "../kafka-on-kubernetes/zookeeper-service.yaml" 
    "../kafka-on-kubernetes/zookeeper-deploy.yaml" 
    "../kafka-on-kubernetes/kafka-service.yaml" 
    "../kafka-on-kubernetes/kafka-deployment.yaml" )

# Iterate the K8s artifacts array using for loop
for val in ${StringArray[@]}; do
   kubectl create -f $val
   sleep 15s
done

