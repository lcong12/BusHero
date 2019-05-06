package com.example.bushero;

public class Bus {

    private String busId;
    private String routeId;
    private String name, capacity;
    private String timeLeft;

    public Bus(String busId, String routeId, String name, String timeLeft, String capacity) {
        this.busId = busId;
        this.routeId = routeId;
        this.name = name;
        this.capacity = capacity;
        this.timeLeft = timeLeft;
    }

    public String getBusId() {
        return busId;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getName() {
        return name;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getTimeLeft() {
        return timeLeft;
    }
}
