package org.example;

import java.time.LocalDate;

public class Ticket {

    private int id;
    private int passengerId;
    private int routeId;
    private int busId;
    private LocalDate departureDate;
    private double price;
    private String status;

    public Ticket(int passengerId, int routeId, int busId, LocalDate departureDate, double price, String status) {
        this.passengerId = passengerId;
        this.routeId = routeId;
        this.busId = busId;
        this.departureDate = departureDate;
        this.price = price;
        this.status = status;
    }

    public Ticket(int id, int passengerId, int routeId, int busId, LocalDate departureDate, double price, String status) {
        this.id = id;
        this.passengerId = passengerId;
        this.routeId = routeId;
        this.busId = busId;
        this.departureDate = departureDate;
        this.price = price;
        this.status = status;
    }

    public int getId() { return id; }
    public int getPassengerId() { return passengerId; }
    public int getRouteId() { return routeId; }
    public int getBusId() { return busId; }
    public LocalDate getDepartureDate() { return departureDate; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Квиток #" + id + " | Дата: " + departureDate + " | Статус: " + status;
    }
}
