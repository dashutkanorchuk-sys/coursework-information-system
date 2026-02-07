package org.example;

public class Bus {
    private int id;
    private String model;
    private int seats;
    private String plateNumber;

    // Конструктор для додавання
    public Bus(String model, int seats, String plateNumber) {
        this.model = model;
        this.seats = seats;
        this.plateNumber = plateNumber;
    }

    // Конструктор для оновлення
    public Bus(int id, String model, int seats, String plateNumber) {
        this.id = id;
        this.model = model;
        this.seats = seats;
        this.plateNumber = plateNumber;
    }

    // GET/SET
    public int getId() { return id; }
    public String getModel() { return model; }
    public int getSeats() { return seats; }
    public String getPlateNumber() { return plateNumber; }
    @Override
    public String toString() {
        return model + " [" + plateNumber + "]";
    }

}
