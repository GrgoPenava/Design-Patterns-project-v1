package org.uzdiz.composition;

import org.uzdiz.vehicle.Vehicle;

import java.util.List;

public class Composition {
    private String id;
    private List<Vehicle> vehicles;

    public Composition(String id, List<Vehicle> vehicles) {
        this.id = id;
        this.vehicles = vehicles;
    }

    public String getId() {
        return id;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @Override
    public String toString() {
        return "Composition{" +
                "id='" + id + '\'' +
                ", vehicles=" + vehicles +
                '}';
    }
}
