#!/bin/bash

# CQLSH_PATH
export $1

# CASSANDRA_HOST
export $2

#CASSANDRA_PORT
export $3

cassandra_user="cassandra"
cassandra_pass="cassandra"

cd $CQLSH_PATH/bin

sleep 5s 
for i in {1..10}
do
    bash ./cqlsh $CASSANDRA_HOST $CASSANDRA_PORT -u $cassandra_user -p $cassandra_pass -f /mnt/e/Data/Notes/learning/docker-images/automated-script/keyspace_and_tables.cql #> /mnt/e/Data/Notes/learning/docker-images/automated-script/cql_output.out  2>&1
    result=$?
    echo $result

    if [[ $result == 0 ]]
    then
        echo "successful";
        break;
    else
        echo "failed";
    fi
    sleep 15s
done


