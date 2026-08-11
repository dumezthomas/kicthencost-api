package dev.dumezthomas.kitchencost.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum Unit {

    MG(MeasurementType.WEIGHT, BigDecimal.valueOf(0.001)),
    G(MeasurementType.WEIGHT, BigDecimal.valueOf(1)),
    KG(MeasurementType.WEIGHT, BigDecimal.valueOf(1000)),

    ML(MeasurementType.VOLUME, BigDecimal.valueOf(0.001)),
    L(MeasurementType.VOLUME, BigDecimal.valueOf(1)),

    PIECE(MeasurementType.COUNT, BigDecimal.valueOf(1));

    private final MeasurementType type;
    private final BigDecimal conversionFactor;
}