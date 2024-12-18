package org.uzdiz.mediator;

import org.uzdiz.timeTableComposite.Train;

public interface INotificationMediator {
    void registerTrain(Train train);

    void notifySubscribers(String trainOznaka, int delayMinutes);
}

