package com.cs.fabric;

import com.cs.fabric.client.ConstructChannel;
import com.cs.fabric.client.InvokeChainCode;
import com.cs.fabric.client.SetupUsers;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:8080/myapp/";


    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        System.out.println("FAPP");
        for(int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        String[] ctxArgs = {"peerOrgAtq"};
        String[] emptyArgs = {};

        //create channel
        ConstructChannel chan = new ConstructChannel();
        chan.main(ctxArgs);

        if(0 == 1) {

            SetupUsers setup = new SetupUsers();
            setup.main(ctxArgs);
        }


        //InvokeChainCode = new InvokeChainCode();
    }
}

