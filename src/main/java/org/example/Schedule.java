package org.example;

import java.time.LocalDateTime;

public class Schedule {
    private int id;
    private int routeId;
    private int busId;
    private int driverId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    // Конструктор для створення (без id)
    public Schedule(int routeId, int busId, int driverId, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.routeId = routeId;
        this.busId = busId;
        this.driverId = driverId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // Конструктор з id (для update / select)
    public Schedule(int id, int routeId, int busId, int driverId, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.routeId = routeId;
        this.busId = busId;
        this.driverId = driverId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // GETTERS / SETTERS
    public int getId() { return id; }
    public int getRouteId() { return routeId; }
    public int getBusId() { return busId; }
    public int getDriverId() { return driverId; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }

    public void setId(int id) { this.id = id; }
    public void setRouteId(int routeId) { this.routeId = routeId; }
    public void setBusId(int busId) { this.busId = busId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }
}
