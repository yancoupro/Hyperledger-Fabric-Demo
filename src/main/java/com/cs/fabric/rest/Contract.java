package com.cs.fabric.rest;


import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//import org.hyperledger.fabric.protos.common.Common;
//import org.hyperledger.fabric.protos.orderer.Ab;
//import org.hyperledger.fabric.protos.orderer.Ab.DeliverResponse;
//import org.hyperledger.fabric.protos.orderer.AtomicBroadcastGrpc;
//import org.hyperledger.fabric.sdk.exception.TransactionException;
//import org.hyperledger.fabric.sdk.helper.Config;

/**
 * Root resource (exposed at "contract" path)
 */
@Path("contract")
public class Contract {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() throws InvalidArgumentException{

        return "Got it!";
    }
}
