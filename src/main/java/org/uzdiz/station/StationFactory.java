package org.uzdiz.station;

public class StationFactory {
    public static Station createStation(String type) {
        switch (type) {
            case "kolodvor":
                return new MainStation();
            case "stajalište":
                return new StopStation();
            default:
                throw new IllegalArgumentException("Unknown station type");
        }
    }
}

