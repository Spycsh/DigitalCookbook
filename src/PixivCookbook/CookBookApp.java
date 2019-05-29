package PixivCookbook;
/**
 * 
 */
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import PixivCookbook.Model.SQL_test;
/**
 * @author precision 7710
 *
 */
/**
 * A class for the program entry point and some test recipes.
 * 
 * @author breukerm
 */


public class CookBookApp {

	/**
	 * Creates a Gong Bao Jiding recipe.
	 * 
	 * @return  the new recipe
	 */
	private static Recipe createGongBaoJiding() {
		Recipe recipe = new Recipe("Gong Bao Jiding", "Sichuan Dish", 4);		
		
		recipe.addIngredient(new Ingredient("cornstarch", 1.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("soy sauce", 4.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("chicken breast", 0.5, "kg"));
		recipe.addIngredient(new Ingredient("Shaoxin rice wine", 3.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("sugar", 2.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("chicken stock", 3.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("Chiangang vinegar", 4.0, "teaspoon"));
		recipe.addIngredient(new Ingredient("sesame oil", 4.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("dark soy sauce", 2.0, "teaspoon"));
		recipe.addIngredient(new Ingredient("peanut oil", 3.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("dried red chilis", 12.0, "pieces", "stemmed, halved crosswise, and seeded"));
		recipe.addIngredient(new Ingredient("scallions", 5.0, "pieces", "white part onyl, thickly sliced crosswise"));
		recipe.addIngredient(new Ingredient("garlic", 1.0, "cloves", "peeled, thinly sliced"));
		recipe.addIngredient(new Ingredient("ginger", 0.5, "pieces", "peeled, minced"));
		recipe.addIngredient(new Ingredient("peanuts", 0.5, "cups", "peeled, thinly sliced"));		
		
		recipe.addPreparationStep("Mix together cornstarch and 1 tbsp. of the soy sauce in a medium bowl.");
		recipe.addPreparationStep("Add chicken, toss well, and set aside to marinate for 30 minutes.");
		recipe.addPreparationStep("Meanwhile, mix together the remaining 3 tbsp. soy sauce, rice wine, sugar, stock, vinegar, sesame oil, and dark soy sauce.");
		recipe.addPreparationStep("Set aside.");
		recipe.addPreparationStep("Heat peanut oil in a wok or large nonstick skillet over high heat until just beginning to smoke.");
		recipe.addPreparationStep("Add chilis, half the scallions, garlic, ginger, and chicken and stir-fry until chicken is golden, 3-5 minutes.");
		recipe.addPreparationStep("Add soy sauce mixture and stir-fry until sauce thickens, about 2 minutes.");
		recipe.addPreparationStep("Stir in peanuts.");
		recipe.addPreparationStep("Garnish with remaining scallions.");
		
		recipe.setPreparationTime(30);
		recipe.setCookingTime(10);
		recipe.restoreImg("food.jpg");

		return recipe;
	}
	
	/**
	 * Creates a Hong Shao Rou recipe.
	 * 
	 * @return  the recipe
	 */
	private static Recipe createHongShaoRou() {
		Recipe recipe = new Recipe("Hong Shao Rou", "Hunan Dish", 4);		
		
		recipe.addIngredient(new Ingredient("pork belly", 0.5, "kg", "cut into 2cm pieces"));
		recipe.addIngredient(new Ingredient("cooking oil", 2.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("brown sugar", 1.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("Shaoxin rice wine", 3.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("light soy sauce", 1.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("dark soy sauce", 0.5, "tablespoon"));
		recipe.addIngredient(new Ingredient("chicken stock or water", 2.0, "cups"));
		
		recipe.addPreparationStep("Bring a pot of water to a boil and blanch the pork for a couple minutes.");
		recipe.addPreparationStep("Take the pork out of the pot and set aside.");
		recipe.addPreparationStep("Over low heat, add oil and sugar to your wok.");
		recipe.addPreparationStep("Melt the sugar slightly and add the pork.");
		recipe.addPreparationStep("Raise the heat to medium and cook until the pork is lightly browned.");
		recipe.addPreparationStep("Turn the heat back down to low and add cooking wine, light soy sauce, dark soy sauce, and chicken stock.");
		recipe.addPreparationStep("Cover and simmer for about 60 minutes to 90 minutes until pork is fork tender.");
		recipe.addPreparationStep("Every 5-10 minutes, stir to prevent burning and add water if it gets too dry.");
		recipe.addPreparationStep("Once the pork is fork tender, if there is still a lot of visible liquid, uncover the wok, turn up the heat, and stir continuously the sauce has reduced to a glistening coating.");
		
		recipe.setPreparationTime(5);
		recipe.setCookingTime(100);
		recipe.restoreImg("food.jpg");
		return recipe;
	} 
	
	/**
	 * Creates a Suan La Fen recipe.
	 * 
	 * @return  the recipe
	 */
	private static Recipe createSuanLaFen() {
		Recipe recipe = new Recipe("Suan La Fen", "Sichuan Dish", 2);		
		
		recipe.addIngredient(new Ingredient("potato noodles", 1.0, "bunch"));
		recipe.addIngredient(new Ingredient("peanuts", 2.0, "tablespoon", "roasted"));
		recipe.addIngredient(new Ingredient("spring onion", 1.0, "tablespoon", "chopped"));
		recipe.addIngredient(new Ingredient("coriander", 1.0, "tablespoon", "chopped"));
		recipe.addIngredient(new Ingredient("pickled Sichuan vegetable", 2.0, "tablespoon", "chopped"));
		recipe.addIngredient(new Ingredient("garlic", 3.0, "cloves", "mashed"));
		recipe.addIngredient(new Ingredient("peanut oil", 0.5, "tablespoon"));
		recipe.addIngredient(new Ingredient("Sichuan peppercorn powder", 0.5, "teaspoon"));
		recipe.addIngredient(new Ingredient("Chinese five spicy powder", 0.5, "teaspoon"));
		recipe.addIngredient(new Ingredient("chili powder", 1.0, "teaspoon"));
		recipe.addIngredient(new Ingredient("vinegar", 1.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("light soy sauce", 1.0, "tablespoon"));
		recipe.addIngredient(new Ingredient("salt", 1.0, "teaspoon"));
				
		recipe.addPreparationStep("Soak the sweet potato noodles with hot water around 30 minutes.");
		recipe.addPreparationStep("Transfer out and set aside.");
		recipe.addPreparationStep("In the serving bowls and mix all the seasonings.");
		recipe.addPreparationStep("Heat up peanuts oil in pan to stir-fry the mashed garlic until aroma.");
		recipe.addPreparationStep("Mix the garlic oil with the seasonings.");
		recipe.addPreparationStep("Add some spring onions and corianders in serving bowls.");
		recipe.addPreparationStep("Pour in boiling water or stock to tune the seasonings.");
		recipe.addPreparationStep("Add vinegar and light soy sauce.");
		recipe.addPreparationStep("Mix well and set aside.");
		recipe.addPreparationStep("Cook soaked sweet potato noodles around 3~5 minutes until you can break the noodles with your fingers.");
		recipe.addPreparationStep("Transfer the noodles out to the serving bowls and then add top with pickled vegetables, roasted peanuts and chopped spring onions and coriander. ");
				
		recipe.setPreparationTime(30);
		recipe.setCookingTime(5);
		recipe.restoreImg("food.jpg");

		return recipe;
	} 
	
	/**
	 * Program entry point. 
	 * 
	 * @param args  command line arguments; not used.
	 */
	public static void main(String[] args) {
		CookBook cb = new CookBook("Chinese Cuisine");
		SQL_test databaseConnection = new SQL_test();
		databaseConnection.run();
		
		cb.add(createGongBaoJiding());
		cb.add(createHongShaoRou());
		cb.add(createSuanLaFen());
		
		// get recipe instances
		Recipe recipe_GBJ = cb.getRecipe("Gong Bao Jiding");
		Recipe recipe_HSR = cb.getRecipe("Hong Shao Rou");
		Recipe recipe_SLF = cb.getRecipe("Suan La Fen");

		// add recipes to the database
		databaseConnection.addRecipetoDatabase(recipe_GBJ,cb.getRecipeList().indexOf(recipe_GBJ));
		databaseConnection.addRecipetoDatabase(recipe_HSR,cb.getRecipeList().indexOf(recipe_HSR));
		databaseConnection.addRecipetoDatabase(recipe_SLF,cb.getRecipeList().indexOf(recipe_SLF));




		// test the functionthat change the amount with serve
		if (recipe_GBJ != null) {
			System.out.println(recipe_GBJ);
			// test recipe info after changed number of Eaters

			recipe_GBJ.changeWithServe(8);
			System.out.println("after changed number of Eaters:");
			System.out.println(recipe_GBJ);
			recipe_GBJ.changeWithServe(21);
			System.out.println(recipe_GBJ);
		}

		// 28/05/2019 Chen Sihan
		// test the function that search the  matched recipes with given string
		System.out.println("**********Then I test the searchAllMatchedRecipes**********:");
		System.out.println(databaseConnection.searchAllMatchedRecipes("rou"));

		//29/05/2019 Ling Wei
		//get recipe details by searching id
		System.out.println("**********Then I test the getRecipesBySearchfromDatabase**********:");
		Recipe matchedRecipesDetail = new Recipe("","",0);
		matchedRecipesDetail = databaseConnection.getRecipeBySearchfromDatabase(databaseConnection.searchAllMatchedRecipes("rou").get(0));
		System.out.println(matchedRecipesDetail);

		//29/05/2019 Ling Wei
		//get ingredient details by searching id
		System.out.println("**********Then I test the getIngredientsfromDatabase**********:");
		List<Ingredient> matchedIngredientsDetail = new LinkedList<Ingredient>();
		matchedIngredientsDetail = databaseConnection.getIngredientsfromDatabase(databaseConnection.searchAllMatchedRecipes("rou").get(0));
		for (int i = 0; i<matchedIngredientsDetail.size();i++){
			System.out.println(matchedIngredientsDetail.get(i));
		}

		//29/05/2019 Ling Wei
		//get step details by searching id
		System.out.println("**********Then I test the getStepsfromDatabase**********:");
		List<Step> matchedStepsDetail = new LinkedList<Step>();
		matchedStepsDetail = databaseConnection.getStepsfromDatabase(databaseConnection.searchAllMatchedRecipes("rou").get(0));
		for (int i = 0; i<matchedStepsDetail.size();i++){
			System.out.println(matchedStepsDetail.get(i));
		}

		// 28/05/2019 Chen Sihan
		// test the func that get 12 latest recipes
		System.out.println("*********Then I test the getRecipesForMainPage******:");
		System.out.println(databaseConnection.getRecipesForMainPage());

		// 28/05/2019 Chen Sihan
		// test the func that add forbidden pair
		System.out.println("*********Then I test the forbidden pair add func******:");
		databaseConnection.addForbiddenPair("persimmon","milk");
		System.out.println("Please check the database");

		// 28/05/2019 Chen Sihan
		//test the func that get the forbidden pair
		System.out.println("*********Then I test the forbidden pair get func******:");
		System.out.println(databaseConnection.getForbiddenPair("milk"));


		try {
			databaseConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


	}
}