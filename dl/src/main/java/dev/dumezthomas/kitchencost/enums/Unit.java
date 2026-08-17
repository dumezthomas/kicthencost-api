package dev.dumezthomas.kitchencost.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public enum Unit {

    MG(MeasurementType.WEIGHT, BigDecimal.valueOf(0.001)),
    G(MeasurementType.WEIGHT, BigDecimal.ONE),
    KG(MeasurementType.WEIGHT, BigDecimal.valueOf(1000)),

    ML(MeasurementType.VOLUME, BigDecimal.ONE),
    L(MeasurementType.VOLUME, BigDecimal.valueOf(1000)),

    WEDGE(MeasurementType.COUNT, BigDecimal.valueOf(0.125)),
    QUARTER(MeasurementType.COUNT, BigDecimal.valueOf(0.25)),
    HALF(MeasurementType.COUNT, BigDecimal.valueOf(0.5)),
    PIECE(MeasurementType.COUNT, BigDecimal.ONE);

    private final MeasurementType type;
    private final BigDecimal conversionFactor;
}