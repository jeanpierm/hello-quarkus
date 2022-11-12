package com.jm.monitor;

import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    
    public List<String> getAllModels() {
    	var monitorNames = monitorRepository.listAll(Sort.by("model")).stream()
    			.map(monitor -> monitor.getModel())
    			.collect(Collectors.toList());
    	return monitorNames;
    }

    public Monitor create(MonitorRequestDto monitorDto) {
        Optional<Monitor> optionalMonitor = monitorRepository.findByModel(monitorDto.getModel());
        if (optionalMonitor.isPresent()) {
            throw new WebApplicationException("Monitor model '" + monitorDto.getModel() + "' already registered " +
                    "with id '" + optionalMonitor.get().getId() +"'.", CONFLICT);
        }
        Monitor monitor = MonitorMapper.requestToMonitor(monitorDto);
        monitorRepository.persist(monitor);
        logger.info("Monitor with id " + monitor.getId() + " created");

        return monitor;
    }

    public Monitor update(MonitorRequestDto monitor, Long id) {
        Monitor entity = findById(id);
        if (monitor.getModel() != null) {
            entity.setModel(monitor.getModel());
        }
        if (monitor.getPrice() != null) {
            entity.setPrice(monitor.getPrice());
        }
        logger.info("Monitor with id " + id + " upgraded");

        return entity;
    }

    public void delete(Long id) {
        Monitor entity = findById(id);
        monitorRepository.delete(entity);
        logger.info("Monitor with id " + id + " deleted");
    }
}
