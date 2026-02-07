package org.example;

public class Driver {

    private int id;
    private String name;
    private String phone;
    private String license; // ← поле ПРАВА

    // Конструктор для вставки нового водія (без ID)
    public Driver(String name, String phone, String license) {
        this.name = name;
        this.phone = phone;
        this.license = license;
    }

    // Повний конструктор
    public Driver(int id, String name, String phone, String license) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.license = license;
    }

    // GETTERS
    public int getId() { return id; }

    public String getName() { return name; }

    public String getPhone() { return phone; }

    public String getLicense() {   // ← ЦЕЙ МЕТОД ТЕБЕ ЦІКАВИТЬ
        return license;
    }

    // SETTERS
    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setLicense(String license) { this.license = license; }

    // ⬇️⬇️ ДОДАЙ ЦЕ СЮДИ — КРАСИВЕ ВІДОБРАЖЕННЯ У ComboBox
    @Override
    public String toString() {
        return name + " (ліцензія: " + license + ")";
    }
}
