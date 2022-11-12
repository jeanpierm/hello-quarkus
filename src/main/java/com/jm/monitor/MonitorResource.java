package com.jm.monitor;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jm.config.ValidationGroups;

@Path("/monitors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MonitorResource {

    private static final Logger LOGGER = Logger.getLogger(MonitorResource.class.getName());

    @Inject
    MonitorService monitorService;

    @Inject
    Validator validator;

    @Inject
    ObjectMapper objectMapper;

    @GET
    public List<Monitor> get() {
        return monitorService.listAll();
    }

    @GET
    @Path("/models")
    public Response getModels() {
        LOGGER.info("Obteniendo modelos...");
        Map<String, Object> res = Map.of("models", monitorService.getAllModels());
        LOGGER.info("Modelos obtenidos... " + res.toString());
        return Response.ok(res).build();
    }

    @GET
    @Path("{id}")
    public Monitor findById(@PathParam Long id) {
        return monitorService.findById(id);
    }

    @POST
    @Transactional
    public Response create(@Valid @ConvertGroup(to = ValidationGroups.Post.class) MonitorRequestDto monitor) {
        monitorService.create(monitor);
        return Response.ok(monitor).status(CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Monitor update(@PathParam Long id,
            @Valid @ConvertGroup(to = ValidationGroups.Put.class) MonitorRequestDto monitor) {
        return monitorService.update(monitor, id);
    }

    @PATCH
    @Path("{id}")
    @Transactional
    public Monitor patch(@PathParam Long id,
            @Valid @ConvertGroup(to = ValidationGroups.Patch.class) MonitorRequestDto monitor) {
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
