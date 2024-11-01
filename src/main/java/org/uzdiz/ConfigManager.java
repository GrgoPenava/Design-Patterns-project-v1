package org.uzdiz;

import org.uzdiz.composition.Composition;
import org.uzdiz.railway.Railway;
import org.uzdiz.station.Station;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private static ConfigManager instance;

    // Putanje do CSV datoteka
    private String stationFilePath;
    private String railwayFilePath;
    private String compositionFilePath;

    private List<Station> stations = new ArrayList<>();
    private List<Railway> railways = new ArrayList<>();
    private List<Composition> compositions = new ArrayList<>();

    // Brojač pogrešaka
    private int errorCount;

    private ConfigManager() {
        // Inicijalizacija zadane konfiguracije
        this.errorCount = 0;
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    // Metoda za postavljanje putanje do datoteka
    public void setStationFilePath(String path) {
        this.stationFilePath = path;
    }

    public String getStationFilePath() {
        return stationFilePath;
    }

    public void setRailwayFilePath(String path) {
        this.railwayFilePath = path;
    }

    public String getRailwayFilePath() {
        return railwayFilePath;
    }

    public void setCompositionFilePath(String path) {
        this.compositionFilePath = path;
    }

    public String getCompositionFilePath() {
        return compositionFilePath;
    }

    public void incrementErrorCount() {
        this.errorCount++;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setRailways(List<Railway> railways) {
        this.railways = railways;
    }

    public List<Railway> getRailways() {
        return railways;
    }

    public void setCompositions(List<Composition> compositions) {
        this.compositions = compositions;
    }

    public List<Composition> getCompositions() {
        return compositions;
    }

    // Ostale metode za konfiguraciju i validaciju
}
