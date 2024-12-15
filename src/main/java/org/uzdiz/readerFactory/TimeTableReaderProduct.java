package org.uzdiz.readerFactory;

import org.uzdiz.builder.TimeTable;
import org.uzdiz.ConfigManager;
import org.uzdiz.timeTableComposite.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeTableReaderProduct implements CsvReaderProduct {
    private List<TimeTable> timeTables = new ArrayList<>();
    private String path;
    private Integer fileErrorCounter = 0;

    TimeTableComposite vozniRed = ConfigManager.getInstance().getVozniRed();

    private Map<String, Train> trainMap = new HashMap<>();

    /*
     * Obavezni podaci su:
     * 0  Oznaka pruge
     * 1  Smjer (N ili O)
     * 4  Oznaka vlaka
     * 6  Vrijeme polaska*/

    @Override
    public void loadData(String filePath) {
        this.path = filePath;

        if (vozniRed == null) {
            vozniRed = new VozniRed("Vozni red");
            ConfigManager.getInstance().setVozniRed(vozniRed);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                if (line.startsWith("#")) continue;

                String[] data = line.split(";", -1);

                if (!validateData(data)) {
                    continue;
                }

                TimeTable.TimeTableBuilder builder = new TimeTable.TimeTableBuilder(data[0], data[1], data[4], data[6]);

                if (!data[2].isEmpty()) {
                    builder.setPolaznaStanica(data[2]);
                }

                if (!data[3].isEmpty()) {
                    builder.setOdredisnaStanica(data[3]);
                }

                String vrstaVlaka = switch (data[5]) {
                    case "U" -> "U";
                    case "B" -> "B";
                    default -> "N";
                };
                builder.setVrstaVlaka(vrstaVlaka);

                if (!data[7].isEmpty()) {
                    builder.setTrajanjeVoznje(data[7]);
                }

                if (!data[8].isEmpty()) {
                    builder.setOznakaDana(data[8]);
                }

                timeTables.add(builder.build());

                String oznakaPruge = data[0];
                String smjer = data[1];
                String oznakaVlaka = data[4];
                String polaznaStanica = data[2];
                String odredisnaStanica = data[3];

                Train train = trainMap.computeIfAbsent(oznakaVlaka, k -> new Train(oznakaVlaka));

                Etapa etapa = new Etapa(oznakaPruge);

                if (!polaznaStanica.isEmpty()) {
                    etapa.add(new Station(polaznaStanica));
                }

                if (!odredisnaStanica.isEmpty()) {
                    etapa.add(new Station(odredisnaStanica));
                }

                train.add(etapa);

                if (!vozniRed.getChildren().contains(train)) {
                    vozniRed.add(train);
                }
            }

            ConfigManager.getInstance().setTimeTables(timeTables);
        } catch (IOException e) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Nije moguće učitati datoteku - " + filePath);
            this.printFileError();
        }
    }

    private void printFileError() {
        this.fileErrorCounter++;
        System.out.println("-> Greška datoteke (" + path + ") br. " + this.fileErrorCounter);
    }

    private boolean validateData(String[] data) {
        boolean isValid = true;

        if (isEmptyRow(data)) {
            return false;
        }

        if (data.length != 9) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Zapis nije potpun, očekuje se 9 podataka, ali postoji " + data.length);
            return false;
        }

        if (data[0].isEmpty()) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Nedostaje vrijednost za 'Oznaka pruge'.");
            isValid = false;
        }

        if (data[1].isEmpty()) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Nedostaje vrijednost za 'Smjer'.");
            isValid = false;
        } else if (!data[1].equals("N") && !data[1].equals("O")) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Neispravna vrijednost za 'Smjer'. Dozvoljene vrijednosti su 'N' ili 'O'.");
            isValid = false;
        }

        if (data[4].isEmpty()) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Nedostaje vrijednost za 'Oznaka vlaka'.");
            isValid = false;
        }

        if (data[6].isEmpty()) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Nedostaje vrijednost za 'Vrijeme polaska'.");
            isValid = false;
        }

        if (!data[2].isEmpty() && !isStationValid(data[2])) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Polazna stanica '" + data[2] + "' ne postoji u popisu svih stanica.");
            isValid = false;
        }

        if (!data[3].isEmpty() && !isStationValid(data[3])) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Odredišna stanica '" + data[3] + "' ne postoji u popisu svih stanica.");
            isValid = false;
        }

        if (!isValid) {
            this.printFileError();
        }

        return isValid;
    }

    private boolean isEmptyRow(String[] data) {
        for (String field : data) {
            if (field != null && !field.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isStationValid(String stationName) {
        return ConfigManager.getInstance().getStations().stream()
                .anyMatch(station -> station.getNaziv().equals(stationName));
    }
}
