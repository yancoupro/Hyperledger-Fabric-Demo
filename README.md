# Hyperledger


## start containers
from :
src/test/fixture/sdkintegration
run :
`bash ../../fixture/src/rundc.sh`

from IDE Run
## create user (once per user)
setupUsers.java:main

## create channel (foo by default, once per user)
com.cs.fabric.client.ConstructChannel.main

## deploy chaincode
com.cs.fabric.client.DeployChaincode.main

## test workflow
inspect, ship, present, arrival, payment