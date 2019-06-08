package PixivCookbook;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JunitRecipe {
	Recipe aRecipe;
	List<Ingredient> aIngredientList;
	
	@BeforeEach
	void setUp() throws Exception {
		aRecipe = new Recipe("testRecipe", "Shanghai dish", 3);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAddIngredient() {
		aRecipe.addIngredient(new Ingredient("cornstarch", 1.0, "tablespoon"));
		aRecipe.addIngredient(new Ingredient("soy sauce", 4.0, "tablespoon"));
		aRecipe.addIngredient(new Ingredient("dried red chilis", 12.0, "pieces", "stemmed, halved crosswise, and seeded"));
		assertEquals(aRecipe.getIngredients().get(0).toString(), "1.0 tablespoon  cornstarch");
		assertEquals(aRecipe.getIngredients().get(1).toString(),"4.0 tablespoon  soy sauce");
		assertEquals(aRecipe.getIngredients().get(2).toString(), "12.0 pieces stemmed, halved crosswise, and seeded dried red chilis");
	}

	@Test
	void testAddPreparationStep() {
		aRecipe.addPreparationStep("Mix together cornstarch and 1 tbsp. of the soy sauce in a medium bowl.");
		aRecipe.addPreparationStep("Add chicken, toss well, and set aside to marinate for 30 minutes.");
		assertEquals(aRecipe.getSteps().get(0).toString(),"Mix together cornstarch and 1 tbsp. of the soy sauce in a medium bowl.");
		assertEquals(aRecipe.getSteps().get(1).toString(),"Add chicken, toss well, and set aside to marinate for 30 minutes.");
		
	}

	@Test
	void testChangeWithServe() {
		aRecipe.addIngredient(new Ingredient("defaultName", 25.0, "defaultUnit"));
			aIngredientList = aRecipe.getIngredients();
		aRecipe.changeWithServe(6);  // original is 3
		assertEquals(aIngredientList.get(0).getNum(), 50.0);
		
		aRecipe.changeWithServe(9);  //original is 6
		assertEquals(aIngredientList.get(0).getNum(), 75.0);  // 9/6*50=75
	}

	// not used
//	@Test
//	void testRestoreImg() {
//		aRecipe.restoreImg("C:\\Users\\Spycsh\\Desktop\\Spycsh.github.io\\image");
//		
//	}

}
