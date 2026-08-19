package dev.dumezthomas.kitchencost.entities;

import dev.dumezthomas.kitchencost.enums.MenuItemType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(
        indexes = {
                @Index(name = "idx_menu_item_restaurant", columnList = "restaurant_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"restaurant_id", "name"})
        }
)
@NoArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
public class MenuItem extends RestaurantScoped {

    @NotBlank
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 250)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MenuItemType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @PositiveOrZero
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean archived = false;

    public MenuItem(
            String name,
            String description,
            MenuItemType type,
            Recipe recipe,
            BigDecimal price) {

        this.name = name;
        this.description = description;
        this.type = type;
        this.recipe = recipe;
        this.price = price;
    }
}
