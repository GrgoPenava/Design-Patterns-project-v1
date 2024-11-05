package org.uzdiz.railway;

public class LocalRailway extends Railway {
    public LocalRailway(String oznakaPruge) {
        super(oznakaPruge);
        this.kategorija = "Lokalna";
    }
}