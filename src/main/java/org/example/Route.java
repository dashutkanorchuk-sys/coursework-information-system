package org.example;

public class Route {
    private int id;
    private String startPoint;
    private String endPoint;
    private double distance;

    public Route(int id, String startPoint, String endPoint, double distance) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
    }

    public Route(String startPoint, String endPoint, double distance) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public double getDistance() {
        return distance;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    @Override
    public String toString() {
        return startPoint + " → " + endPoint;
    }
}
