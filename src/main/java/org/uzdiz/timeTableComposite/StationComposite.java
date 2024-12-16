package org.uzdiz.timeTableComposite;

public class StationComposite extends TimeTableComponent {
    private String nazivStanice;
    private Integer idStanice;

    public StationComposite(String nazivStanice, Integer idStanice) {
        this.nazivStanice = nazivStanice;
        this.idStanice = idStanice;
    }

    @Override
    public void showDetails() {
        System.out.println(nazivStanice);
    }

    public Integer getIdStanice() {
        return idStanice;
    }

    public String getNazivStanice() {
        return nazivStanice;
    }
}
