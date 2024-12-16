package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.builder.Station;
import org.uzdiz.railwayFactory.Railway;
import org.uzdiz.timeTableComposite.Etapa;
import org.uzdiz.timeTableComposite.TimeTableComponent;
import org.uzdiz.timeTableComposite.TimeTableComposite;
import org.uzdiz.timeTableComposite.Train;
import org.uzdiz.utils.TableBuilder;

import java.util.List;

public class TimeTableCommand implements Command {
    @Override
    public void execute(String input) {
        //TODO
    }
        /*ConfigManager config = ConfigManager.getInstance();

        if (!validateInput(input)) {
            config.incrementErrorCount();
            System.out.println("Greška br. " + config.getErrorCount() + ": Neispravan format naredbe. Očekuje se format 'IVRV oznaka' (npr. 'IVRV 3609').");
            return;
        }

        String oznakaVlaka = input.substring(5).trim();
        TimeTableComposite vozniRed = config.getVozniRed();

        if (vozniRed == null || vozniRed.getChildren().isEmpty()) {
            config.incrementErrorCount();
            System.out.println("Greška br. " + config.getErrorCount() + ": Nema dostupnih podataka o voznom redu.");
            return;
        }

        Train train = findTrainByOznaka(vozniRed, oznakaVlaka);

        if (train == null) {
            config.incrementErrorCount();
            System.out.println("Greška br. " + config.getErrorCount() + ": Vlak s oznakom '" + oznakaVlaka + "' nije pronađen.");
            return;
        }

        TableBuilder table = new TableBuilder();
        table.setHeaders("Oznaka vlaka", "Oznaka pruge", "Željeznička stanica", "Vrijeme polaska", "Broj km od polazne stanice");

        processTrainSchedule(train, table);

        table.build();
    }

    private boolean validateInput(String input) {
        return input.matches("^IVRV \\S+$");
    }

    private Train findTrainByOznaka(TimeTableComposite vozniRed, String oznakaVlaka) {
        for (TimeTableComponent component : vozniRed.getChildren()) {
            if (component instanceof Train && ((Train) component).getOznaka().equals(oznakaVlaka)) {
                return (Train) component;
            }
        }
        return null;
    }

    private void processTrainSchedule(Train train, TableBuilder table) {
        String oznakaVlaka = train.getOznaka();
        int totalDistance = 0;

        for (TimeTableComponent etapaComponent : train.getChildren()) {
            if (etapaComponent instanceof Etapa) {
                Etapa etapa = (Etapa) etapaComponent;
                String oznakaPruge = etapa.getOznakaPruge();

                Railway railway = ConfigManager.getInstance().getRailwayByOznakaPruge(oznakaPruge);
                if (railway == null) {
                    ConfigManager.getInstance().incrementErrorCount();
                    System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Pruga s oznakom '" + oznakaPruge + "' nije pronađena.");
                    continue;
                }

                List<Station> stations = railway.getPopisSvihStanica();
                int startIndex = findStationIndex(stations, etapa.getPocetnaStanica());
                int endIndex = findStationIndex(stations, etapa.getOdredisnaStanica());

                if (startIndex == -1 || endIndex == -1) {
                    ConfigManager.getInstance().incrementErrorCount();
                    System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Stanice etape nisu pronađene na pruzi '" + oznakaPruge + "'.");
                    continue;
                }

                if ("O".equals(etapa.getSmjer())) {
                    for (int i = startIndex; i >= endIndex; i--) {
                        Station station = stations.get(i);
                        table.addRow(oznakaVlaka, oznakaPruge, station.getNaziv(), etapa.getVrijemePolaska(), String.valueOf(totalDistance));
                        totalDistance += station.getDuzina();
                    }
                } else {
                    for (int i = startIndex; i <= endIndex; i++) {
                        Station station = stations.get(i);
                        table.addRow(oznakaVlaka, oznakaPruge, station.getNaziv(), etapa.getVrijemePolaska(), String.valueOf(totalDistance));
                        totalDistance += station.getDuzina();
                    }
                }
            }
        }
    }

    private int findStationIndex(List<Station> stations, String stationName) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getNaziv().equals(stationName)) {
                return i;
            }
        }
        return -1;
    }*/
}
