package com.cs.fabric.rest;

import com.cs.fabric.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

public class UserTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        //c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    public void _testGetIt() {
        String responseMsg = target.path("animal/124000100005").request().get(String.class);
        String x = new String("{\"animalId\":\"124000100005\",\"AgeInMonths\":3,\"PlaceOfBirth\":\"longueuil\",\"PlaceOfFeeding\":\"kitchen\",\"PlaceOfSlaughter\":\"slaughterhouse\",\"DateOfSlaughter\":\"2017-09-28T00:00:00Z\",\"Certifications\":\"\"}");
        assertEquals(x, responseMsg);
    }


    @Test
    public void TestSetIt() {
        Form form = new Form();
        form.param("userId", "300");
        form.param("objectType", "USER");
        form.param("name", "Jonh Doe");
        form.param("role", "TR");
        form.param("address", "555 Roland-Therrien, Longueuil, QC");
        form.param("email", "qatests_atq@atq.qc.ca");
        form.param("phone", "514-555-5555");
        form.param("bank", "BMO");
        form.param("account", "111111");
        form.param("routing", "222222");

        Response response = target.path("user").request().post(
                Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE)
        );

        String responseMsg = response.getEntity().toString();
        Main.logger.info("set response : " + responseMsg);
        String x = new String("{\"animalId\":\"124000100007\",\"AgeInMonths\":3,\"PlaceOfBirth\":\"longueuil\",\"PlaceOfFeeding\":\"kitchen\",\"PlaceOfSlaughter\":\"slaughterhouse\",\"DateOfSlaughter\":\"2017-09-28T00:00:00Z\",\"Certifications\":\"\"}");
        assertEquals(200, response.getStatus());

    }

    /**
     * test identifier added
     */
//    @Test
    public void testGet300() {
        String responseMsg = target.path("user/300").request().get(String.class);
        String x = new String("[{\"UserID\":\"300\",\"RecType\":\"USER\",\"Name\":\"Jonh Doe\",\"UserType\":\"TR\",\"Address\":\"555 Roland-Therrien, Longueuil, QC\",\"Phone\":\"514-555-5555\",\"Email\":\"qatests_atq@atq.qc.ca\",\"Bank\":\"BMO\",\"AccountNo\":\"111111\",\"RoutingNo\":\"222222\",\"Timestamp\"");
        assertThat(responseMsg, containsString(x));
    }
}
