/**
 * 
 */
package PixivCookbook;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Ling Wei
 *
 */
public class Recipe implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String recipeName;
	private String cuisineName;
	private int numberOfEaters;
	private int preparationTime;
	private int cookingTime;
	private LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
	private LinkedList<Step> steps = new LinkedList<Step>();
	private int countSteps = 0;
	
	public Recipe(String recipeName,String cuisineName,int numberOfEaters) {
		this.setRecipeName(recipeName);
		this.setCuisineName(cuisineName);
		this.setNumberOfEaters(numberOfEaters);
	}
	
	public void addIngredient(Ingredient newIngredient) {
		ingredients.add(newIngredient);
	}
	
	public void addPreparationStep(String step) {
		setCountSteps(getCountSteps() + 1);
		steps.add(new Step(step,getCountSteps() ));
	}

	public int getCountSteps() {
		return countSteps;
	}

	public void setCountSteps(int countSteps) {
		this.countSteps = countSteps;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getCuisineName() {
		return cuisineName;
	}

	public void setCuisineName(String cuisineName) {
		this.cuisineName = cuisineName;
	}

	public int getNumberOfEaters() {
		return numberOfEaters;
	}

	public void setNumberOfEaters(int numberOfEaters) {
		this.numberOfEaters = numberOfEaters;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	@Override
	public String toString() {
		return "Recipe [recipeName=" + recipeName + ", cuisineName=" + cuisineName + ", numberOfEaters="
				+ numberOfEaters + ", preparationTime=" + preparationTime + ", cookingTime=" + cookingTime
				+ ", ingredients=" + ingredients + ", steps=" + steps + ", countSteps=" + countSteps + "]";
	}
	
}
