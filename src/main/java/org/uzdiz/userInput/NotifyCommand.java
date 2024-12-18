package org.uzdiz.userInput;

import org.uzdiz.ConfigManager;
import org.uzdiz.mediator.INotificationMediator;

public class NotifyCommand implements Command {
    ConfigManager config = ConfigManager.getInstance();
    private INotificationMediator mediator;

    public NotifyCommand(INotificationMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void execute(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length != 3) {
            config.incrementErrorCount();
            System.out.println("Greška br. " + config.getErrorCount() + ": Neispravan format komande. Očekuje se 'NOT brojVlaka minute'.");
            return;
        }

        String trainOznaka = parts[1];
        int delayMinutes;

        try {
            delayMinutes = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            config.incrementErrorCount();
            System.out.println("Greška br. " + config.getErrorCount() + ": Vrijednost za minute mora biti broj.");
            return;
        }

        mediator.notifySubscribers(trainOznaka, delayMinutes);
    }
}
