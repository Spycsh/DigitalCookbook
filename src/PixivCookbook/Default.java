package PixivCookbook;

import PixivCookbook.Controller.DBController;
import PixivCookbook.Model.CookBook;
import PixivCookbook.Model.Ingredient;
import PixivCookbook.Model.Recipe;

import java.sql.SQLException;

public class Default {
	private static Recipe createDefaultRecipe() {
		Recipe recipe = new Recipe("Default", "Default", 4);
		recipe.addIngredient(new Ingredient("default ingredient", 1.0, "spoon"));
		recipe.addPreparationStep("Enter your steps here.");
		recipe.setPreparationTime(10);
		recipe.setCookingTime(10);
		recipe.restoreImg();

		return recipe;
	}


public static void main(String[] args) {
	CookBook cb = new CookBook("Chinese Cuisine");
	DBController databaseConnection = DBController.getInstance();
	databaseConnection.run();
	
	cb.add(createDefaultRecipe());
	
	Recipe recipe_Default = cb.getRecipe("Default");
	
	databaseConnection.addRecipetoDatabase(recipe_Default,databaseConnection.assignID());
	
	// add default forbidden pair
	databaseConnection.addForbiddenPair("persimmon","milk");
	databaseConnection.addForbiddenPair("red carrot", "wine");
	databaseConnection.addForbiddenPair("Bamboo shoots", "tofu");
	
	try {
		databaseConnection.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	


	}
}