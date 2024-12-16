package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.DrivingDays;
import org.uzdiz.timeTableComposite.*;
import org.uzdiz.utils.TableBuilder;

import java.util.List;
import java.util.Optional;

public class ListByDaysCommand implements Command {

    @Override
    public void execute(String input) {
        ConfigManager config = ConfigManager.getInstance();

        if (!validateInput(input)) {
            config.incrementErrorCount();
            System.out.println("Greška br. " + config.getErrorCount() + ": Neispravan format naredbe. Očekuje se format 'IEVD dani' (npr. 'IEVD PoSrPeN').");
            return;
        }

        String dani = input.substring(5).trim();

        if (!areValidDays(dani)) {
            config.incrementErrorCount();
            System.out.println("Greška br. " + config.getErrorCount() + ": Uneseni dani '" + dani + "' nisu valjani.");
            return;
        }

        TimeTableComposite vozniRed = config.getVozniRed();
        if (vozniRed == null || vozniRed.getChildren().isEmpty()) {
            config.incrementErrorCount();
            System.out.println("Greška br. " + config.getErrorCount() + ": Nema dostupnih podataka o voznom redu.");
            return;
        }

        TableBuilder table = new TableBuilder();
        table.setHeaders("Oznaka vlaka", "Oznaka pruge", "Polazišna stanica", "Odredišna stanica", "Vrijeme polaska", "Vrijeme dolaska", "Dani u tjednu");

        for (TimeTableComponent trainComponent : vozniRed.getChildren()) {
            if (trainComponent instanceof Train) {
                Train train = (Train) trainComponent;
                processTrain(train, dani, table);
            }
        }

        table.build();
    }

    private boolean validateInput(String input) {
        return input.matches("^IEVD\\s+[A-Za-zČčĆćŠšŽž]+$");
    }

    private void processTrain(Train train, String dani, TableBuilder table) {
        String oznakaVlaka = train.getOznaka();

        for (TimeTableComponent etapaComponent : train.getChildren()) {
            if (etapaComponent instanceof Etapa) {
                Etapa etapa = (Etapa) etapaComponent;

                String oznakaDana = etapa.getOznakaDana();
                if (isMatchingDays(oznakaDana, dani)) {
                    String oznakaPruge = etapa.getOznakaPruge();
                    String polaznaStanica = etapa.getPocetnaStanica();
                    String odredisnaStanica = etapa.getOdredisnaStanica();
                    String vrijemePolaska = etapa.getVrijemePolaska();
                    String vrijemeDolaska = calculateArrivalTime(vrijemePolaska, etapa.getTrajanjeVoznje());
                    String daniUTjednu = getDrivingDays(oznakaDana);

                    table.addRow(oznakaVlaka, oznakaPruge, polaznaStanica, odredisnaStanica, vrijemePolaska, vrijemeDolaska, daniUTjednu);
                }
            }
        }
    }

    private boolean isMatchingDays(String oznakaDana, String dani) {
        ConfigManager config = ConfigManager.getInstance();

        if (oznakaDana == null) {
            return true;
        }

        Optional<DrivingDays> drivingDaysOpt = config.getDrivingDays().stream()
                .filter(days -> days.getOznaka().equals(oznakaDana))
                .findFirst();

        if (drivingDaysOpt.isPresent()) {
            List<String> availableDays = drivingDaysOpt.get().getDays();

            for (int i = 0; i < dani.length(); ) {
                String dan = (i + 2 <= dani.length()) ? dani.substring(i, i + 2) : dani.substring(i);
                i += dan.length();

                if (!availableDays.contains(dan)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    private String calculateArrivalTime(String vrijemePolaska, String trajanjeVoznje) {
        try {
            String[] polazakParts = vrijemePolaska.split(":");
            String[] trajanjeParts = trajanjeVoznje.split(":");

            int polazakSati = Integer.parseInt(polazakParts[0]);
            int polazakMinute = Integer.parseInt(polazakParts[1]);

            int trajanjeSati = Integer.parseInt(trajanjeParts[0]);
            int trajanjeMinute = Integer.parseInt(trajanjeParts[1]);

            int ukupnoMinute = polazakMinute + trajanjeMinute;
            int ukupnoSati = polazakSati + trajanjeSati + ukupnoMinute / 60;
            ukupnoMinute %= 60;

            ukupnoSati %= 24;

            return String.format("%02d:%02d", ukupnoSati, ukupnoMinute);
        } catch (Exception e) {
            return "//";
        }
    }

    private String getDrivingDays(String oznakaDana) {
        Optional<DrivingDays> drivingDays = ConfigManager.getInstance().getDrivingDays().stream()
                .filter(days -> days.getOznaka().equals(oznakaDana))
                .findFirst();

        return drivingDays.map(days -> String.join(", ", days.getDays()))
                .orElse("Po, U, Sr, Č, Pe, Su, N");
    }

    private boolean areValidDays(String dani) {
        List<String> validDays = List.of("Po", "U", "Sr", "Č", "Pe", "Su", "N");

        for (int i = 0; i < dani.length(); ) {
            String dan = (i + 2 <= dani.length()) ? dani.substring(i, i + 2) : dani.substring(i);
            i += dan.length();

            if (!validDays.contains(dan)) {
                return false;
            }
        }

        return true;
    }
}
