package org.uzdiz.railway;

import org.uzdiz.station.Station;

import java.util.ArrayList;

public class RegionalRailway extends Railway {
    public RegionalRailway(String oznakaPruge) {
        super(oznakaPruge);
        this.kategorija = "Regionalna";
    }
}
