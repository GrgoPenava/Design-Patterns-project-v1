package org.uzdiz.composition;

public interface CompositionBuilder {
    CompositionBuilder setOznaka(String oznaka);

    CompositionBuilder setOznakaVozila(String oznakaVozila);

    CompositionBuilder setUloga(String uloga);

    Composition build();
}
