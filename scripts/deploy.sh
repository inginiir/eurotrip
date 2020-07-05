#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/euortrip.pem \
    target/eurotrip-1.0-SNAPSHOT.jar \
    ubuntu@ec2-3-8-212-161.eu-west-2.compute.amazonaws.com:/home/ubuntu/

echo 'Restart server...'

ssh -tt -i ~/.ssh/euortrip.pem ubuntu@ec2-3-8-212-161.eu-west-2.compute.amazonaws.com << EOF

nohup java -jar eurotrip-1.0-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'