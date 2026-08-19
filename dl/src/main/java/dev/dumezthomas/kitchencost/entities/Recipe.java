package dev.dumezthomas.kitchencost.entities;

import dev.dumezthomas.kitchencost.enums.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(name = "idx_recipe_restaurant", columnList = "restaurant_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"restaurant_id", "name"})
        }
)
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
public class Recipe extends RestaurantScoped {

    @NotBlank
    @Column(nullable = false, length = 100)
    @Setter
    private String name;

    @Column(length = 2000)
    @Setter
    private String instructions;

    @Positive
    @Column(nullable = false, precision = 10, scale = 4)
    @Setter
    private BigDecimal yieldQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Unit yieldUnit;

    @Column(nullable = false)
    @Setter
    private boolean archived = false;

    public Recipe(
            String name,
            String instructions,
            BigDecimal yieldQuantity
    ) {

        this.name = name;
        this.instructions = instructions;
        this.yieldQuantity = yieldQuantity;
    }

    public Recipe(
            String name,
            String instructions,
            BigDecimal yieldQuantity,
            Unit yieldUnit
    ) {

        this(name, instructions, yieldQuantity);
        this.yieldUnit = yieldUnit;
    }

    public Recipe(UUID id) {

        this.id = id;
    }
}