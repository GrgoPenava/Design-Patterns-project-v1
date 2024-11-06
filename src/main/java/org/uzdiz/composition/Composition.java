package org.uzdiz.composition;

import org.uzdiz.vehicle.Vehicle;

public class Composition {
    private String oznaka;
    private String oznakaVozila;
    private String uloga;

    public Composition() {
    }

    public String getOznaka() {
        return oznaka;
    }

    public Composition setOznaka(String oznaka) {
        this.oznaka = oznaka;
        return this;
    }

    public String getOznakaVozila() {
        return oznakaVozila;
    }

    public Composition setOznakaVozila(String oznakaVozila) {
        this.oznakaVozila = oznakaVozila;
        return this;
    }

    public String getUloga() {
        return uloga;
    }

    public Composition setUloga(String uloga) {
        this.uloga = uloga;
        return this;
    }

    @Override
    public String toString() {
        return "Composition{" +
                "oznaka='" + oznaka + '\'' +
                ", oznakaVozila='" + oznakaVozila + '\'' +
                ", uloga='" + uloga + '\'' +
                '}';
    }
}
