package org.uzdiz.reader;

import org.uzdiz.ConfigManager;
import org.uzdiz.composition.Composition;
import org.uzdiz.composition.ConcreteCompositionBuilder;
import org.uzdiz.composition.CompositionBuilder;
import org.uzdiz.composition.CompositionDirector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CompositionReader implements CsvReader {
    private List<Composition> compositions = new ArrayList<>();

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

                if (isEmptyRow(data) || !validateData(data)) {
                    continue;
                }

                CompositionBuilder compBuilder = new ConcreteCompositionBuilder();
                CompositionDirector director = new CompositionDirector(compBuilder);

                Composition composition = director.createBasicComposition(
                        data[0], // Oznaka
                        data[1], // Oznaka vozila
                        data[2]  // Uloga
                );

                compositions.add(composition);
            }

            ConfigManager.getInstance().setCompositions(compositions);
        } catch (IOException e) {
            System.out.println("Greška pri čitanju datoteke: " + filePath);
            e.printStackTrace();
        }
    }

    private boolean isEmptyRow(String[] data) {
        for (String entry : data) {
            if (entry != null && !entry.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean validateData(String[] data) {
        if (data.length != 3) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Zapis nije potpun, očekuju se 3 podatka, ali postoji " + data.length);
            return false;
        }

        return validatePresence(data) && validateUloga(data[2], "Uloga");
    }

    private boolean validatePresence(String[] data) {
        String[] columnNames = {"Oznaka", "Oznaka vozila", "Uloga"};

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null || data[i].trim().isEmpty()) {
                ConfigManager.getInstance().incrementErrorCount();
                System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Nedostaje vrijednost za stupac '" + columnNames[i] + "'.");
                return false;
            }
        }
        return true;
    }

    private boolean validateUloga(String value, String fieldName) {
        if (!value.equals("P") && !value.equals("V")) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": " + fieldName + " mora biti 'P' ili 'V'. Pronađeno: '" + value + "'");
            return false;
        }
        return true;
    }
}
