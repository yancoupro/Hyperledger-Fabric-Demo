# Hyperledger


## start containers
from :
src/test/fixture/sdkintegration
run :

** warning, rundc.sh will reset (delete) the blockchain

`bash ../../fixture/src/rundc.sh`

OR

`docker-compose -f docker-compose.yml up`


#ATQ

## for demo
`
cd /home/devops2/java/atiServer/queblock/external/yancoupro-Fabric-Demo-Base/src/test/fixture/sdkintegration;

docker-compose -f docker-compose.yaml up
#you can skip tests if you want : mvn clean install -Dmaven.test.skip=true
mvn clean install;
mvnDebug exec:java -X -Dexec.args="read"
from IDE click debug
`
if not working, you need to init first.
mvnDebug exec:java -X -Dexec.args="init"

## create channel transaction
export CHANNEL_NAME=public

`cd /home/devops2/java/atiServer/queblock/external/yancoupro-Fabric-Demo-Base/src/test/fixture/sdkintegration/e2e-2Orgs/channel`
configtxgen -profile TwoOrgsChannel -outputCreateChannelTx ./public.tx -channelID $CHANNEL_NAME

## run main with args
mvn clean install;
mvnDebug exec:java -X -Dexec.args="init"

mvnDebug exec:java -X -Dexec.args="create_users"
from IDE click debug
mvnDebug exec:java -X -Dexec.args="create_channel"
from IDE click debug
mvnDebug exec:java -X -Dexec.args="deploy_chaincode"
from IDE click debug
mvnDebug exec:java -X -Dexec.args="invoke"
from IDE click debug
mvnDebug exec:java -X -Dexec.args="read"
from IDE click debug

##connect to client ( never worked)
docker exec -it peer1.org2.example.com bash
export CHANNEL_NAME=public
peer chaincode query -C $CHANNEL_NAME -n public_chaincode -c '{"Args":["readAnimal","124000100005"]}'


## other commands (directly using hyperledger fabric's tools) ##
../bin/configtxgen -profile AtqOrgsOrdererGenesis -outputBlock ./channel-artifacts/genesis.block
../bin/configtxgen -profile AtqOrgsChannel -outputCreateChannelTx ./channel-artifacts/channel.tx -channelID $CHANNEL_NAME
../bin/configtxgen -profile AtqOrgsChannel -outputAnchorPeersUpdate ./channel-artifacts/AtqMSPanchors.tx -channelID $CHANNEL_NAME -asOrg OrgAtqMSP
../bin/configtxgen -profile AtqOrgsChannel -outputAnchorPeersUpdate ./channel-artifacts/AtqMSPanchors.tx -channelID $CHANNEL_NAME -asOrg OrgAbattoirMSP

CHANNEL_NAME=$CHANNEL_NAME TIMEOUT=12000 docker-compose -f docker-compose-cli.yaml up -d
peer channel create -o orderer.example.com:7050 -c $CHANNEL_NAME -f ./channel-artifacts/channel.tx --tls $CORE_PEER_TLS_ENABLED --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem


CORE_PEER_MSPCONFIGPATH=
CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/atq.example.com/peers/peer0.atq.example.com/tls/ca.crt

CORE_PEER_ADDRESS=peer0.atq.example.com:7051
CORE_PEER_LOCALMSPID="OrgAtqMSP"
CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/atq.example.com/users/Admin@atq.example.com/msp
CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/atq.example.com/peers/peer0.atq.example.com/tls/ca.crt



CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
CORE_PEER_ADDRESS=peer1.org1.example.com:7053
CORE_PEER_LOCALMSPID="Org1MSP"
CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer1.org1.example.com/tls/ca.crt
