package org.uzdiz;

public class ConfigManager {
    private static ConfigManager instance;

    // Putanje do CSV datoteka
    private String stationFilePath;
    private String railwayFilePath;
    private String compositionFilePath;

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

    public void incrementErrorCount() {
        this.errorCount++;
    }

    public int getErrorCount() {
        return errorCount;
    }

    // Ostale metode za konfiguraciju i validaciju
}
