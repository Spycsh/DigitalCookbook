package PixivCookbook.Model;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * entity class cookbook
 * @author Spycsh
 * 
 * 
 */
public class CookBook implements Serializable {

	private static final long serialVersionUID = -7540360778235899360L;

	private String cookBookName;
	private List<Recipe> RecipeList = new LinkedList<Recipe>();

	public CookBook(String name) {
		this.cookBookName = name;
	}

	public void add(Recipe aRecipe) {
		this.RecipeList.add(aRecipe);
	}
	
	/**
	 * @param recipeName
	 * @return the exact recipe 
	 */
	public Recipe getRecipe(String recipeName) {
		Recipe targetRecipe = null;
		for (Recipe e : RecipeList) {
			if (e.getRecipeName() == recipeName) {
				targetRecipe = e;
			}
		}
		return targetRecipe;
	}

	/**
	 * @return the cookBookName
	 */
	public String getCookBookName() {
		return cookBookName;
	}

	/**
	 * @param cookBookName the cookBookName to set
	 */
	public void setCookBookName(String cookBookName) {
		this.cookBookName = cookBookName;
	}

	/**
	 * @return the recipeList
	 */
	public List<Recipe> getRecipeList() {
		return RecipeList;
	}

	/**
	 * @param recipeList the recipeList to set
	 */
	public void setRecipeList(List<Recipe> recipeList) {
		RecipeList = recipeList;
	}

	@ Override
	public String toString() {
		return this.cookBookName + " " + "Cookbook";
	}
	
}
