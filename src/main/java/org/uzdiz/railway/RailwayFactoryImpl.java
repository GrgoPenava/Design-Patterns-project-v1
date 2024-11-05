package org.uzdiz.railway;

public class RailwayFactoryImpl extends RailwayFactory {
    @Override
    public Railway createRailway(String type, String oznakaPruge) {
        char railType = type.charAt(0);
        switch (railType) {
            case 'L':
                return new LocalRailway(oznakaPruge);
            case 'R':
                return new RegionalRailway(oznakaPruge);
            case 'M':
                return new InternationalRailway(oznakaPruge);
            default:
                throw new IllegalArgumentException("Nepoznat tip pruge: " + type);
        }
    }
}
