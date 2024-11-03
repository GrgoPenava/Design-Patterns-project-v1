package org.uzdiz.railway;

import org.uzdiz.station.Station;

import java.util.ArrayList;

public class InternationalRailway extends Railway {
    public InternationalRailway(String oznakaPruge) {
        super(oznakaPruge);
        this.kategorija = "Međunarodna";
    }
}
