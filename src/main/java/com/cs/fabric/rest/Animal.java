package com.cs.fabric.rest;


import com.cs.fabric.Main;
import com.cs.fabric.client.InvokePublicChaincode;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Enumeration;

//import org.hyperledger.fabric.protos.common.Common;
//import org.hyperledger.fabric.protos.orderer.Ab;
//import org.hyperledger.fabric.protos.orderer.Ab.DeliverResponse;
//import org.hyperledger.fabric.protos.orderer.AtomicBroadcastGrpc;
//import org.hyperledger.fabric.sdk.exception.TransactionException;
//import org.hyperledger.fabric.sdk.helper.Config;

/**
 * Root resource (exposed at "contract" path)
 */
@Path("/animal")
public class Animal {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("{identifier}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt(@PathParam("identifier") String identifier) throws InvalidArgumentException {

        String[] ctxArgs = new String[]{"readAnimal", identifier};
        String txResult = "";

        InvokePublicChaincode icc = new InvokePublicChaincode();
        try {
            txResult = icc.main(ctxArgs);
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.status(200).entity(txResult).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String setIt(final MultivaluedMap<String, String> map) throws InvalidArgumentException {
        StringBuilder s = new StringBuilder();
        s.append("SET IT INITIATED");

        //   0       1       2			  3             4              5         6    7
        //   ID      Age    Birth Place  Feeding Place Slaughter Place Slaughter Date Certifications
        String[] ctxArgs = new String[]{"initAnimal",
                map.getFirst("animalId"),
                map.getFirst("ageInMonths"),
                map.getFirst("placeOfBirth"),
                map.getFirst("placeOfFeeding"),
                map.getFirst("placeOfSlaughter"),
                map.getFirst("DateOfSlaughter")
        };

        Main.logger.info("args to cc" + Arrays.toString(ctxArgs));
        try {

            Main.logger.info("POST Parameters:");


//            MultivaluedMap<String, String> map = ui.get();

            for (String key : map.keySet()) {
                Main.logger.info("Key: " + key);
                Main.logger.info("Val: " + map.getFirst(key));
                s.append("Key: " + key);
                s.append("Val: " + map.getFirst(key));

            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build().toString();
        }
        String txResult = "";

        InvokePublicChaincode icc = new InvokePublicChaincode();
        try {
            txResult = icc.main(ctxArgs);
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Got it!" + "xx" + "\n" + txResult;
    }
}
