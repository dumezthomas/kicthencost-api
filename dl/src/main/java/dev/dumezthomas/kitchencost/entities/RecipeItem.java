package dev.dumezthomas.kitchencost.entities;

import dev.dumezthomas.kitchencost.enums.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(
        indexes = {
                @Index(name = "idx_recipe_item_recipe", columnList = "recipe_id")
        }
)
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class RecipeItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_recipe_id")
    private Recipe subRecipe;

    @Positive
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Unit unit;

    public RecipeItem(
            BigDecimal quantity,
            Unit unit
    ) {

        this.quantity = quantity;
        this.unit = unit;
    }

    public boolean isIngredient() {

        return ingredient != null;
    }
}
