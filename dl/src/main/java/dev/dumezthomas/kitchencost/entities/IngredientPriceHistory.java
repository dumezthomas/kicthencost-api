package dev.dumezthomas.kitchencost.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(indexes = {
        @Index(name = "idx_price_history_ingredient", columnList = "ingredient_id")
})
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class IngredientPriceHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal priceByDefaultUnit;

    @Column(nullable = false)
    private LocalDate recordedAt;
}
