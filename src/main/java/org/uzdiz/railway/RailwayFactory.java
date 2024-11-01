package org.uzdiz.railway;

public class RailwayFactory {
    public static Railway createRailway(String type) {
        char railType = type.charAt(0);
        return switch (railType) {
            case 'L' -> new LocalRailway();
            case 'R' -> new RegionalRailway();
            case 'M' -> new InternationalRailway();
            default -> throw new IllegalArgumentException("Nepoznat tip pruge" + type);
        };
    }
}
