package com.jm.monitor;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.Optional;

@ApplicationScoped
public class MonitorRepository implements PanacheRepository<Monitor> {

    // custom logic
    public Optional<Monitor> findByModel(String model) {
        return find("model", model).firstResultOptional();
    }
}
