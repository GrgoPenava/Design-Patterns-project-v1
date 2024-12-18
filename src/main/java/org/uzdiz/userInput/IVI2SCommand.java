package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.builder.Station;
import org.uzdiz.railwayFactory.Railway;
import org.uzdiz.timeTableComposite.TimeTableComposite;
import org.uzdiz.timeTableComposite.Train;
import org.uzdiz.timeTableComposite.Etapa;
import org.uzdiz.timeTableComposite.TimeTableComponent;
import org.uzdiz.utils.GraphUtil;
import org.uzdiz.utils.TableBuilder;

import java.util.*;

public class IVI2SCommand implements Command {
    private GraphUtil graphUtil = new GraphUtil();

    @Override
    public void execute(String input) {
        if (!validateInput(input)) {
            return;
        }

        String[] parts = input.substring(6).split(" - ");
        String polaznaStanica = parts[0].trim();
        String odredisnaStanica = parts[1].trim();
        String dan = parts[2].trim();
        String odVr = parts[3].trim();
        String doVr = parts[4].trim();
        String prikaz = parts[5].trim();

        graphUtil.buildGraphFromRailways();

        List<Train> validTrains = findValidTrains(polaznaStanica, odredisnaStanica, dan, odVr, doVr);

        if (validTrains.isEmpty()) {
            System.out.println("Nema vlakova koji zadovoljavaju zadane kriterije.");
            return;
        }

        TableBuilder table = new TableBuilder();
        configureTableHeaders(table, prikaz);

        for (Train train : validTrains) {
            processTrainSchedule(train, polaznaStanica, odredisnaStanica, prikaz, table);
        }

        table.build();
    }

    private boolean validateInput(String input) {
        if (!input.matches("^IVI2S\\s+[^-]+\\s+-\\s+[^-]+\\s+-\\s+[A-Za-zČčĆćŠšŽž]+\\s+-\\s+\\d{1,2}:\\d{2}\\s+-\\s+\\d{1,2}:\\d{2}\\s+-\\s+[A-Za-z]+$")) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Neispravan format naredbe.");
            return false;
        }
        return true;
    }

    private List<Train> findValidTrains(String polaznaStanica, String odredisnaStanica, String dan, String odVr, String doVr) {
        List<Train> validTrains = new ArrayList<>();
        TimeTableComponent vozniRed = ConfigManager.getInstance().getVozniRed();

        if (vozniRed instanceof TimeTableComposite) {
            for (TimeTableComponent component : ((TimeTableComposite) vozniRed).getChildren()) {
                if (component instanceof Train) {
                    Train train = (Train) component;
                    for (TimeTableComponent etapaComponent : train.getChildren()) {
                        if (etapaComponent instanceof Etapa) {
                            Etapa etapa = (Etapa) etapaComponent;
                            if (etapa.getOznakaDana() != null && etapa.getOznakaDana().contains(dan)) {
                                if (etapa.getOznakaDana().contains(dan) &&
                                        etapa.getPocetnaStanica().equals(polaznaStanica) &&
                                        etapa.getOdredisnaStanica().equals(odredisnaStanica) &&
                                        etapa.getVrijemePolaska().compareTo(odVr) >= 0 &&
                                        etapa.getVrijemePolaska().compareTo(doVr) <= 0) {
                                    validTrains.add(train);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    ConfigManager.getInstance().incrementErrorCount();
                    System.out.println("Greška: Vozni red nije ispravnog tipa.");
                }
            }
        }
        return validTrains;
    }

    private void configureTableHeaders(TableBuilder table, String prikaz) {
        List<String> headers = new ArrayList<>();
        for (char c : prikaz.toCharArray()) {
            switch (c) {
                case 'S' -> headers.add("Naziv stanice");
                case 'P' -> headers.add("Oznaka pruge");
                case 'K' -> headers.add("Broj km od polazne stanice");
                case 'V' -> headers.add("Vrijeme polaska");
            }
        }
        table.setHeaders(headers.toArray(new String[0]));
    }

    private void processTrainSchedule(Train train, String polaznaStanica, String odredisnaStanica, String prikaz, TableBuilder table) {
        for (TimeTableComponent etapaComponent : train.getChildren()) {
            if (etapaComponent instanceof Etapa) {
                Etapa etapa = (Etapa) etapaComponent;
                List<Station> stations = ConfigManager.getInstance().getRailwayByOznakaPruge(etapa.getOznakaPruge()).getPopisSvihStanica();

                int startIndex = findStationIndex(stations, polaznaStanica);
                int endIndex = findStationIndex(stations, odredisnaStanica);

                if (startIndex == -1 || endIndex == -1) {
                    continue;
                }

                for (int i = startIndex; i <= endIndex; i++) {
                    Station station = stations.get(i);
                    List<String> row = new ArrayList<>();
                    for (char c : prikaz.toCharArray()) {
                        switch (c) {
                            case 'S' -> row.add(station.getNaziv());
                            case 'P' -> row.add(etapa.getOznakaPruge());
                            case 'K' -> row.add(String.valueOf(station.getDuzina()));
                            case 'V' -> row.add(etapa.getVrijemePolaska());
                        }
                    }
                    table.addRow(row.toArray(new String[0]));
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
    }
}
