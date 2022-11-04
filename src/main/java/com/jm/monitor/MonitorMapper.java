package com.jm.monitor;

public class MonitorMapper {

    public static Monitor requestToMonitor(MonitorRequestDto request) {
        Monitor monitor = new Monitor();
        monitor.setModel(request.getModel());
        monitor.setPrice(request.getPrice());
        return monitor;
    }
}
