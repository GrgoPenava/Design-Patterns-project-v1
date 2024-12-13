package org.uzdiz.station;

public class StationFactory {
    public static Station createStation(Integer id, String naziv, String oznakaPruge, String vrstaStanice) {
        return switch (vrstaStanice.toLowerCase()) {
            case "kol." -> new MainStation(id, naziv, oznakaPruge, vrstaStanice);
            case "staj." -> new StopStation(id, naziv, oznakaPruge, vrstaStanice);
            default -> throw new IllegalArgumentException("Nepoznat tip stajalista: " + vrstaStanice);
        };
    }
}
