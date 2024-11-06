package org.uzdiz.reader;

import org.uzdiz.ConfigManager;
import org.uzdiz.vehicle.ConcreteVehicleBuilder;
import org.uzdiz.vehicle.Vehicle;
import org.uzdiz.vehicle.VehicleBuilder;
import org.uzdiz.vehicle.VehicleDirector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class VehicleReader implements CsvReader {
    private List<Vehicle> vehicles = new ArrayList<>();

    @Override
    public void loadData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(";");
                if (!validateData(data)) {
                    continue;
                }

                VehicleBuilder vehBuilder = new ConcreteVehicleBuilder();
                VehicleDirector director = new VehicleDirector(vehBuilder);

                Vehicle vehicle = director.constructStandardVehicle(
                        data[0],
                        data[1],
                        data[2],
                        data[3]
                );

                vehBuilder.setNamjera(data[4])
                        .setVrstaPrijevoza(data[5])
                        .setVrstaPogona(data[6])
                        .setMaksBrzina(data[7])
                        .setMaksSnaga(data[8].replace(",", "."))
                        .setBrojSjedecihMjesta(data[9])
                        .setBrojStajacihMjesta(data[10])
                        .setBrojBicikala(data[11])
                        .setBrojKreveta(data[12])
                        .setBrojAutomobila(data[13])
                        .setNosivost(data[14].replace(",", "."))
                        .setPovrsina(data[15].replace(",", "."))
                        .setZapremnina(data[16].replace(",", "."))
                        .setStatus(data[17]);

                vehicles.add(vehBuilder.build());
            }

            ConfigManager.getInstance().setVehicles(vehicles);
        } catch (IOException e) {
            System.out.println("Greška pri čitanju datoteke: " + filePath);
            e.printStackTrace();
        }
    }

    private boolean validateData(String[] data) {
        if (data.length != 18) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Zapis nije potpun, očekuje se 18 podataka, ali postoji " + data.length);
            return false;
        }

        return validatePresence(data) &&
                validateInteger(data[7], "Maksimalna Brzina", 1, 200) &&
                validateDoubleRange(data[8].replace(",", "."), "Maksimalna Snaga", -1.0, 10.0, true) &&
                validateInteger(data[9], "Broj Sjedecih Mjesta") &&
                validateInteger(data[10], "Broj Stajacih Mjesta") &&
                validateInteger(data[11], "Broj Bicikala") &&
                validateInteger(data[12], "Broj Kreveta") &&
                validateInteger(data[13], "Broj Automobila") &&
                validateDouble(data[14].replace(",", "."), "Nosivost") &&
                validateDouble(data[15].replace(",", "."), "Povrsina") &&
                validateDouble(data[16].replace(",", "."), "Zapremnina") &&
                validateStatus(data[17], "Status");
    }

    private boolean validatePresence(String[] data) {
        String[] columnNames = {
                "Oznaka", "Opis", "Proizvodac", "Godina", "Namjera", "Vrsta Prijevoza", "Vrsta Pogona",
                "Maksimalna Brzina", "Maksimalna Snaga", "Broj Sjedecih Mjesta", "Broj Stajacih Mjesta",
                "Broj Bicikala", "Broj Kreveta", "Broj Automobila", "Nosivost", "Povrsina", "Zapremnina", "Status"
        };

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].isEmpty()) {
                ConfigManager.getInstance().incrementErrorCount();
                System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Nedostaje vrijednost za stupac '" + columnNames[i] + "'.");
                return false;
            }
        }
        return true;
    }

    private boolean validateInteger(String value, String fieldName) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": " + fieldName + " mora biti cijeli broj. Pronađeno: '" + value + "'");
            return false;
        }
    }

    private boolean validateInteger(String value, String fieldName, int min, int max) {
        try {
            int intValue = Integer.parseInt(value);
            if (intValue < min || intValue > max) {
                ConfigManager.getInstance().incrementErrorCount();
                System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": " + fieldName + " mora biti između " + min + " i " + max + ". Pronađeno: '" + value + "'");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": " + fieldName + " mora biti cijeli broj. Pronađeno: '" + value + "'");
            return false;
        }
    }

    private boolean validateDouble(String value, String fieldName) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": " + fieldName + " mora biti decimalan broj. Pronađeno: '" + value + "'");
            return false;
        }
    }

    private boolean validateDoubleRange(String value, String fieldName, double min, double max, boolean allowNegativeOne) {
        try {
            double doubleValue = Double.parseDouble(value);
            if ((doubleValue < min || doubleValue > max) && (!allowNegativeOne || doubleValue != -1.0)) {
                ConfigManager.getInstance().incrementErrorCount();
                System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": " + fieldName + " mora biti između " + min + " i " + max +
                        " ili -1. Pronađeno: '" + value + "'");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": " + fieldName + " mora biti decimalan broj. Pronađeno: '" + value + "'");
            return false;
        }
    }

    private boolean validateStatus(String value, String fieldName) {
        if (!value.equals("I") && !value.equals("K")) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": " + fieldName + " mora biti 'I' ili 'K'. Pronađeno: '" + value + "'");
            return false;
        }
        return true;
    }
}
