package org.uzdiz.railway;

public class RailwayFactory {
    public static Railway createRailway(String type) {
        return switch (type) {
            case "lokalna" -> new LocalRailway();
            case "regionalna" -> new RegionalRailway();
            default -> throw new IllegalArgumentException("Unknown railway type");
        };
    }
}
