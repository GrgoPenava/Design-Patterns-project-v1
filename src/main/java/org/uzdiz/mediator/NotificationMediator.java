package org.uzdiz.mediator;

import org.uzdiz.ConfigManager;
import org.uzdiz.timeTableComposite.Train;

import java.util.HashMap;
import java.util.Map;

public class NotificationMediator implements INotificationMediator {
    private Map<String, Train> trains = new HashMap<>();

    @Override
    public void registerTrain(Train train) {
        trains.put(train.getOznaka(), train);
    }

    @Override
    public void notifySubscribers(String trainOznaka, int delayMinutes) {
        ConfigManager config = ConfigManager.getInstance();
        Train train = trains.get(trainOznaka);
        if (train != null) {
            String message = "Vlak " + trainOznaka + " kasni " + delayMinutes + " minuta.";
            train.notifyObservers(message);
        } else {
            config.incrementErrorCount();
            System.out.println("Greška br. " + config.getErrorCount() + ": Vlak s oznakom '" + trainOznaka + "' nije registriran.");
        }
    }
}
