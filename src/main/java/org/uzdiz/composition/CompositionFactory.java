package org.uzdiz.composition;

import org.uzdiz.vehicle.Vehicle;

import java.util.List;

public interface CompositionFactory {
    Composition createComposition(String id, List<Vehicle> vehicles);
}




