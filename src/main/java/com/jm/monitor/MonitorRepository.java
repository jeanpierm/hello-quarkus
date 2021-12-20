package com.jm.monitor;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class MonitorRepository implements PanacheRepository<Monitor> {
    
    // custom logic
    public Monitor findByModel(String model) {
        return find("model", model).firstResult();
    }
}
