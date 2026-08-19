package dev.dumezthomas.kitchencost.models.menuitem.responses;

import dev.dumezthomas.kitchencost.enums.Allergen;
import dev.dumezthomas.kitchencost.enums.DietType;
import dev.dumezthomas.kitchencost.enums.MenuItemType;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record PublicMenuItemResponse(

        UUID id,
        String name,
        String description,
        MenuItemType type,

        BigDecimal price,

        DietType dietType,
        Set<Allergen> allergens
) {

}