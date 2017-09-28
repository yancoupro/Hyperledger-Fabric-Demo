package com.cs.fabric;



import com.cs.fabric.client.DeployPublicChaincode;
import com.cs.fabric.client.InvokePublicChaincode;
import com.cs.fabric.client.PublicChannel;
import com.cs.fabric.client.SetupUsers;

import com.cs.fabric.client.utils.ClientHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import java.io.IOException;

/**
 * Main class.
 *
 */
public class Main {

    private static final Log logger = LogFactory.getLog(Main.class);

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

        String[] ctxArgs = {""};
        String[] emptyArgs = {};
        logger.info("main is started");

        if(0 == 1) {

            SetupUsers setup = new SetupUsers();
            setup.main(ctxArgs);
        }


        if(0 == 1) {
            logger.info("creating public channel");

            //create channel public
            PublicChannel chan = new PublicChannel();
            chan.main(ctxArgs);
        }


        if(args[0].equals("deploy")) {
            logger.info("deploy of public cc version " + ClientHelper.CHAIN_CODE_VERSION);
            DeployPublicChaincode publicChaincode = new DeployPublicChaincode();
            publicChaincode.main(ctxArgs);

        }

        if (args[0].equals("invoke") || args[0].equals("read")){
            logger.info("invoking chaincode : " + args[0]);

            if(args[0].equals("invoke")) {
                //   0       1       2			  3             4              5         6    7
                //   ID      Age    Birth Place  Feeding Place Slaughter Place Slaughter Date Certifications
                ctxArgs  = new String[] { "initAnimal", "124000100005", "3", "Longueuil",
                        "kitchen", "SlaughterHouse", "2017-09-28" };
            }
            else {
                //   0       1       2			  3             4              5         6    7
                //   ID      Age    Birth Place  Feeding Place Slaughter Place Slaughter Date Certifications
                ctxArgs  = new String[] { "readAnimal", "124000100005" };

            }

            InvokePublicChaincode icc = new InvokePublicChaincode();
            icc.main(ctxArgs);

        }


    }
}

