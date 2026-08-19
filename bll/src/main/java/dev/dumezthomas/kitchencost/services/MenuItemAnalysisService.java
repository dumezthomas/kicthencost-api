package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.MenuItem;
import dev.dumezthomas.kitchencost.results.MenuItemAnalysis;

public interface MenuItemAnalysisService {

    MenuItemAnalysis analyze(MenuItem menuItem);
}
