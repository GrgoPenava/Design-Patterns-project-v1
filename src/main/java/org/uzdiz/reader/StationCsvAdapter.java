package org.uzdiz.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.uzdiz.ConfigManager;
import org.uzdiz.railway.Railway;
import org.uzdiz.railway.RailwayFactory;
import org.uzdiz.station.Station;
import org.uzdiz.station.StationFactory;


public class StationCsvAdapter implements CsvAdapter {
    private List<Station> stations = new ArrayList<>();

    @Override
    public void loadData(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Greška: Nije moguće pronaći datoteku " + filePath + ". Provjerite jeste li pozicionirani u odgovarajuću mapu.");
            return;
        }

        List<Railway> railways = new ArrayList<>();
        Railway currentRailway = null;
        String currentRailwayType = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] data = line.split(";");
                if (data.length != 14) {
                    System.out.println("Nepodrzan zapis: " + line + "(Kriva duljina zapisa)");
                    continue;
                }

                try {
                    String oznakaPruge = data[1];
                    Station station = StationFactory.createStation(data[0], data[1], data[2]);

                    //TODO napraviti bolju validaciju
                    station.setAdditionalAttributes(
                            data[3],
                            "DA".equals(data[4]),
                            "DA".equals(data[5]),
                            data[6],
                            Integer.parseInt(data[7]),
                            data[8],
                            Integer.parseInt(data[9]), //br kolosjeka
                            Double.parseDouble(data[10].replace(",", ".")),
                            Double.parseDouble(data[11].replace(",", ".")),
                            data[12],
                            Integer.parseInt(data[13])
                    );
                    if (currentRailway == null || !oznakaPruge.equals(currentRailwayType)) {
                        // Ako postoji trenutna pruga, dodaj je u listu
                        if (currentRailway != null) {
                            railways.add(currentRailway);
                        }

                        // Kreiraj novu prugu i postavi oznaku pruge
                        currentRailway = RailwayFactory.createRailway(data[6], data[1]);
                        currentRailwayType = oznakaPruge;
                    }

                    // Dodaj stanicu u trenutnu prugu
                    currentRailway.addStation(station);
                    stations.add(station);
                } catch (IllegalArgumentException e) {
                    System.out.println("Nepodrzan zapis: " + line + " (" + e.getMessage() + ")");
                }
            }

            if (currentRailway != null) {
                railways.add(currentRailway);
            }

            ConfigManager.getInstance().setRailways(railways);
            ConfigManager.getInstance().setStations(stations);
        } catch (IOException e) {
            System.out.println("Greška pri čitanju stanica datoteke: " + filePath);
            e.printStackTrace();
        }
    }
}
