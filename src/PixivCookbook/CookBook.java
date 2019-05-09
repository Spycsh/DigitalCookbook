package PixivCookbook;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Spycsh
 *
 */
public class CookBook implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cookBookName;
	private LinkedList<Recipe> RecipeList = new LinkedList<Recipe>();

	public CookBook(String name) {
		this.cookBookName = name;
	}

	public void add(Recipe aRecipe) {
		this.RecipeList.add(aRecipe);
	}

	public Recipe getRecipe(String RecipeName) {
		Recipe targetRecipe = null;
		for (Recipe e : RecipeList) {
			if (e.getRecipeName() == RecipeName) {
				targetRecipe = e;
			}
		}
		return targetRecipe;
	}

	public String toString() {
		return this.cookBookName + " " + "Cookbook";
	}

}
