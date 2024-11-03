package org.uzdiz.railway;

import org.uzdiz.station.Station;

import java.util.ArrayList;

public class RailwayFactory {
    public static Railway createRailway(String type, String oznakaPruge) {
        char railType = type.charAt(0);
        return switch (railType) {
            case 'L' -> new LocalRailway(oznakaPruge);
            case 'R' -> new RegionalRailway(oznakaPruge);
            case 'M' -> new InternationalRailway(oznakaPruge);
            default -> throw new IllegalArgumentException("Nepoznat tip pruge" + type);
        };
    }
}
