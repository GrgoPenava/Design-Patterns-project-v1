package org.uzdiz.vehicle;

public class VehicleFactory {
    public static Vehicle createVehicle(String type, String id, String description) {
        return switch (type.toLowerCase()) {
            case "lokomotiva" -> new Locomotive(id, description);
            case "vagon" -> new Wagon(id, description);
            default -> throw new IllegalArgumentException("Nepoznat tip vozila: " + type);
        };
    }
}
