package org.uzdiz.vehicle;

public abstract class Vehicle {
    private String oznaka;
    private String opis;
    private String proizvodac;
    private String godina;
    private String namjera;
    private String vrstaPrijevoza;
    private char vrstaPogona;
    private Integer maksBrzina;
    private Double maksSnaga;
    private Integer brojSjedecihMjesta;
    private Integer brojStajacihMjesta;
    private Integer brojBicikala;
    private Integer brojKreveta;
    private Integer brojAutomobila;
    private Double nosivost;
    private Double povrsina;
    private Double zapremnina;
    private char status;

    // Constructor
    public Vehicle(String oznaka, String opis) {
        this.oznaka = oznaka;
        this.opis = opis;
    }

    // Getters and Setters
    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getProizvodac() {
        return proizvodac;
    }

    public void setProizvodac(String proizvodac) {
        this.proizvodac = proizvodac;
    }

    public String getGodina() {
        return godina;
    }

    public void setGodina(String godina) {
        this.godina = godina;
    }

    public String getNamjera() {
        return namjera;
    }

    public void setNamjera(String namjera) {
        this.namjera = namjera;
    }

    public String getVrstaPrijevoza() {
        return vrstaPrijevoza;
    }

    public void setVrstaPrijevoza(String vrstaPrijevoza) {
        this.vrstaPrijevoza = vrstaPrijevoza;
    }

    public char getVrstaPogona() {
        return vrstaPogona;
    }

    public void setVrstaPogona(char vrstaPogona) {
        this.vrstaPogona = vrstaPogona;
    }

    public Integer getMaksBrzina() {
        return maksBrzina;
    }

    public void setMaksBrzina(Integer maksBrzina) {
        this.maksBrzina = maksBrzina;
    }

    public Double getMaksSnaga() {
        return maksSnaga;
    }

    public void setMaksSnaga(Double maksSnaga) {
        this.maksSnaga = maksSnaga;
    }

    public Integer getBrojSjedecihMjesta() {
        return brojSjedecihMjesta;
    }

    public void setBrojSjedecihMjesta(Integer brojSjedecihMjesta) {
        this.brojSjedecihMjesta = brojSjedecihMjesta;
    }

    public Integer getBrojStajacihMjesta() {
        return brojStajacihMjesta;
    }

    public void setBrojStajacihMjesta(Integer brojStajacihMjesta) {
        this.brojStajacihMjesta = brojStajacihMjesta;
    }

    public Integer getBrojBicikala() {
        return brojBicikala;
    }

    public void setBrojBicikala(Integer brojBicikala) {
        this.brojBicikala = brojBicikala;
    }

    public Integer getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(Integer brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public Integer getBrojAutomobila() {
        return brojAutomobila;
    }

    public void setBrojAutomobila(Integer brojAutomobila) {
        this.brojAutomobila = brojAutomobila;
    }

    public Double getNosivost() {
        return nosivost;
    }

    public void setNosivost(Double nosivost) {
        this.nosivost = nosivost;
    }

    public Double getPovrsina() {
        return povrsina;
    }

    public void setPovrsina(Double povrsina) {
        this.povrsina = povrsina;
    }

    public Double getZapremnina() {
        return zapremnina;
    }

    public void setZapremnina(Double zapremnina) {
        this.zapremnina = zapremnina;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "oznaka='" + oznaka + '\'' +
                ", opis='" + opis + '\'' +
                ", proizvodac='" + proizvodac + '\'' +
                ", godina='" + godina + '\'' +
                ", namjera='" + namjera + '\'' +
                ", vrstaPrijevoza='" + vrstaPrijevoza + '\'' +
                ", vrstaPogona=" + vrstaPogona +
                ", maksBrzina=" + maksBrzina +
                ", maksSnaga=" + maksSnaga +
                ", brojSjedecihMjesta=" + brojSjedecihMjesta +
                ", brojStajacihMjesta=" + brojStajacihMjesta +
                ", brojBicikala=" + brojBicikala +
                ", brojKreveta=" + brojKreveta +
                ", brojAutomobila=" + brojAutomobila +
                ", nosivost=" + nosivost +
                ", povrsina=" + povrsina +
                ", zapremnina=" + zapremnina +
                ", status=" + status +
                '}';
    }
}
