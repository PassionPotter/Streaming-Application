#!/bin/bash

# Declare an array of K8s artifacts
declare -a StringArray=(     
    "../cassandra-on-kubernetes/cassandra-service.yaml" 
    "../cassandra-on-kubernetes/cassandra-deployment.yaml" )

# Iterate the K8s artifacts array using for loop
for val in ${StringArray[@]}; do
   kubectl create -f $val
   sleep 15s
done

