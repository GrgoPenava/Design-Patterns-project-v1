package org.uzdiz;

import org.uzdiz.reader.RailwayCsvAdapter;
import org.uzdiz.reader.StationCsvAdapter;
import org.uzdiz.userInput.Command;
import org.uzdiz.userInput.CommandExecutor;
import org.uzdiz.userInput.ListRailwaysCommand;
import org.uzdiz.userInput.ListStationsCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Manager {
    public static void main(String[] args) {
        ConfigManager config = ConfigManager.getInstance();

        parseCommandLineArgs(args, config);

        if (!validateConfig(config)) {
            System.out.println("Greška: Svi argumenti (--zs, --zps, --zk) su obavezni.");
            config.incrementErrorCount();
            return;
        }

        new StationCsvAdapter().loadData(config.getStationFilePath());
        new RailwayCsvAdapter().loadData(config.getRailwayFilePath());

        Map<String, Command> commands = new HashMap<>();
        commands.put("IP", new ListRailwaysCommand());
        commands.put("ISP", new ListStationsCommand());

        Scanner scanner = new Scanner(System.in);
        String commandInput;

        // Unutar while petlje
        while (true) {
            System.out.print("Unesite komandu: ");
            String userInput = scanner.nextLine().trim();

            if (userInput.equals("Q")) {
                System.out.println("Prekid programa.");
                break;
            }

            Command command = commands.get(userInput.split(" ")[0]);
            if (command != null) {
                CommandExecutor executor = new CommandExecutor(command);
                executor.executeCommand(userInput);
            } else {
                config.incrementErrorCount();
                System.out.println("Nepoznata komanda. Pokušajte ponovno.");
            }
        }

        scanner.close();

    }

    private static void parseCommandLineArgs(String[] args, ConfigManager config) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--zs":
                    if (i + 1 < args.length) {
                        config.setStationFilePath(args[++i]);
                    } else {
                        config.incrementErrorCount();
                        //TODO brojac dodat
                        System.out.println("Nedostaje putanja za --zs opciju");
                    }
                    break;
                case "--zps":
                    if (i + 1 < args.length) {
                        config.setRailwayFilePath(args[++i]);
                    } else {
                        config.incrementErrorCount();
                        //TODO brojac dodat
                        System.out.println("Nedostaje putanja za --zps opciju");
                    }
                    break;
                case "--zk":
                    if (i + 1 < args.length) {
                        config.setCompositionFilePath(args[++i]);
                    } else {
                        config.incrementErrorCount();
                        //TODO brojac dodat
                        System.out.println("Nedostaje putanja za --zk opciju");
                    }
                    break;
                default:
                    config.incrementErrorCount();
                    //TODO brojac dodat
                    System.out.println("Nepoznata opcija: " + args[i]);
            }
        }
    }

    private static boolean validateConfig(ConfigManager config) {
        return config.getStationFilePath() != null &&
                config.getRailwayFilePath() != null &&
                config.getCompositionFilePath() != null;
    }
}