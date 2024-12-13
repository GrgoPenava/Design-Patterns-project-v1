package org.uzdiz.station;

public abstract class Station {
    private Integer id;
    private String naziv;
    private String oznakaPruge;
    private String vrstaStanice;
    private String statusStanice;
    private boolean putnici;
    private boolean roba;
    private String kategorijaPruge;
    private int brojPerona;
    private String vrstaPruge;
    private int brojKolosjeka;
    private double DOPoOsovini;
    private double DOPoDuznomM;
    private String statusPruge;
    private int duzina;

    public Station(Integer id, String naziv, String oznakaPruge, String vrstaStanice) {
        this.id = id;
        this.naziv = naziv;
        this.oznakaPruge = oznakaPruge;
        this.vrstaStanice = vrstaStanice;
    }

    public void setAdditionalAttributes(String statusStanice, boolean putnici, boolean roba,
                                        String kategorijaPruge, int brojPerona, String vrstaPruge,
                                        int brojKolosjeka, double DOPoOsovini, double DOPoDuznomM,
                                        String statusPruge, int duzina) {
        this.statusStanice = statusStanice;
        this.putnici = putnici;
        this.roba = roba;
        this.kategorijaPruge = kategorijaPruge;
        this.brojPerona = brojPerona;
        this.vrstaPruge = vrstaPruge;
        this.brojKolosjeka = brojKolosjeka;
        this.DOPoOsovini = DOPoOsovini;
        this.DOPoDuznomM = DOPoDuznomM;
        this.statusPruge = statusPruge;
        this.duzina = duzina;
    }

    public String getnaziv() {
        return naziv;
    }

    public Integer getId() {
        return id;
    }

    public void setnaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getoznakaPruge() {
        return oznakaPruge;
    }

    public void setoznakaPruge(String oznakaPruge) {
        this.oznakaPruge = oznakaPruge;
    }

    public String getvrstaStanice() {
        return vrstaStanice;
    }

    public void setvrstaStanice(String vrstaStanice) {
        this.vrstaStanice = vrstaStanice;
    }

    public String getstatusStanice() {
        return statusStanice;
    }

    public void setstatusStanice(String statusStanice) {
        this.statusStanice = statusStanice;
    }

    public boolean isputnici() {
        return putnici;
    }

    public void setputnici(boolean putnici) {
        this.putnici = putnici;
    }

    public boolean isroba() {
        return roba;
    }

    public void setroba(boolean roba) {
        this.roba = roba;
    }

    public String getkategorijaPruge() {
        return kategorijaPruge;
    }

    public void setkategorijaPruge(String kategorijaPruge) {
        this.kategorijaPruge = kategorijaPruge;
    }

    public int getbrojPerona() {
        return brojPerona;
    }

    public void setbrojPerona(int brojPerona) {
        this.brojPerona = brojPerona;
    }

    public String getvrstaPruge() {
        return vrstaPruge;
    }

    public void setvrstaPruge(String vrstaPruge) {
        this.vrstaPruge = vrstaPruge;
    }

    public int getbrojKolosjeka() {
        return brojKolosjeka;
    }

    public void setbrojKolosjeka(int brojKolosjeka) {
        this.brojKolosjeka = brojKolosjeka;
    }

    public double getDOPoOsovini() {
        return DOPoOsovini;
    }

    public void setDOPoOsovini(double DOPoOsovini) {
        this.DOPoOsovini = DOPoOsovini;
    }

    public double getDOPoDuznomM() {
        return DOPoDuznomM;
    }

    public void setDOPoDuznomM(double DOPoDuznomM) {
        this.DOPoDuznomM = DOPoDuznomM;
    }

    public String getstatusPruge() {
        return statusPruge;
    }

    public void setstatusPruge(String statusPruge) {
        this.statusPruge = statusPruge;
    }

    public int getduzina() {
        return duzina;
    }

    public void setduzina(int duzina) {
        this.duzina = duzina;
    }
}
