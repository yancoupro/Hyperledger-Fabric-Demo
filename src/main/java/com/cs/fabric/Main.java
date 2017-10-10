package com.cs.fabric;


import com.cs.fabric.client.DeployAtqChaincode;
import com.cs.fabric.client.InvokePublicChaincode;
import com.cs.fabric.client.PublicChannel;
import com.cs.fabric.client.SetupUsers;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;


import com.cs.fabric.client.utils.ClientHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Main class.
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in atq.queblock package
        final ResourceConfig rc = new ResourceConfig().packages("com.cs.fabric.rest");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void startServer(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }

    public static final Log logger = LogFactory.getLog(Main.class);

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {

        System.out.println("FAPP");
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }

        String[] ctxArgs = {""};
        String[] privateArgs = {""};
        logger.info("main is started");

        if (args[0].equals("create_users") || args[0].equals("init")) {

            SetupUsers setup = new SetupUsers();
            setup.main(ctxArgs);
        }


        if (args[0].equals("create_channel") || args[0].equals("init")) {
            logger.info("creating public channel");

            //create channel public
            PublicChannel chan = new PublicChannel();
            chan.main(ctxArgs);
        }

        if (args[0].equals("deploy_public") || args[0].equals("init")) {
            logger.info("deploy of public cc ");
            privateArgs[0] = "public";
            DeployAtqChaincode deployChaincode = new DeployAtqChaincode();
            deployChaincode.main(privateArgs);

        }

        /*
        if (args[0].equals("deploy_art") || args[0].equals("init")) {
            logger.info("deploy of art cc ");
            privateArgs[0] = "art";
            DeployAtqChaincode deployChaincode = new DeployAtqChaincode();
            deployChaincode.main(privateArgs);
        }
        */

        if (args[0].equals("server")) {
            startServer(args);
        }

        if (args[0].equals("invoke") || args[0].equals("read")) {
            logger.info("chaincode : " + args[1] + " mode : " + args[0]);

            if (args[0].equals("invoke")) {
                if("art_cc".equals(args[1])) {
                    ctxArgs = new String[]{"art", "iPostUser", "200", "USER", "TRD", "Longueuil",
                            "kitchen", "SlaughterHouse", "2017-09-28"};

                }
                else {
                    //   0       1       2			  3             4              5         6    7
                    //   ID      Age    Birth Place  Feeding Place Slaughter Place Slaughter Date Certifications
                    ctxArgs = new String[]{"public", "initAnimal", args[2], "3", "Longueuil",
                            "kitchen", "SlaughterHouse", "2017-09-28"};
                }
            } else {
                if("art_cc".equals(args[1])) {
                    ctxArgs = new String[]{"art", "GetUser", "100"};

                }
                else{
                    //   0       1       2			  3             4              5         6    7
                    //   ID      Age    Birth Place  Feeding Place Slaughter Place Slaughter Date Certifications
                    ctxArgs = new String[]{"public", "readAnimal", "124000100005"};
                }

            }
        }

            InvokePublicChaincode icc = new InvokePublicChaincode();
            icc.main(ctxArgs);

        }



}

