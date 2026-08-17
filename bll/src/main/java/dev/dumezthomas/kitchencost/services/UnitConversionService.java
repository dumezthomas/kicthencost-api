package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.enums.Unit;

import java.math.BigDecimal;

public interface UnitConversionService {

    void validateCompatibility(Unit from, Unit to);

    BigDecimal convert(BigDecimal quantity, Unit from, Unit to);
}
