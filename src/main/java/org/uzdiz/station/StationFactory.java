package org.uzdiz.station;

public class StationFactory {
    public static Station createStation(String id, String name, String type) {
        return switch (type.toLowerCase()) {
            case "kol." -> new MainStation(id, name, type);
            case "staj." -> new StopStation(id, name, type);
            default -> throw new IllegalArgumentException("Nepoznat tip stajalista: " + type);
        };
    }
}
