package dev.dumezthomas.kitchencost.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class IngredientPriceHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal priceByDefaultUnit;

    @Column(nullable = false)
    private LocalDate recordedAt;
}
