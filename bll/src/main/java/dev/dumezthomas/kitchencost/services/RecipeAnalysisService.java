package dev.dumezthomas.kitchencost.services;

import dev.dumezthomas.kitchencost.entities.Recipe;
import dev.dumezthomas.kitchencost.results.RecipeAnalysis;
import dev.dumezthomas.kitchencost.results.RecipeItemAnalysis;

public interface RecipeAnalysisService {

    RecipeAnalysis analyze(Recipe recipe);

    RecipeItemAnalysis analyzeSummary(Recipe recipe);
}
