package org.uzdiz.railway;

public class RailwayFactory {
    public static Railway createRailway(String type) {
        switch (type) {
            case "lokalna":
                return new LocalRailway();
            case "regionalna":
                return new RegionalRailway();
            default:
                throw new IllegalArgumentException("Unknown railway type");
        }
    }
}
