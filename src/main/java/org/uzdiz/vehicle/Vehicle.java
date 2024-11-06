package org.uzdiz.vehicle;

// Product - služi kao "Product" u uzorku
public class Vehicle {
    private String oznaka;
    private String opis;
    private String proizvodac;
    private String godina;
    private String namjera;
    private String vrstaPrijevoza;
    private String vrstaPogona;
    private String maksBrzina;
    private String maksSnaga;
    private String brojSjedecihMjesta;
    private String brojStajacihMjesta;
    private String brojBicikala;
    private String brojKreveta;
    private String brojAutomobila;
    private String nosivost;
    private String povrsina;
    private String zapremnina;
    private String status;

    public Vehicle() {
    }

    public String getOznaka() {
        return oznaka;
    }

    public Vehicle setOznaka(String oznaka) {
        this.oznaka = oznaka;
        return this;
    }

    public String getOpis() {
        return opis;
    }

    public Vehicle setOpis(String opis) {
        this.opis = opis;
        return this;
    }

    public String getProizvodac() {
        return proizvodac;
    }

    public Vehicle setProizvodac(String proizvodac) {
        this.proizvodac = proizvodac;
        return this;
    }

    public String getGodina() {
        return godina;
    }

    public Vehicle setGodina(String godina) {
        this.godina = godina;
        return this;
    }

    public String getNamjera() {
        return namjera;
    }

    public Vehicle setNamjera(String namjera) {
        this.namjera = namjera;
        return this;
    }

    public String getVrstaPrijevoza() {
        return vrstaPrijevoza;
    }

    public Vehicle setVrstaPrijevoza(String vrstaPrijevoza) {
        this.vrstaPrijevoza = vrstaPrijevoza;
        return this;
    }

    public String getVrstaPogona() {
        return vrstaPogona;
    }

    public Vehicle setVrstaPogona(String vrstaPogona) {
        this.vrstaPogona = vrstaPogona;
        return this;
    }

    public String getMaksBrzina() {
        return maksBrzina;
    }

    public Vehicle setMaksBrzina(String maksBrzina) {
        this.maksBrzina = maksBrzina;
        return this;
    }

    public String getMaksSnaga() {
        return maksSnaga;
    }

    public Vehicle setMaksSnaga(String maksSnaga) {
        this.maksSnaga = maksSnaga;
        return this;
    }

    public String getBrojSjedecihMjesta() {
        return brojSjedecihMjesta;
    }

    public Vehicle setBrojSjedecihMjesta(String brojSjedecihMjesta) {
        this.brojSjedecihMjesta = brojSjedecihMjesta;
        return this;
    }

    public String getBrojStajacihMjesta() {
        return brojStajacihMjesta;
    }

    public Vehicle setBrojStajacihMjesta(String brojStajacihMjesta) {
        this.brojStajacihMjesta = brojStajacihMjesta;
        return this;
    }

    public String getBrojBicikala() {
        return brojBicikala;
    }

    public Vehicle setBrojBicikala(String brojBicikala) {
        this.brojBicikala = brojBicikala;
        return this;
    }

    public String getBrojKreveta() {
        return brojKreveta;
    }

    public Vehicle setBrojKreveta(String brojKreveta) {
        this.brojKreveta = brojKreveta;
        return this;
    }

    public String getBrojAutomobila() {
        return brojAutomobila;
    }

    public Vehicle setBrojAutomobila(String brojAutomobila) {
        this.brojAutomobila = brojAutomobila;
        return this;
    }

    public String getNosivost() {
        return nosivost;
    }

    public Vehicle setNosivost(String nosivost) {
        this.nosivost = nosivost;
        return this;
    }

    public String getPovrsina() {
        return povrsina;
    }

    public Vehicle setPovrsina(String povrsina) {
        this.povrsina = povrsina;
        return this;
    }

    public String getZapremnina() {
        return zapremnina;
    }

    public Vehicle setZapremnina(String zapremnina) {
        this.zapremnina = zapremnina;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Vehicle setStatus(String status) {
        this.status = status;
        return this;
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
                ", vrstaPogona='" + vrstaPogona + '\'' +
                ", maksBrzina='" + maksBrzina + '\'' +
                ", maksSnaga='" + maksSnaga + '\'' +
                ", brojSjedecihMjesta='" + brojSjedecihMjesta + '\'' +
                ", brojStajacihMjesta='" + brojStajacihMjesta + '\'' +
                ", brojBicikala='" + brojBicikala + '\'' +
                ", brojKreveta='" + brojKreveta + '\'' +
                ", brojAutomobila='" + brojAutomobila + '\'' +
                ", nosivost='" + nosivost + '\'' +
                ", povrsina='" + povrsina + '\'' +
                ", zapremnina='" + zapremnina + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}