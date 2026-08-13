package dev.dumezthomas.kitchencost.entities;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.IngredientCategory;
import dev.dumezthomas.kitchencost.enums.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        indexes = {
                @Index(name = "idx_ingredient_restaurant", columnList = "restaurant_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"restaurant_id", "name"})
        }
)
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
public class Ingredient extends RestaurantScoped {

    @NotBlank
    @Column(nullable = false, length = 100)
    @Setter
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IngredientCategory ingredientCategory;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "ingredient_allergen",
            joinColumns = @JoinColumn(name = "ingredient_id")
    )
    @Column(name = "allergen", nullable = false, length = 20)
    private Set<Allergen> allergens = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Unit defaultUnit;

    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 4)
    @Setter
    private BigDecimal currentPriceByDefaultUnit;

    @Column(nullable = false)
    @Setter
    private boolean archived = false;

    public void setAllergens(Set<Allergen> allergens) {

        this.allergens = new HashSet<>(allergens);
    }

    public Ingredient(
            String name,
            Set<Allergen> allergens,
            BigDecimal currentPriceByDefaultUnit
    ) {

        this.name = name;
        this.allergens = new HashSet<>(allergens);
        this.currentPriceByDefaultUnit = currentPriceByDefaultUnit;
    }

    public Ingredient(
            String name,
            IngredientCategory ingredientCategory,
            Set<Allergen> allergens,
            Unit defaultUnit,
            BigDecimal currentPriceByDefaultUnit
    ) {

        this(name, allergens, currentPriceByDefaultUnit);
        this.ingredientCategory = ingredientCategory;
        this.defaultUnit = defaultUnit;
    }
}
