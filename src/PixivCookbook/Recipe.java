package PixivCookbook;
/**
 * 
 */


import java.io.Serializable;
import java.util.LinkedList;

/**
 * entity class recipe
 * @author Ling Wei
 *
 */
public class Recipe implements Serializable{

	private static final long serialVersionUID = 1L;
	private String recipeName;
	private String cuisineName;
	private int numberOfEaters;
	private int preparationTime;
	private int cookingTime;
	private LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
	private LinkedList<Step> steps = new LinkedList<Step>();

	private int countSteps = 0;
	
	/**
	 * constructor for class Recipe
	 * @param recipeName the name of the recipe
	 * @param cuisineName the name of cuisine the recipe belongs to
	 * @param numberOfEaters the number of people who would have the dish
	 */
	public Recipe(String recipeName,String cuisineName,int numberOfEaters) {
		this.setRecipeName(recipeName);
		this.setCuisineName(cuisineName);
		this.setNumberOfEaters(numberOfEaters);
	}
	
	/**
	 * add one ingredient to the recipe
	 * @param newIngredient new ingredient of the recipe
	 */
	public void addIngredient(Ingredient newIngredient) {
		ingredients.add(newIngredient);
	}
	
	public LinkedList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(LinkedList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public LinkedList<Step> getSteps() {
		return steps;
	}

	public void setSteps(LinkedList<Step> steps) {
		this.steps = steps;
	}
	/**
	 * add one step to the recipe
	 * @param stepContent the step's content
	 */
	public void addPreparationStep(String stepContent) {
		setCountSteps(getCountSteps() + 1);
		steps.add(new Step(stepContent,getCountSteps() ));
	}

	/**
	 * @return the latest step's number
	 */
	public int getCountSteps() {
		return countSteps;
	}

	/**
	 * default setter for step's number
	 * @param countSteps
	 */
	public void setCountSteps(int countSteps) {
		this.countSteps = countSteps;
	}

	/**
	 * default getter for the name of the recipe
	 * @return the name of the recipe
	 */
	public String getRecipeName() {
		return recipeName;
	}

	/**
	 * default setter for the name of the recipe
	 * @param recipeName input name for the recipe
	 */
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	/**
	 * default getter for the name of the cuisine
	 * @return the name of the cuisine
	 */
	public String getCuisineName() {
		return cuisineName;
	}

	/**
	 * default setter for the name of the cuisine
	 * @param cuisineName
	 */
	public void setCuisineName(String cuisineName) {
		this.cuisineName = cuisineName;
	}

	/**
	 * default getter for the number of the eaters
	 * @return the number of the eaters
	 */
	public int getNumberOfEaters() {
		return numberOfEaters;
	}

	/**
	 * default setter for the number of the eaters
	 * @param numberOfEaters the input number of eaters from the customer
	 */
	public void setNumberOfEaters(int numberOfEaters) {
		this.numberOfEaters = numberOfEaters;
	}

	/**
	 * default getter for the time needed for the preparation phase
	 * @return the time needed for the preparation phase(in minutes)
	 */
	public int getPreparationTime() {
		return preparationTime;
	}

	/**
	 * default setter for the time needed for the preparation phase
	 * @param preparationTime input time needed for the preparation phase
	 */
	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	/**
	 * default getter for the time needed for the cooking phase
	 * @return the time needed for the cooking phase
	 */
	public int getCookingTime() {
		return cookingTime;
	}

	/**
	 * default setter for the time needed for the cooking phase
	 * @param cookingTime input time needed for the cooking phase
	 */
	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}
	
	// change the unit and amount with changing eater numbers
	public void changeWithServe(int changedNumberOfEaters) {
		double ratio = (double)changedNumberOfEaters/(double)this.numberOfEaters;
		this.numberOfEaters = changedNumberOfEaters;
		for(Ingredient i: ingredients) {
			i.setNum(i.getNum()*ratio);
		}
	}

	@Override
	public String toString() {
		return "Recipe [recipeName=" + recipeName + ", cuisineName=" + cuisineName + ", numberOfEaters="
				+ numberOfEaters + ", preparationTime=" + preparationTime + ", cookingTime=" + cookingTime
				+ ", ingredients=" + ingredients + ", steps=" + steps + ", countSteps=" + countSteps + "]";
	}
	
}
