package PixivCookbook;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Spycsh
 *
 */
public class CookBook implements Serializable {
	private String cookBookName;
	private LinkedList<Recipe> RecipeList;

	CookBook(String name) {
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
