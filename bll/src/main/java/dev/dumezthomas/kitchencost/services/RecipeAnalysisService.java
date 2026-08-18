package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.results.RecipeAnalysis;

public interface RecipeAnalysisService {

    RecipeAnalysis analyze(Recipe recipe);
}
