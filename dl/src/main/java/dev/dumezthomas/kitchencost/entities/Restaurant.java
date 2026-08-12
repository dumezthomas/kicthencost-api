package dev.dumezthomas.kitchencost.entities;

import dev.dumezthomas.kitchencost.enums.CuisineType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class Restaurant extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 250)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CuisineType cuisineType = CuisineType.OTHER;

    @Positive
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal targetFoodCostPercentage = BigDecimal.valueOf(30);

    @Positive
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal warningFoodCostPercentage = BigDecimal.valueOf(35);

    @Positive
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal criticalFoodCostPercentage = BigDecimal.valueOf(40);
}
