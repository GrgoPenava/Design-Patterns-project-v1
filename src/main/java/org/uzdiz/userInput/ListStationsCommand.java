package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.railway.Railway;
import org.uzdiz.station.Station;
import org.uzdiz.table.TableBuilder;

import java.util.List;


public class ListStationsCommand implements Command {
    public void execute(String input) {
        if (!validateInput(input)) {
            ConfigManager.getInstance().incrementErrorCount();
            return;
        }

        String[] parts = input.split(" ");
        String oznakaPruge = parts[1];
        boolean isReverseOrder = parts[2].equals("O");

        Railway choosenRailway = ConfigManager.getInstance().getRailways().stream()
                .filter(railway -> railway.getOznakaPruge().equals(oznakaPruge))
                .findFirst()
                .orElse(null);

        if (choosenRailway == null) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Pruga s oznakom '" + oznakaPruge + "' ne postoji.");
            return;
        }

        TableBuilder table = new TableBuilder();
        table.setHeaders("Naziv stanice", "Vrsta stanice", "Duljina od početne stanice (km)");

        if (isReverseOrder) {
            printReverseOrder(choosenRailway, table);
        } else {
            printNormalOrder(choosenRailway, table);
        }

        table.build();
    }

    private void printNormalOrder(Railway choosenRailway, TableBuilder table) {
        double lenSum = 0;
        String lastStationName = "";
        String lastStationType = "";

        List<Station> stations = choosenRailway.getPopisSvihStanica();

        if (!stations.isEmpty() && stations.get(0).getduzina() > 0) {
            stations.get(0).setduzina(0);
        }

        for (Station station : stations) {
            if (!station.getnaziv().equals(lastStationName) || !station.getvrstaStanice().equals(lastStationType)) {
                if (!lastStationName.isEmpty()) {
                    table.addRow(lastStationName, lastStationType, String.format("%.2f", lenSum));
                }
                lastStationName = station.getnaziv();
                lastStationType = station.getvrstaStanice();
            }

            lenSum += station.getduzina();
        }

        if (!lastStationName.isEmpty()) {
            table.addRow(lastStationName, lastStationType, String.format("%.2f", lenSum));
        }
    }


    //TODO ispraviti ako je zapresic 0 za R201
    private void printReverseOrder(Railway choosenRailway, TableBuilder table) {
        double lenSum = 0;
        String lastStationName = "";
        String lastStationType = "";
        double accumulatedLength = 0;

        List<Station> stations = choosenRailway.getPopisSvihStanica();
        for (int i = stations.size() - 1; i >= 0; i--) {
            Station station = stations.get(i);
            if (!station.getnaziv().equals(lastStationName) || !station.getvrstaStanice().equals(lastStationType)) {
                if (!lastStationName.isEmpty()) {
                    table.addRow(lastStationName, lastStationType, String.format("%.2f", lenSum - accumulatedLength));
                }
                lastStationName = station.getnaziv();
                lastStationType = station.getvrstaStanice();
                accumulatedLength = 0;
            }

            lenSum += station.getduzina();
            accumulatedLength += station.getduzina();
        }

        if (!lastStationName.isEmpty()) {
            table.addRow(lastStationName, lastStationType, String.format("%.2f", lenSum - accumulatedLength));
        }
    }


    private boolean validateInput(String input) {
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Naredba mora sadržavati točno tri riječi. Upisano je: " + parts.length);
            return false;
        }

        if (!parts[0].equals("ISP")) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Prva riječ naredbe mora biti 'ISP'. Upisano je: '" + parts[0] + "'");
            return false;
        }

        if (!parts[2].equals("N") && !parts[2].equals("O")) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() + ": Zadnja riječ naredbe mora biti 'N' ili 'O'. Upisano je: '" + parts[2] + "'");
            return false;
        }

        return true;
    }
}
