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

#ATQ

## create channel transaction
export CHANNEL_NAME=public

`cd /home/devops2/java/atiServer/queblock/external/yancoupro-Fabric-Demo-Base/src/test/fixture/sdkintegration/e2e-2Orgs/channel`
configtxgen -profile TwoOrgsChannel -outputCreateChannelTx .//public.tx -channelID $CHANNEL_NAME

## run main with pub channel option

