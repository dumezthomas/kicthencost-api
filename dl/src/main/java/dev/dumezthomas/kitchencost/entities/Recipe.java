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

@Entity
@Table(indexes = {
        @Index(name = "idx_recipe_restaurant", columnList = "restaurant_id")
})
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class Recipe extends RestaurantScoped {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @Positive
    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal yieldQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Unit yieldUnit;

    @Column(nullable = false)
    private boolean archived = false;
}
