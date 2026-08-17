package dev.dumezthomas.kitchencost.services.impls;

import dev.dumezthomas.kitchencost.enums.Unit;
import dev.dumezthomas.kitchencost.exceptions.InvalidOperationException;
import dev.dumezthomas.kitchencost.services.UnitConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class UnitConversionServiceImpl implements UnitConversionService {

    @Override
    public void validateCompatibility(Unit from, Unit to) {

        if (from.getType() != to.getType()) {

            throw new InvalidOperationException(
                    String.format("Unit '%s' is not compatible with unit '%s'.", from, to)
            );
        }
    }

    @Override
    public BigDecimal convert(BigDecimal quantity, Unit from, Unit to) {

        validateCompatibility(from, to);

        return quantity
                .multiply(from.getConversionFactor())
                .divide(to.getConversionFactor(), 8, RoundingMode.HALF_UP);
    }
}