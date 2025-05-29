package org.example.automatization_1_1;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/give-me-surname")
public class SurnameResource {
    @GET
    @Path("/my-surname")
    @Produces("text/plain")
    public String surname() {
        return "Hryshchenko";
    }
}