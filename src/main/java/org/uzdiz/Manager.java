package org.uzdiz;

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

        config.setStationFilePath("path/to/stationFile.csv");
        System.out.println("Putanja datoteke: " + config.getStationFilePath());

        Map<String, Command> commands = new HashMap<>();
        commands.put("IP", new ListRailwaysCommand());
        commands.put("ISP", new ListStationsCommand());

        Scanner scanner = new Scanner(System.in);
        String commandInput;

        while (true) {
            System.out.print("Unesite komandu: ");
            //commandInput = scanner.nextLine().trim().toUpperCase();
            commandInput = scanner.nextLine().trim();

            if (commandInput.equals("Q")) {
                System.out.println("Prekid programa.");
                break;
            }

            Command command = commands.get(commandInput);
            if (command != null) {
                CommandExecutor executor = new CommandExecutor(command);
                executor.executeCommand();
            } else {
                System.out.println("Nepoznata komanda. Pokusajte ponovno.");
            }
        }

        scanner.close();
    }
}