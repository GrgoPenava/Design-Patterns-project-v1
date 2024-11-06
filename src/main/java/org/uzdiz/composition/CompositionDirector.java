package org.uzdiz.composition;

public class CompositionDirector {
    private CompositionBuilder builder;

    public CompositionDirector(CompositionBuilder builder) {
        this.builder = builder;
    }

    public Composition createBasicComposition(String oznaka, String oznakaVozila, String uloga) {
        return builder.setOznaka(oznaka)
                .setOznakaVozila(oznakaVozila)
                .setUloga(uloga)
                .build();
    }
}
