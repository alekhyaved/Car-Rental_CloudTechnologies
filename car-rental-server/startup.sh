#!/bin/bash

echo "Remove old logs"
rm -rf ./carrentalserver*

DATE_WITH_TIME=`date "+%Y%m%d-%H%M%S"`
echo $DATE_WITH_TIME

kill -9 $(lsof -i:8443 -t) 2> /dev/null
echo "Killed port 8443"
mvn clean install

java -jar target/car-rental-server-0.0.1-SNAPSHOT.jar > ./carrentalserver-${DATE_WITH_TIME}.log

echo "Started carrentalserver application"
