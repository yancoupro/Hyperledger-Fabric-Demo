#!/usr/bin/env bash
DOCKERFILE=docker-compose.yaml
DOCKERFILE_DEV=docker-compose-dev.override.yaml
DOCKERFILE_COUCH=docker-compose-couch.yaml
#default docker-compose -f $DOCKERFILE down ;rm -rf  /tmp/keyValStore*; rm -rf  /tmp/kvs-hfc-e2e ~/test.properties; rm -rf /var/hyperledger/*  ; docker-compose -f $DOCKERFILE up --force-recreate
docker-compose -f $DOCKERFILE -f $DOCKERFILE_DEV down ;
rm -rf  /tmp/keyValStore*; rm -rf  /tmp/kvs-hfc-e2e ~/test.properties; rm -rf /var/hyperledger/*  ;
docker-compose -f $DOCKERFILE -f $DOCKERFILE_DEV up --force-recreate
#with couchdb docker-compose -f $DOCKERFILE down ;rm -rf  /tmp/keyValStore*; rm -rf  /tmp/kvs-hfc-e2e ~/test.properties; rm -rf /var/hyperledger/*  ; docker-compose -f $DOCKERFILE -f $DOCKERFILE_COUCH up --force-recreate
#docker-compose -f $DOCKERFILE down ;rm -rf  /tmp/keyValStore*; rm -rf  /tmp/kvs-hfc-e2e ; rm -rf /var/hyperledger/*  ; docker-compose -f $DOCKERFILE up --force-recreate
