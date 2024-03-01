#!/bin/bash
./mvnw clean install
rm splited-app-* | true
split -b 21M target/FoolishStoreProject-0.0.1-SNAPSHOT.jar splited-app-
git add .
git commit -m 'build'
git push origin build
ssh root@103.200.20.153 'cd FoolishStoreProject && git pull origin build && cat splited-app-* > FoolishStoreProject-0.0.1-SNAPSHOT.jar && docker rm -f spring-boot-container && docker compose up -d --build && rm -r splited-app-*'


java -jar target/FoolishStoreProject-0.0.1-SNAPSHOT.jar