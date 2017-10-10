#!/usr/bin/env bash
export CHANNEL_NAME=public

cd /home/devops2/java/atiServer/queblock/external/yancoupro-Fabric-Demo-Base/src/test/fixture/sdkintegration/e2e-2Orgs/channel
configtxgen -profile TwoOrgsChannel -outputCreateChannelTx ./$CHANNEL_NAME.tx -channelID $CHANNEL_NAME
