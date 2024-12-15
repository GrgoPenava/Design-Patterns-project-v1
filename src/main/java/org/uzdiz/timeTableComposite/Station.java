package org.uzdiz.timeTableComposite;

public class Station extends TimeTableComponent {
    private String nazivStanice;

    public Station(String nazivStanice) {
        this.nazivStanice = nazivStanice;
    }

    @Override
    public void showDetails() {
        System.out.println(nazivStanice);
    }
}
