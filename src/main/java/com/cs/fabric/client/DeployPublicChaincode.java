package com.cs.fabric.client;

import com.cs.fabric.client.utils.ClientHelper;
import com.cs.fabric.sdk.utils.ClientConfig;
import com.cs.fabric.sdkintegration.SampleOrg;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.sdk.*;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DeployPublicChaincode {

	private static final ClientHelper clientHelper = new ClientHelper();
	private static final ClientConfig clientConfig = ClientConfig.getConfig();
	private static final String FOO_CHANNEL_NAME = "public";
	private static final String TEST_FIXTURES_PATH = "src/test/fixture";

	private static final Log logger = LogFactory.getLog(DeployPublicChaincode.class);

	public static void main(String[] args) throws Exception {

		// Get Org1
		SampleOrg sampleOrg = clientHelper.getSamleOrg();

		// Create instance of client.
		HFClient client = clientHelper.getHFClient();

		client.setUserContext(sampleOrg.getPeerAdmin());

		Channel channel = clientHelper.getChannel();
		logger.info("Get Channel " + FOO_CHANNEL_NAME);

		final ChaincodeID chaincodeID = clientHelper.getChaincodeID();
		Collection<ProposalResponse> responses;
		Collection<ProposalResponse> successful = new LinkedList<>();
		Collection<ProposalResponse> failed = new LinkedList<>();

		// chainCodeID =
		// ChainCodeID.newBuilder().setName(CHAIN_CODE_NAME).setVersion(ClientHelper.CHAIN_CODE_VERSION)
		// .setPath(CHAIN_CODE_PATH).build();
		// logger.info("Chain Code Name:" + chainCodeID.getName() + "; Version:"
		// + chainCodeID.getVersion() + "; Path:"
		// + chainCodeID.getPath());

		////////////////////////////
		// Install Proposal Request
		//
		logger.info("Creating install proposal");

		InstallProposalRequest installProposalRequest = client.newInstallProposalRequest();
		installProposalRequest.setChaincodeID(chaincodeID);
		//// For GO language and serving just a single user, chaincodeSource is
		//// mostly likely the users GOPATH
		installProposalRequest
				.setChaincodeSourceLocation(new File(TEST_FIXTURES_PATH + "/sdkintegration/gocc/golocal"));
		installProposalRequest.setChaincodeVersion(ClientHelper.CHAIN_CODE_VERSION);

		logger.info("Sending install proposal");
		////////////////////////////
		// only a client from the same org as the peer can issue an install
		//////////////////////////// request
		int numInstallProposal = 0;
		// Set<String> orgs = orgPeers.keySet();
		// for (SampleOrg org : testSampleOrgs) {

		Set<Peer> peersFromOrg = sampleOrg.getPeers();
		numInstallProposal = numInstallProposal + peersFromOrg.size();
		responses = client.sendInstallProposal(installProposalRequest, channel.getPeers());

		for (ProposalResponse response : responses) {
			if (response.getStatus() == ProposalResponse.Status.SUCCESS) {
				logger.info("Successful install proposal response Txid: " + response.getTransactionID() + " from peer"
						+ response.getPeer().getName());
				successful.add(response);
			} else {
				failed.add(response);
			}
		}

		SDKUtils.getProposalConsistencySets(responses);
		logger.info("Received " + numInstallProposal + " install proposal responses. Successful+verified: "
				+ successful.size() + ". Failed: " + failed.size());

		if (failed.size() > 0) {
			ProposalResponse first = failed.iterator().next();
			logger.info("Not enough endorsers for install :" + successful.size() + ".  " + first.getMessage());
		}

		///////////////
		//// Instantiate chain code.
		InstantiateProposalRequest instantiateProposalRequest = client.newInstantiationProposalRequest();
		instantiateProposalRequest.setProposalWaitTime(60000);
		instantiateProposalRequest.setChaincodeID(chaincodeID);
		instantiateProposalRequest.setFcn("init");
		instantiateProposalRequest.setArgs(new String[] {});
		Map<String, byte[]> tm = new HashMap<>();
		tm.put("HyperLedgerFabric", "InstantiateProposalRequest:JavaSDK".getBytes(UTF_8));
		tm.put("method", "InstantiateProposalRequest".getBytes(UTF_8));
		instantiateProposalRequest.setTransientMap(tm);

		/*
		 * policy OR(Org1MSP.member, Org2MSP.member) meaning 1 signature from
		 * someone in either Org1 or Org2 See README.md Chaincode endorsement
		 * policies section for more details.
		 */
		ChaincodeEndorsementPolicy chaincodeEndorsementPolicy = new ChaincodeEndorsementPolicy();
		chaincodeEndorsementPolicy
				.fromYamlFile(new File(TEST_FIXTURES_PATH + "/sdkintegration/chaincodeendorsementpolicy.yaml"));
		instantiateProposalRequest.setChaincodeEndorsementPolicy(chaincodeEndorsementPolicy);

		logger.info("Sending instantiateProposalRequest to all peers without arguments");
		successful.clear();
		failed.clear();

		// client.setUserContext(sampleOrg.getAdmin());
		responses = channel.sendInstantiationProposal(instantiateProposalRequest, channel.getPeers());
		for (ProposalResponse response : responses) {
			if (response.isVerified() && response.getStatus() == ProposalResponse.Status.SUCCESS) {
				successful.add(response);
				logger.info("Succesful instantiate proposal response Txid: " + response.getTransactionID()
						+ " from peer " + response.getPeer().getName());
			} else {
				failed.add(response);
			}
		}
		logger.info("Received " + responses.size() + " instantiate proposal responses. Successful+verified: "
				+ successful.size() + ". Failed: " + failed.size());
		if (failed.size() > 0) {
			ProposalResponse first = failed.iterator().next();
			logger.info("Not enough endorsers for instantiate :" + successful.size() + "endorser failed with "
					+ first.getMessage() + ". Was verified:" + first.isVerified());
		}

		///////////////
		/// Send instantiate transaction to orderer
		logger.info("Sending instantiateTransaction to orderer without arguments");
		channel.sendTransaction(successful, channel.getOrderers()).thenApply(transactionEvent -> {

			if (transactionEvent.isValid()) {
				logger.info("Finished transaction with transaction id " + transactionEvent.getTransactionID());
			} else {
				logger.info("Failed to sending instatiate transaction to orderer!");
			}
			// chain.shutdown(true);
			return null;
		}).get(clientConfig.getTransactionWaitTime(), TimeUnit.SECONDS);
	}
}