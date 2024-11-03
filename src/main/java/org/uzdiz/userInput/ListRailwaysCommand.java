package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.railway.Railway;
import org.uzdiz.station.Station;

public class ListRailwaysCommand implements Command {
    public void execute() {
        // Logika za ispis pruga
        for (Railway railway : ConfigManager.getInstance().getRailways()) {
            Station firstStation = railway.getPopisSvihStanica().get(0);
            Station lastStation = railway.getPopisSvihStanica().get(railway.getPopisSvihStanica().size() - 1);

            double getSum = railway.getPopisSvihStanica().stream()
                    .mapToDouble(Station::getduzina)
                    .sum();

            System.out.println(railway.getOznakaPruge() + "," + firstStation.getnaziv() + "," + lastStation.getnaziv() + "," + getSum);
        }
    }
}
