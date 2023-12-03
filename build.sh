#!/bin/bash
./mvnw clean install
rm splited-app-* | true
split -b 21M target/FoolishStoreProject-0.0.1-SNAPSHOT.jar splited-app-
git add .
git commit -m 'build'
git push origin build
ssh root@14.225.254.87 'cd FoolishStoreProject && git pull origin build && cat splited-app-* > FoolishStoreProject-0.0.1-SNAPSHOT.jar && docker compose up -d --build'
