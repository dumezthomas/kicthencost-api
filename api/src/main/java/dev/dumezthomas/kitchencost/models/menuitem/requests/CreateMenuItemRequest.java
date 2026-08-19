package dev.dumezthomas.kitchencost.models.menuitem.requests;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.enums.MenuItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateMenuItemRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 250)
        String description,

        @NotNull
        MenuItemType type,

        @NotNull
        UUID recipeId,

        @NotNull
        @PositiveOrZero
        BigDecimal price
) {

    public MenuItem toMenuItem() {

        return new MenuItem(
                name,
                description,
                type,
                new Recipe(recipeId),
                price
        );
    }
}