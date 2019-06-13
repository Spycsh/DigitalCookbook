package PixivCookbook.Model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * entity class recipe
 * @author Ling Wei
 * 
 */
public class Recipe implements Serializable{

	private static final long serialVersionUID = -8812539449144498327L;

	private String recipeName;
	private String cuisineName;
	private String imgAddress;
	private int numberOfEaters = 0;
	private int preparationTime = 0;
	private int cookingTime = 0;

	private List<Ingredient> ingredients = new LinkedList<Ingredient>();
	private List<Step> steps = new LinkedList<Step>();

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
	
	
	/**
	 * @return the ingredients
	 */
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * @param ingredients the ingredients to set
	 */
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * @return the steps
	 */
	public List<Step> getSteps() {
		return steps;
	}

	/**
	 * @param steps the steps to set
	 */
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	/**
	 * add one step to the recipe
	 * @param stepContent the step's content
	 */
	public void addPreparationStep(String stepContent) {
		steps.add(new Step(stepContent,steps.size() ));
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
	
	/**
	 * @param changedNumberOfEaters
	 * change amount and unit of ingredients
	 * with the ratio of new number of eater divided by 
	 * original number of eaters
	 */
	public void changeWithServe(int changedNumberOfEaters) {
		double ratio = (double)changedNumberOfEaters/(double)this.numberOfEaters;
		this.numberOfEaters = changedNumberOfEaters;
		for(Ingredient i: ingredients) {
			i.setNum(i.getNum()*ratio);
		}
	}


	/**
	 * @return the imgAddress
	 */
	public String getImgAddress() {
		return imgAddress;
	}

	/**
	 * @param imgAddress the imgAddress to set
	 */
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}

	/**
	 * @param imgAddress
	 * set image path attribute with a specific path format
	 * 
	 */
	public void restoreImg() {
		String name=this.recipeName.replaceAll(" ","");
		String desPath="img\\"+name+".jpg";
		try
		{
			/*
			FileInputStream fis = new FileInputStream(imgAddress);
			File file = new File(desPath);
			FileOutputStream fos = new FileOutputStream(desPath);
			int len = 0;
			while ((len = fis.read()) != -1) {
				fos.write(len);
			}
			fos.close();
			fis.close();
			*/
			this.imgAddress = desPath;
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public String toString() {
		return "Recipe [recipeName=" + recipeName + ", cuisineName=" + cuisineName + ", numberOfEaters="
				+ numberOfEaters + ", preparationTime=" + preparationTime + ", cookingTime=" + cookingTime
				+ ", ingredients=" + ingredients + ", steps=" + steps + ", countSteps=" + countSteps +",imgAdr="+imgAddress+ "]";
	}
	
}
