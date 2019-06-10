package PixivCookbook.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PixivCookbook.Ingredient;
import PixivCookbook.Recipe;
import PixivCookbook.Step;

class JunitTestSQL_testTest {
	SQL_test aSql_test;
	Recipe aRecipe;
	Ingredient aIngredient;
	
	Recipe testRecipe ;
	
	
	@BeforeEach
	void setUp() throws Exception {
		aSql_test = new SQL_test();
		aSql_test.run();
		testRecipe = new Recipe("testRecipe","ShanghaiCai" , 4);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRun() throws SQLException {
		assertTrue(!aSql_test.getConnect().isClosed());
	}

	@Test
	void testClose() throws SQLException {
		aSql_test.getConnect().close();
		assertTrue(aSql_test.getConnect().isClosed());
	}
	
	
	// test search first
	// if you set the recipe id to be 999, then the new one id will be 999+1
	@Test
	void testAddRecipetoDatabase() {
		aSql_test.addRecipetoDatabase(testRecipe, 665);  // will be 666
		aRecipe = aSql_test.getRecipeBySearchfromDatabase(666);
		assertEquals(aRecipe.getRecipeName(), "testRecipe");
	}

	@Test
	void testAssignID() {
		int aID = aSql_test.assignID();
		assertEquals(aID, 4);
	}

//	 caution! this is delete!
	@Test
	void testDeleteRecipefromDatabase() {
		List<String> aList = aSql_test.getAllRecipeNamesfromDatabase();
		aSql_test.deleteRecipefromDatabase(666);
		List<String> aList2 = aSql_test.getAllRecipeNamesfromDatabase();
		assertNotEquals(aList, aList2);
	}
	
//	 this updates the name
	@Test
	void testSaveRecipName() throws InterruptedException {
		aSql_test.saveRecipName(1, "testRecipe_modified");
		aRecipe = aSql_test.getRecipeBySearchfromDatabase(1);
		assertEquals(aRecipe.getRecipeName(), "testRecipe_modified");
		aSql_test.saveRecipName(1, "Gong Bao Jiding");
		aRecipe = aSql_test.getRecipeBySearchfromDatabase(1);
		assertEquals(aRecipe.getRecipeName(), "Gong Bao Jiding");
	}
	
	
	// model has description is cuisine
	@Test
	void testSaveDescription() {
		aSql_test.saveDescription(666, "aaa");
		aRecipe = aSql_test.getRecipeBySearchfromDatabase(666);
		assertEquals(aRecipe.getCuisineName(), "aaa");
	}
	
	
	
	@Test
	void testSaveImagePath() {
		aSql_test.saveImagePath(666, "test.jpg");
		aRecipe = aSql_test.getRecipeBySearchfromDatabase(666);
		assertEquals(aRecipe.getImgAddress(), "test.jpg");
	}

	@Test
	void testGetAllRecipeNamesfromDatabase() {
		List<String> atestList= new LinkedList<String>() {{
			add("Gong Bao Jiding");
			add("Hong Shao Rou");
			add("Suan La Fen");
			add("testRecipe");
		}};
		
		List<String> aList = aSql_test.getAllRecipeNamesfromDatabase();
		assertEquals(atestList, aList);
		
	}

	@Test
	void testGetRecipeBySearchfromDatabase() {
		aRecipe = aSql_test.getRecipeBySearchfromDatabase(666);
		assertEquals(aRecipe.getRecipeName(), "testRecipe");
	}

	@Test
	void testGetIngredientsfromDatabase() {
		List<Ingredient> aIngredientList = aSql_test.getIngredientsfromDatabase(1);
		assertEquals(aIngredientList.get(0).getName(),"Chiangang vinegar");
	}

	@Test
	void testGetStepsfromDatabase() {
		List<Step> aStepList = aSql_test.getStepsfromDatabase(1);  // recipe 1's step
		assertEquals(aStepList.get(3).getContent(),"Set aside." );
	}

	@Test
	void testSearchAllMatchedRecipes() {
		List<Recipe> atestRecipeList = aSql_test.searchAllMatchedRecipes("ong");
		List<Recipe> aRecipeList = new LinkedList<Recipe>() {{
			add(aSql_test.getRecipeBySearchfromDatabase(1));  // Gong Bao Jiding
			add(aSql_test.getRecipeBySearchfromDatabase(2));  // Hong Shao Rou
		}};
		assertEquals(atestRecipeList.toString(), aRecipeList.toString());  // should not compare the object directly
		
	}

	@Test
	void testSearchAllMatchedID() {
		List<Integer> aList = aSql_test.searchAllMatchedID("Gong");
		List<Integer> atestList = new LinkedList<Integer>() {{
			add(1);
		}};
		assertEquals(aList, atestList);
	}

	@Test
	void testGetRecipesForMainPage() {
		List<Recipe> aList= aSql_test.getRecipesForMainPage();   // list desc, so first element with index 0 is testRecipe!
		assertEquals(aList.get(0).getRecipeName(),"testRecipe");
		
	}

	@Test
	void testAddForbiddenPair() {
		aSql_test.addForbiddenPair("f_2", "f_1");
		LinkedList<String> aList = new LinkedList<String>();
		aList.add("f_2");
		assertEquals(aSql_test.getForbiddenPair("f_1"), aList);
	}

	//add a pair in first
	@Test
	void testGetForbiddenPair() {
		LinkedList<String> alist = new LinkedList<String>();
		alist.add("persimmon");
		assertEquals(aSql_test.getForbiddenPair("milk"),alist);
	}

}
