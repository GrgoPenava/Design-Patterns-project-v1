package org.uzdiz.vehicle;

// ConcreteBuilder - služi kao "ConcreteBuilder" u uzorku
public class ConcreteVehicleBuilder implements VehicleBuilder {
    private Vehicle vehicle;

    public ConcreteVehicleBuilder() {
        vehicle = new Vehicle();
    }

    public VehicleBuilder setOznaka(String oznaka) {
        vehicle.setOznaka(oznaka);
        return this;
    }

    public VehicleBuilder setOpis(String opis) {
        vehicle.setOpis(opis);
        return this;
    }

    public VehicleBuilder setProizvodac(String proizvodac) {
        vehicle.setProizvodac(proizvodac);
        return this;
    }

    public VehicleBuilder setGodina(String godina) {
        vehicle.setGodina(godina);
        return this;
    }

    public VehicleBuilder setNamjera(String namjera) {
        vehicle.setNamjera(namjera);
        return this;
    }

    public VehicleBuilder setVrstaPrijevoza(String vrstaPrijevoza) {
        vehicle.setVrstaPrijevoza(vrstaPrijevoza);
        return this;
    }

    public VehicleBuilder setVrstaPogona(String vrstaPogona) {
        vehicle.setVrstaPogona(vrstaPogona);
        return this;
    }

    public VehicleBuilder setMaksBrzina(String maksBrzina) {
        vehicle.setMaksBrzina(maksBrzina);
        return this;
    }

    public VehicleBuilder setMaksSnaga(String maksSnaga) {
        vehicle.setMaksSnaga(maksSnaga);
        return this;
    }

    public VehicleBuilder setBrojSjedecihMjesta(String brojSjedecihMjesta) {
        vehicle.setBrojSjedecihMjesta(brojSjedecihMjesta);
        return this;
    }

    public VehicleBuilder setBrojStajacihMjesta(String brojStajacihMjesta) {
        vehicle.setBrojStajacihMjesta(brojStajacihMjesta);
        return this;
    }

    public VehicleBuilder setBrojBicikala(String brojBicikala) {
        vehicle.setBrojBicikala(brojBicikala);
        return this;
    }

    public VehicleBuilder setBrojKreveta(String brojKreveta) {
        vehicle.setBrojKreveta(brojKreveta);
        return this;
    }

    public VehicleBuilder setBrojAutomobila(String brojAutomobila) {
        vehicle.setBrojAutomobila(brojAutomobila);
        return this;
    }

    public VehicleBuilder setNosivost(String nosivost) {
        vehicle.setNosivost(nosivost);
        return this;
    }

    public VehicleBuilder setPovrsina(String povrsina) {
        vehicle.setPovrsina(povrsina);
        return this;
    }

    public VehicleBuilder setZapremnina(String zapremnina) {
        vehicle.setZapremnina(zapremnina);
        return this;
    }

    public VehicleBuilder setStatus(String status) {
        vehicle.setStatus(status);
        return this;
    }

    public Vehicle build() {
        return vehicle;
    }
}
