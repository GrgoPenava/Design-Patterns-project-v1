package org.uzdiz.userInput;

import org.uzdiz.timeTableComposite.*;
import org.uzdiz.user.User;
import org.uzdiz.ConfigManager;

public class DPKCommand implements Command {

    @Override
    public void execute(String input) {
        if (!validateInput(input)) {
            System.out.println("Greška: Neispravan format naredbe. Očekuje se 'DPK ime prezime - oznakaVlaka [- stanica]'.");
            return;
        }

        String[] parts = input.substring(4).split(" - ");
        String[] nameParts = parts[0].trim().split("\\s+", 2);
        String ime = nameParts[0];
        String prezime = nameParts[1];
        String oznakaVlaka = parts[1].trim();
        String stanica = parts.length == 3 ? parts[2].trim() : null;

        User user = findUserByName(ime, prezime);
        if (user == null) {
            System.out.println("Greška: Korisnik '" + ime + " " + prezime + "' nije pronađen.");
            return;
        }

        Train train = findTrainByOznaka(oznakaVlaka);
        if (train == null) {
            System.out.println("Greška: Vlak s oznakom '" + oznakaVlaka + "' ne postoji.");
            return;
        }

        if (stanica == null) {
            // Pretplata korisnika na vlak
            if (train.hasObserver(user)) {
                System.out.println("Korisnik je već pretplaćen na vlak " + oznakaVlaka + ".");
            } else {
                train.attachObserver(user);
                System.out.println("Korisnik " + ime + " " + prezime + " dodan za praćenje vlaka " + oznakaVlaka + ".");
            }
        } else {
            // Pretplata korisnika na specifičnu stanicu
            if (!stationExistsForTrain(oznakaVlaka, stanica)) {
                ConfigManager.getInstance().incrementErrorCount();
                System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() +
                        ": Vlak '" + oznakaVlaka + "' ne prolazi kroz stanicu '" + stanica + "'.");
                return;
            }
            StationComposite station = findStationInTrain(train, stanica);

            if (station.hasObserver(user)) {
                System.out.println("Korisnik je već pretplaćen na vlak " + oznakaVlaka + " za stanicu " + stanica + ".");
            } else {
                station.attachObserver(user);
                System.out.println("Korisnik " + ime + " " + prezime + " dodan za praćenje vlaka " + oznakaVlaka + " za stanicu " + stanica + ".");
            }
        }
    }

    private boolean validateInput(String input) {
        return input.matches("^DPK\\s+\\w+\\s+\\w+\\s+-\\s+\\d{1,4}(\\s+-\\s+.+)?$");
    }

    private User findUserByName(String ime, String prezime) {
        return ConfigManager.getInstance().getUsers().stream()
                .filter(user -> user.getIme().equals(ime) && user.getPrezime().equals(prezime))
                .findFirst()
                .orElse(null);
    }

    private Train findTrainByOznaka(String oznakaVlaka) {
        TimeTableComposite vozniRed = ConfigManager.getInstance().getVozniRed();
        for (TimeTableComponent component : vozniRed.getChildren()) {
            if (component instanceof Train && ((Train) component).getOznaka().equals(oznakaVlaka)) {
                return (Train) component;
            }
        }
        return null;
    }

    private StationComposite findStationInTrain(Train train, String stanica) {
        for (TimeTableComponent component : train.getChildren()) {
            if (component instanceof Etapa) {
                Etapa etapa = (Etapa) component;

                // Provjera za početnu stanicu
                for (TimeTableComponent child : etapa.getChildren()) {
                    if (child instanceof StationComposite && ((StationComposite) child).getNazivStanice().equals(stanica)) {
                        return (StationComposite) child;
                    }
                }

                // Provjera za odredišnu stanicu
                for (TimeTableComponent child : etapa.getChildren()) {
                    if (child instanceof StationComposite && ((StationComposite) child).getNazivStanice().equals(stanica)) {
                        return (StationComposite) child;
                    }
                }
            }
        }
        return null;
    }


    private boolean stationExistsForTrain(String oznakaVlaka, String stanica) {
        TimeTableComposite vozniRed = ConfigManager.getInstance().getVozniRed();

        for (TimeTableComponent component : vozniRed.getChildren()) {
            if (component instanceof Train && ((Train) component).getOznaka().equals(oznakaVlaka)) {
                Train train = (Train) component;
                for (TimeTableComponent etapa : train.getChildren()) {
                    if (etapa instanceof org.uzdiz.timeTableComposite.Etapa) {
                        if (((org.uzdiz.timeTableComposite.Etapa) etapa).getPocetnaStanica().equals(stanica) ||
                                ((org.uzdiz.timeTableComposite.Etapa) etapa).getOdredisnaStanica().equals(stanica)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
