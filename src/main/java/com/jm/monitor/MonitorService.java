package com.jm.monitor;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class MonitorService {
    
    @Inject
    MonitorRepository monitorRepository;

    public List<Monitor> listAll() {
        return monitorRepository.listAll(Sort.by("model"));
    }

    public Monitor findById(Long id) {
        return monitorRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Monitor with id of " + id + " does not exist.", 404));
    }

    public Monitor create(Monitor monitor) {
        if (monitor.getId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        if (monitor.getPrice() == null) {
            throw new WebApplicationException("Monitor price was not set on request.", 422);
        }
        monitorRepository.persist(monitor);

        return monitor;
    }

    public Monitor update(Monitor monitor, Long id) {
        if (monitor.getModel() == null) {
            throw new WebApplicationException("Monitor name was not set on request.", 422);
        }
        if (monitor.getPrice() == null) {
            throw new WebApplicationException("Monitor price was not set on request.", 422);
        }
        Monitor entity = findById(id);
        entity.setModel(monitor.getModel());
        entity.setPrice(monitor.getPrice());

        return entity;
    }

    public void delete(Long id) {
        Monitor entity = findById(id);
        monitorRepository.delete(entity);
    }
}
