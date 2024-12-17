package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.timeTableComposite.TimeTableComposite;
import org.uzdiz.timeTableComposite.TimeTableComponent;
import org.uzdiz.timeTableComposite.Train;
import org.uzdiz.user.User;

public class DPKCommand implements Command {

    @Override
    public void execute(String input) {
        if (!validateInput(input)) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() +
                    ": Neispravan format naredbe. Očekuje se 'DPK ime prezime - oznakaVlaka [- stanica]'.");
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
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() +
                    ": Korisnik '" + ime + " " + prezime + "' nije pronađen.");
            return;
        }

        if (!trainExists(oznakaVlaka)) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() +
                    ": Vlak s oznakom '" + oznakaVlaka + "' ne postoji.");
            return;
        }

        if (stanica != null && !stationExistsForTrain(oznakaVlaka, stanica)) {
            ConfigManager.getInstance().incrementErrorCount();
            System.out.println("Greška br. " + ConfigManager.getInstance().getErrorCount() +
                    ": Vlak '" + oznakaVlaka + "' ne prolazi kroz stanicu '" + stanica + "'.");
            return;
        }

        if (ConfigManager.getInstance().isUserSubscribedToTrain(user.getId(), oznakaVlaka)) {
            if (stanica == null) {
                System.out.println("Korisnik je već pretplaćen na vlak " + oznakaVlaka + ".");
                return;
            }
            if (ConfigManager.getInstance().isUserSubscribedToStation(user.getId(), oznakaVlaka, stanica)) {
                System.out.println("Korisnik je već pretplaćen na vlak " + oznakaVlaka + " za stanicu " + stanica + ".");
                return;
            }
        }

        if (ConfigManager.getInstance().addSubscription(user.getId(), oznakaVlaka, stanica)) {
            if (stanica != null) {
                System.out.println("Korisnik " + ime + " " + prezime + " dodan za praćenje vlaka " + oznakaVlaka +
                        " za stanicu " + stanica + ".");
            } else {
                System.out.println("Korisnik " + ime + " " + prezime + " dodan za praćenje vlaka " + oznakaVlaka + ".");
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

    private boolean trainExists(String oznakaVlaka) {
        TimeTableComposite vozniRed = ConfigManager.getInstance().getVozniRed();
        if (vozniRed == null) {
            return false;
        }

        for (TimeTableComponent component : vozniRed.getChildren()) {
            if (component instanceof Train && ((Train) component).getOznaka().equals(oznakaVlaka)) {
                return true;
            }
        }
        return false;
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
