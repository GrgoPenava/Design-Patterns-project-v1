package org.uzdiz.composition;

import org.uzdiz.vehicle.Vehicle;

import java.util.List;

public class PassengerCompositionFactory implements CompositionFactory {
    @Override
    public Composition createComposition(String id, List<Vehicle> vehicles) {
        return new Composition(id, vehicles);
    }
}