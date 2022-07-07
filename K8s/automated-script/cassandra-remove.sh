#!/bin/bash

# Declare an array of K8s artifacts
declare -a StringArray=("deployment.apps/cassandra" "service/cassandra-service" )
 
# Iterate the K8s artifacts array using for loop
for val in ${StringArray[@]}; do
   kubectl delete $val
   sleep 15s
done

