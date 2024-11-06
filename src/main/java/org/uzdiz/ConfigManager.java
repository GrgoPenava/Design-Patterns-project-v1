package org.uzdiz;

import org.uzdiz.railway.Railway;
import org.uzdiz.station.Station;
import org.uzdiz.vehicle.Composition;
import org.uzdiz.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
    private static volatile ConfigManager instance;

    private String stationFilePath;
    private String railwayFilePath;
    private String compositionFilePath;

    private List<Station> stations = new ArrayList<>();
    private List<Railway> railways = new ArrayList<>();
    private List<Composition> compositions = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();

    private int errorCount;

    private ConfigManager() {
        this.errorCount = 0;
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

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

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
