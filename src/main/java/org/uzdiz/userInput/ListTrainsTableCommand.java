package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.timeTableComposite.*;
import org.uzdiz.utils.TableBuilder;
import org.uzdiz.builder.Station;
import org.uzdiz.railwayFactory.Railway;

import java.util.List;

public class ListTrainsTableCommand implements Command {

    @Override
    public void execute(String input) {
        TimeTableComposite vozniRed = ConfigManager.getInstance().getVozniRed();

        if (vozniRed == null || vozniRed.getChildren().isEmpty()) {
            System.out.println("Nema dostupnih podataka o vlakovima.");
            return;
        }

        TableBuilder table = new TableBuilder();
        table.setHeaders("Oznaka vlaka", "Polazišna stanica", "Odredišna stanica", "Vrijeme polaska", "Vrijeme dolaska", "Ukupan broj km");

        for (TimeTableComponent trainComponent : vozniRed.getChildren()) {
            if (trainComponent instanceof Train) {
                Train train = (Train) trainComponent;
                processTrain(train, table);
            }
        }

        table.build();
    }

    private void processTrain(Train train, TableBuilder table) {
        String oznakaVlaka = train.getOznaka();

        for (TimeTableComponent etapaComponent : train.getChildren()) {
            if (etapaComponent instanceof Etapa) {
                Etapa etapa = (Etapa) etapaComponent;

                String polaznaStanica = etapa.getPocetnaStanica();
                String odredisnaStanica = etapa.getOdredisnaStanica();
                String vrijemePolaska = etapa.getVrijemePolaska();
                String vrijemeDolaska = calculateArrivalTime(vrijemePolaska, etapa.getTrajanjeVoznje());
                int ukupanBrojKm = calculateTotalDistance(etapa);

                table.addRow(oznakaVlaka, polaznaStanica, odredisnaStanica, vrijemePolaska, vrijemeDolaska, String.valueOf(ukupanBrojKm));
            }
        }
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

    private int calculateTotalDistance(Etapa etapa) {
        int totalDistance = 0;

        Railway railway = ConfigManager.getInstance().getRailwayByOznakaPruge(etapa.getOznakaPruge());
        if (railway == null) {
            return 0;
        }

        List<Station> stations = railway.getPopisSvihStanica();
        int startIndex = findStationIndex(stations, etapa.getPocetnaStanica());
        int endIndex = findStationIndex(stations, etapa.getOdredisnaStanica());

        if (startIndex == -1 || endIndex == -1) {
            return 0;
        }

        if ("O".equals(etapa.getSmjer())) {
            // Obrnuti smjer
            for (int i = startIndex; i > endIndex; i--) {
                totalDistance += stations.get(i).getDuzina();
            }
        } else {
            // Normalan smjer
            for (int i = startIndex + 1; i <= endIndex; i++) {
                totalDistance += stations.get(i).getDuzina();
            }
        }

        return totalDistance;
    }

    private int findStationIndex(List<Station> stations, String stationName) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getNaziv().equals(stationName)) {
                return i;
            }
        }
        return -1;
    }
}
