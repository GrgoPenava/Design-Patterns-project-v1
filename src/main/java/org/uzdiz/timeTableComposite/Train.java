package org.uzdiz.timeTableComposite;

import org.uzdiz.ConfigManager;
import org.uzdiz.mediator.INotificationMediator;
import org.uzdiz.observer.Observer;
import org.uzdiz.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class Train extends TimeTableComposite implements Subject {
    private String vrstaVlaka;
    private List<Observer> observers = new ArrayList<>();
    private INotificationMediator mediator;

    public Train(String oznakaVlaka, String vrstaVlaka) {
        super(oznakaVlaka);
        this.vrstaVlaka = vrstaVlaka;
        this.mediator = ConfigManager.getInstance().getMediator();
    }

    @Override
    public boolean add(TimeTableComponent component) {
        return super.add(component);
    }

    public String getVrstaVlaka() {
        return vrstaVlaka;
    }

    @Override
    public void attachObserver(Observer observer) {
        this.observers.add(observer);
        mediator.registerTrain(this);
    }

    @Override
    public void detachObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : this.observers) {
            observer.update(message);
        }
    }

    public boolean hasObserver(Observer observer) {
        return observers.contains(observer);
    }

}
