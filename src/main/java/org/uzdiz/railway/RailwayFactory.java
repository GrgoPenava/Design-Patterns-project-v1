package org.uzdiz.railway;

public abstract class RailwayFactory {
    // Factory method
    public abstract Railway createRailway(String type, String oznakaPruge);
}