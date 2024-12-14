package org.uzdiz.readerFactory;

import org.uzdiz.builder.TimeTable;
import org.uzdiz.ConfigManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TimeTableReaderProduct implements CsvReaderProduct {
    private List<TimeTable> timeTables = new ArrayList<>();
    private String path;
    private Integer fileErrorCounter = 0;

    /*
     * Obavezni podaci su:
     * 0  Oznaka pruge
     * 1  Smjer (N ili O)
     * 4  Oznaka vlaka
     * 6  Vrijeme polaska*/

    @Override
    public void loadData(String filePath) {
        this.path = filePath;
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

                if (!data[5].isEmpty()) {
                    String vrstaVlaka = switch (data[5]) {
                        case "U" -> "Ubrzani";
                        case "B" -> "Brzi";
                        default -> "Normalni";
                    };
                    builder.setVrstaVlaka(vrstaVlaka);
                }

                if (!data[7].isEmpty()) {
                    builder.setTrajanjeVoznje(data[7]);
                }

                if (!data[8].isEmpty()) {
                    builder.setOznakaDana(data[8]);
                }

                timeTables.add(builder.build());
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
}
