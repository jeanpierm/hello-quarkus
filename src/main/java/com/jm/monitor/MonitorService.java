package com.jm.monitor;

import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.*;

@ApplicationScoped
public class MonitorService {
    
    @Inject
    MonitorRepository monitorRepository;

    @Inject
    Logger logger;

    public List<Monitor> listAll() {
        var monitors = monitorRepository.listAll(Sort.by("model"));
        logger.info("Monitors obtained");
        return monitors;
    }

    public Monitor findById(Long id) {
        var monitor = monitorRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Monitor with id of " + id + " does not exist.", NOT_FOUND));
        logger.info("Monitor with id " + id + " obtained");
        return monitor;
    }

    public Monitor create(Monitor monitor) {
        if (monitor.getId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", BAD_REQUEST);
        }
        if (monitor.getPrice() == null) {
            throw new WebApplicationException("Monitor price was not set on request.", BAD_REQUEST);
        }
        Optional<Monitor> optionalMonitor = monitorRepository.findByModel(monitor.getModel());
        if (optionalMonitor.isPresent()) {
            throw new WebApplicationException("Monitor model '" + monitor.getModel() + "' already registered " +
                    "with id '" + optionalMonitor.get().getId() +"'.", CONFLICT);
        }
        monitorRepository.persist(monitor);
        logger.info("Monitor with id " + monitor.getId() + " created");

        return monitor;
    }

    public Monitor update(Monitor monitor, Long id) {
        if (monitor.getModel() == null) {
            throw new WebApplicationException("Monitor name was not set on request.", BAD_REQUEST);
        }
        if (monitor.getPrice() == null) {
            throw new WebApplicationException("Monitor price was not set on request.", BAD_REQUEST);
        }
        Monitor entity = findById(id);
        entity.setModel(monitor.getModel());
        entity.setPrice(monitor.getPrice());
        logger.info("Monitor with id " + id + " upgraded");

        return entity;
    }

    public void delete(Long id) {
        Monitor entity = findById(id);
        monitorRepository.delete(entity);
        logger.info("Monitor with id " + id + " deleted");
    }
}
