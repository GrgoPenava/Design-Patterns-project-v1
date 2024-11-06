package org.uzdiz.vehicle;

// Builder interface - služi kao "Builder" u uzorku
public interface VehicleBuilder {
    VehicleBuilder setOznaka(String oznaka);

    VehicleBuilder setOpis(String opis);

    VehicleBuilder setProizvodac(String proizvodac);

    VehicleBuilder setGodina(String godina);

    VehicleBuilder setNamjera(String namjera);

    VehicleBuilder setVrstaPrijevoza(String vrstaPrijevoza);

    VehicleBuilder setVrstaPogona(String vrstaPogona);

    VehicleBuilder setMaksBrzina(String maksBrzina);

    VehicleBuilder setMaksSnaga(String maksSnaga);

    VehicleBuilder setBrojSjedecihMjesta(String brojSjedecihMjesta);

    VehicleBuilder setBrojStajacihMjesta(String brojStajacihMjesta);

    VehicleBuilder setBrojBicikala(String brojBicikala);

    VehicleBuilder setBrojKreveta(String brojKreveta);

    VehicleBuilder setBrojAutomobila(String brojAutomobila);

    VehicleBuilder setNosivost(String nosivost);

    VehicleBuilder setPovrsina(String povrsina);

    VehicleBuilder setZapremnina(String zapremnina);

    VehicleBuilder setStatus(String status);

    Vehicle build();
}
