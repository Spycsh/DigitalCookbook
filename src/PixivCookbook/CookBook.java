package PixivCookbook;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Spycsh
 *
 */
public class CookBook implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7540360778235899360L;
	/**
	 * 
	 */
	private String cookBookName;
	private List<Recipe> RecipeList = new LinkedList<Recipe>();

	public CookBook(String name) {
		this.cookBookName = name;
	}

	public void add(Recipe aRecipe) {
		this.RecipeList.add(aRecipe);
	}
	
	// search the exact Recipe
	public Recipe getRecipe(String recipeName) {
		Recipe targetRecipe = null;
		for (Recipe e : RecipeList) {
			if (e.getRecipeName() == recipeName) {
				targetRecipe = e;
			}
		}
		return targetRecipe;
	}
	
	public String getCookBookName() {
		return cookBookName;
	}

	public void setCookBookName(String cookBookName) {
		this.cookBookName = cookBookName;
	}

	public List<Recipe> getRecipeList() {
		return RecipeList;
	}

	public void setRecipeList(LinkedList<Recipe> recipeList) {
		RecipeList = recipeList;
	}

	@ Override
	public String toString() {
		return this.cookBookName + " " + "Cookbook";
	}
	
}
