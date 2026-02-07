package org.example;

public class Passenger {

    private int id;
    private String name;
    private String phone;
    private String email;

    // Конструктор для додавання
    public Passenger(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Конструктор для оновлення
    public Passenger(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // GETTERS
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
    
}

