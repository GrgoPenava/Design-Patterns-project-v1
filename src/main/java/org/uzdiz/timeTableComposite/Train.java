package org.uzdiz.timeTableComposite;

public class Train extends TimeTableComposite {
    private String vrstaVlaka;

    public Train(String oznakaVlaka, String vrstaVlaka) {
        super(oznakaVlaka);
        this.vrstaVlaka = vrstaVlaka;
    }

    @Override
    public boolean add(TimeTableComponent component) {
        return super.add(component);
    }
}
