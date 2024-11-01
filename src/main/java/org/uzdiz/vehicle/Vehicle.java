package org.uzdiz.vehicle;

public abstract class Vehicle {
    private String id;
    private String description;

    public Vehicle(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
