#!/usr/bin/env bash
DOCKERFILE=docker-compose.yaml
DOCKERFILE_COUCH=docker-compose-couch.yaml
docker-compose -f $DOCKERFILE down ;rm -rf  /tmp/keyValStore*; rm -rf  /tmp/kvs-hfc-e2e ~/test.properties; rm -rf /var/hyperledger/*  ; docker-compose -f $DOCKERFILE up --force-recreate
#with couchdb docker-compose -f $DOCKERFILE down ;rm -rf  /tmp/keyValStore*; rm -rf  /tmp/kvs-hfc-e2e ~/test.properties; rm -rf /var/hyperledger/*  ; docker-compose -f $DOCKERFILE -f $DOCKERFILE_COUCH up --force-recreate
#docker-compose -f $DOCKERFILE down ;rm -rf  /tmp/keyValStore*; rm -rf  /tmp/kvs-hfc-e2e ; rm -rf /var/hyperledger/*  ; docker-compose -f $DOCKERFILE up --force-recreate
