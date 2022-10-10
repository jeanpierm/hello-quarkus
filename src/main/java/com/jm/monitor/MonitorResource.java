package com.jm.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.Status;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@Path("/monitors")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MonitorResource {

    private static final Logger LOGGER = Logger.getLogger(MonitorResource.class.getName());

    @Inject
    MonitorService monitorService;

    @GET
    public List<Monitor> get() {
        return monitorService.listAll();
    }

    @GET
    @Path("{id}")
    public Monitor findById(@PathParam Long id) {
        return monitorService.findById(id);
    }

    @POST
    @Transactional
    public Response create(Monitor monitor) {
        monitorService.create(monitor);

        return Response.ok(monitor).status(CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Monitor update(@PathParam Long id, Monitor monitor) {
        return monitorService.update(monitor, id);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response remove(@PathParam Long id) {
        monitorService.delete(id);
        return Response.status(NO_CONTENT).build();
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Failed to handle request", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}
