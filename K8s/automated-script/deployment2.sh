#!/bin/bash

#install python
#sudo apt update && sudo apt install python2

#install java
#sudo apt-get install openjdk-8-jdk

# download and use cqlsh from https://downloads.datastax.com/#cqlsh



# CQLSH_PATH
export $1

# CASSANDRA_HOST
export $2

#CASSANDRA_PORT
export $3

sleep 15s

bash ./cassandra-update.sh $1 $2 $3