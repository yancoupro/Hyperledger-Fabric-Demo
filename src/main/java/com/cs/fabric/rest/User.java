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

//import org.hyperledger.fabric.protos.common.Common;
//import org.hyperledger.fabric.protos.orderer.Ab;
//import org.hyperledger.fabric.protos.orderer.Ab.DeliverResponse;
//import org.hyperledger.fabric.protos.orderer.AtomicBroadcastGrpc;
//import org.hyperledger.fabric.sdk.exception.TransactionException;
//import org.hyperledger.fabric.sdk.helper.Config;

/**
 * Root resource (exposed at "contract" path)
 */
@Path("/user")
public class User extends Common{

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

        String[] ctxArgs = new String[]{"art", "qGetUser", identifier};
        return this.getFromCC(ctxArgs);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String setIt(final MultivaluedMap<String, String> map) throws InvalidArgumentException {
        StringBuilder s = new StringBuilder();
        s.append("SET IT INITIATED");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = Calendar.getInstance().getTime();
        String creationDate = df.format(today);

        // ./peer chaincode invoke -l golang -n mycc -c '
        // "Function": "PostUser",
        // "Args":[
        //0 "100",
        //1 "USER",
        //2 "Ashley Hart",
        //3 "TRD",
        //4 "Morrisville Parkway,  #216,  Morrisville,  NC 27560",
        // "9198063535",
        // "ashley@itpeople.com",
        // "SUNTRUST",
        // "00017102345",
        //9 "0234678"]'

        String[] ctxArgs = new String[]{"art", "iPostUser",
                map.getFirst("userId"),
                map.getFirst("objectType"),
                map.getFirst("name"),
                map.getFirst("role"),
                map.getFirst("address"),
                map.getFirst("phone"),
                map.getFirst("email"),
                map.getFirst("bank"),
                map.getFirst("account"),
                map.getFirst("routing"),
                creationDate
        };

        Main.logger.info("args to cc" + Arrays.toString(ctxArgs));
        this.logPost(map);
        return this.setToCC(ctxArgs);
    }
}
