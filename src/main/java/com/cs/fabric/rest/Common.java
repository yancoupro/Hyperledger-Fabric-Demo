package com.cs.fabric.rest;


import com.cs.fabric.Main;
import com.cs.fabric.client.InvokePublicChaincode;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Common {

    protected Response getFromCC(String[] ctxArgs) throws InvalidArgumentException {

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

    protected String setToCC(String[] ctxArgs) throws InvalidArgumentException {

        Main.logger.info("args to cc" + Arrays.toString(ctxArgs));
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
        return "SET it!" + "xx" + "\n" + txResult;
    }

    protected void logPost(final MultivaluedMap<String, String> map) throws InvalidArgumentException {

            Main.logger.info("POST Parameters:");

            for (String key : map.keySet()) {
                Main.logger.info("Key: " + key);
                Main.logger.info("Val: " + map.getFirst(key));
            }

    }
}
