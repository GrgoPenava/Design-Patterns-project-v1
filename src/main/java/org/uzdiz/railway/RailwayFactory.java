package org.uzdiz.railway;

public abstract class RailwayFactory {
    public abstract Railway createRailway(String type, String oznakaPruge);
}