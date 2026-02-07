package org.example;

import java.time.LocalDate;

public class MaintenanceRequest {

    private int id;
    private int busId;
    private LocalDate requestDate;
    private String problem;
    private String priority;
    private String status;

    public MaintenanceRequest(int busId, LocalDate requestDate, String problem, String priority, String status) {
        this.busId = busId;
        this.requestDate = requestDate;
        this.problem = problem;
        this.priority = priority;
        this.status = status;
    }

    public MaintenanceRequest(int id, int busId, LocalDate requestDate, String problem, String priority, String status) {
        this.id = id;
        this.busId = busId;
        this.requestDate = requestDate;
        this.problem = problem;
        this.priority = priority;
        this.status = status;
    }

    public int getId() { return id; }
    public int getBusId() { return busId; }
    public LocalDate getRequestDate() { return requestDate; }
    public String getProblem() { return problem; }
    public String getPriority() { return priority; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Запит #" + id + " | " + priority + " | " + status;
    }
}
