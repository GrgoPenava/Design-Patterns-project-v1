package org.uzdiz.vehicle;

// Director - služi kao "Director" u uzorku
public class VehicleDirector {
    private VehicleBuilder builder;

    public VehicleDirector(VehicleBuilder builder) {
        this.builder = builder;
    }

    public Vehicle constructStandardVehicle(String oznaka, String opis, String proizvodac, String godina) {
        return builder.setOznaka(oznaka)
                .setOpis(opis)
                .setProizvodac(proizvodac)
                .setGodina(godina)
                .build();
    }

    public Vehicle constructFullFeatureVehicle(String oznaka, String opis, String proizvodac, String godina, String maksBrzina, String maksSnaga) {
        return builder.setOznaka(oznaka)
                .setOpis(opis)
                .setProizvodac(proizvodac)
                .setGodina(godina)
                .setMaksBrzina(maksBrzina)
                .setMaksSnaga(maksSnaga)
                .build();
    }
}
