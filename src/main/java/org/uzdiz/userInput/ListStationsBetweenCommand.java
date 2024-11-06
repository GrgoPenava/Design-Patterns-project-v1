package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.railway.Railway;
import org.uzdiz.station.Station;
import org.uzdiz.table.TableBuilder;

import java.util.List;
import java.util.Optional;

//TODO treba ispraviti ispis duplikata npr ISI2S Čakovec-Luka
public class ListStationsBetweenCommand implements Command {
    public void execute(String input) {
        if (!validateInput(input)) {
            ConfigManager.getInstance().incrementErrorCount();
            return;
        }

        String[] parts = input.substring(6).split("[-]");
        String startStation = parts[0].trim();
        String endStation = parts[1].trim();

        if (!stationExists(startStation) || !stationExists(endStation)) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Jedna ili obje stanice ne postoje u sustavu.");
            return;
        }

        Optional<Railway> railwayOptional = ConfigManager.getInstance().getRailways().stream()
                .filter(railway -> containsStations(railway, startStation, endStation))
                .findFirst();

        if (railwayOptional.isEmpty()) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Stanice se ne nalaze na istoj pruzi.");
            return;
        }

        Railway railway = railwayOptional.get();
        List<Station> stations = railway.getPopisSvihStanica();

        int startIndex = findStationIndex(stations, startStation);
        int endIndex = findStationIndex(stations, endStation);

        if (startIndex == -1 || endIndex == -1 || startIndex == endIndex) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Stanice nisu ispravne ili se ne nalaze na istoj pruzi.");
            return;
        }

        TableBuilder table = new TableBuilder();
        table.setHeaders("Naziv stanice", "Vrsta", "Broj km od početne stanice");

        if (startIndex < endIndex) {
            printNormalOrder(table, stations, startIndex, endIndex);
        } else {
            printReverseOrder(table, stations, startIndex, endIndex);
        }

        table.build();
    }

    private void printNormalOrder(TableBuilder table, List<Station> stations, int startIndex, int endIndex) {
        double distanceSum = 0;
        for (int i = startIndex + 1; i < endIndex; i++) {
            distanceSum += stations.get(i).getduzina();
            table.addRow(stations.get(i).getnaziv(), stations.get(i).getvrstaStanice(), String.format("%.2f", distanceSum));
        }
    }

    private void printReverseOrder(TableBuilder table, List<Station> stations, int startIndex, int endIndex) {
        double distanceSum = 0;
        for (int i = startIndex - 1; i > endIndex; i--) {
            distanceSum += stations.get(i + 1).getduzina();
            table.addRow(stations.get(i).getnaziv(), stations.get(i).getvrstaStanice(), String.format("%.2f", distanceSum));
        }
    }

    private boolean validateInput(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 2 || !input.contains("-")) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Naredba mora sadržavati početnu stanicu, znak '-' i završnu stanicu.");
            return false;
        }
        String[] stationParts = input.substring(6).split("[-]");
        if (stationParts.length != 2 || stationParts[0].trim().isEmpty() || stationParts[1].trim().isEmpty()) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Unesite ispravno ime početne i završne stanice.");
            return false;
        }
        return true;
    }

    private boolean containsStations(Railway railway, String startStation, String endStation) {
        return railway.getPopisSvihStanica().stream().anyMatch(station -> station.getnaziv().equals(startStation)) &&
                railway.getPopisSvihStanica().stream().anyMatch(station -> station.getnaziv().equals(endStation));
    }

    private int findStationIndex(List<Station> stations, String stationName) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getnaziv().equals(stationName)) {
                return i;
            }
        }
        return -1;
    }

    private boolean stationExists(String stationName) {
        return ConfigManager.getInstance().getStations().stream()
                .anyMatch(station -> station.getnaziv().equals(stationName));
    }
}
