#!/bin/bash

# build microservice
echo "> build microservice..."
sh gradlew clean build

# check microservice is running
sh stop.sh

# run microservice
sh run.sh


