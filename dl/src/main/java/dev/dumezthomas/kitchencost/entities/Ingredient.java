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
@Table(indexes = {
        @Index(name = "idx_ingredient_restaurant", columnList = "restaurant_id")
})
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
    @Setter
    private IngredientCategory ingredientCategory;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "ingredient_allergen",
            joinColumns = @JoinColumn(name = "ingredient_id")
    )
    @Column(name = "allergen", nullable = false, length = 20)
    private Set<Allergen> allergens = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Setter
    private Unit defaultUnit;

    @PositiveOrZero
    @Column(nullable = false, precision = 10, scale = 4)
    @Setter
    private BigDecimal currentPriceByDefaultUnit;

    @Column(nullable = false)
    @Setter
    private boolean archived = false;

    public void addAllergen(Allergen allergen) {

        this.allergens.add(allergen);
    }

    public void removeAllergen(Allergen allergen) {

        this.allergens.remove(allergen);
    }
}
