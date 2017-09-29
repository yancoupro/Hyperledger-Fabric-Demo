package com.cs.fabric.rest;

import com.cs.fabric.Main;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AnimalTest {

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



    public void TestSetIt() {
        Form form = new Form();
        form.param("animalId", "124000100009");
        form.param("ageInMonths", "9");
        form.param("placeOfBirth", "Saint-Lambert");
        form.param("placeOfFeeding", "Laval");
        form.param("placeOfSlaughter", "La Prairie");
        form.param("DateOfSlaughter", "2017-09-29");

//        String post = new String("{\"animalId\":\"124000100007\",\"AgeInMonths\":7,\"PlaceOfBirth\":\"Montreal\",\"PlaceOfFeeding\":\"Barn\",\"PlaceOfSlaughter\":\"ABA0001\",\"DateOfSlaughter\":\"2017-09-28T00:00:00Z\",\"Certifications\":\"some-good-animal\"}");

        Response response= target.path("animal").request().post(
                Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE)
        );

        //String responseMsg = target.path("animal/").request().post(String.class);
        String x = new String("{\"animalId\":\"124000100007\",\"AgeInMonths\":3,\"PlaceOfBirth\":\"longueuil\",\"PlaceOfFeeding\":\"kitchen\",\"PlaceOfSlaughter\":\"slaughterhouse\",\"DateOfSlaughter\":\"2017-09-28T00:00:00Z\",\"Certifications\":\"\"}");
        assertEquals(x, response.toString());

    }

    /**
     * test identifier added
     */
    @Test
    public void testGet124000100009() {
        String responseMsg = target.path("animal/124000100009").request().get(String.class);
        String x = new String("{\"animalId\":\"124000100009\",\"AgeInMonths\":9,\"PlaceOfBirth\":\"saint-lambert\",\"PlaceOfFeeding\":\"laval\",\"PlaceOfSlaughter\":\"la prairie\",\"DateOfSlaughter\":\"2017-09-29T00:00:00Z\",\"Certifications\":\"\"}");
        assertEquals(x, responseMsg);
    }
}
