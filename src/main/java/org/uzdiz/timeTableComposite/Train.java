package org.uzdiz.timeTableComposite;

public class Train extends TimeTableComposite {
    public Train(String oznakaVlaka) {
        super(oznakaVlaka);
    }

    @Override
    public boolean add(TimeTableComponent component) {
        return super.add(component);
    }
}
