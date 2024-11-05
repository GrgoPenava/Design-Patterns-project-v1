package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.railway.Railway;
import org.uzdiz.station.Station;
import org.uzdiz.table.TableBuilder;

public class ListRailwaysCommand implements Command {
    public void execute(String input) {
        TableBuilder table = new TableBuilder();
        table.setHeaders("Oznaka", "Početna stanica", "Završna stanica", "Duljina (km)");
        for (Railway railway : ConfigManager.getInstance().getRailways()) {
            Station firstStation = railway.getPopisSvihStanica().get(0);
            Station lastStation = railway.getPopisSvihStanica().get(railway.getPopisSvihStanica().size() - 1);

            double getSum = railway.getPopisSvihStanica().stream()
                    .mapToDouble(Station::getduzina)
                    .sum();

            table.addRow(railway.getOznakaPruge(), firstStation.getnaziv(), lastStation.getnaziv(), String.format("%.2f", getSum));
        }
        table.build();
    }
}
