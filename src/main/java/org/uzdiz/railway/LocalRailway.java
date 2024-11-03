package org.uzdiz.railway;

import org.uzdiz.station.Station;

import java.util.ArrayList;

public class LocalRailway extends Railway {
    public LocalRailway(String oznakaPruge) {
        super(oznakaPruge);
        this.kategorija = "Lokalna";
    }
}