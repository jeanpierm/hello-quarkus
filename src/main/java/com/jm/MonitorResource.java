package com.jm;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/monitors")
public class MonitorResource {

    private final List<Monitor> monitors = Arrays.asList(
        new Monitor("Acer", 430),
        new Monitor("LG", 460),
        new Monitor("Gigabyte", 300)
    );

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Monitor> getMonitors() {
        return monitors;
    }
}
