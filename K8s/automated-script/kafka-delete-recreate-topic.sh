#!/bin/bash

#KAFKA_HOME

export $1

# ZOOKEEPER
export $2

# BOOTSTRAP_SERVER
export $3

cd $KAFKA_HOME/bin
sh kafka-topics.sh  --zookeeper $ZOOKEEPER --delete --topic tbasic4
sleep 15s

sh kafka-topics.sh  --zookeeper $ZOOKEEPER --delete --topic trating4
sleep 15s

sh kafka-topics.sh --create --zookeeper $ZOOKEEPER --replication-factor 1 --partitions 1 --topic tbasic4
sleep 15s

sh kafka-topics.sh --create --zookeeper $ZOOKEEPER --replication-factor 1 --partitions 1 --topic trating4
sleep 15s
