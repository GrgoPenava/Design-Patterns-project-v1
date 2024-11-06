package org.uzdiz.composition;

public class ConcreteCompositionBuilder implements CompositionBuilder {
    private Composition composition;

    public ConcreteCompositionBuilder() {
        composition = new Composition();
    }

    public CompositionBuilder setOznakaVozila(String oznakaVozila) {
        composition.setOznakaVozila(oznakaVozila);
        return this;
    }

    public CompositionBuilder setOznaka(String oznaka) {
        composition.setOznaka(oznaka);
        return this;
    }

    public CompositionBuilder setUloga(String uloga) {
        composition.setUloga(uloga);
        return this;
    }

    public Composition build() {
        return composition;
    }
}
