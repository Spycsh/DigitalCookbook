/**
 * 
 */
package PixivCookbook;

/**
 * @author precision 7710
 *
 */
public class Ingredient {
	private String name="";
	private double num=0.0;
	private String unit="";
	private String preparation="";
	/**
	 * 
	 */
	public Ingredient(String name,double num,String unit) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.num=num;
		this.unit=unit;
	}
	public Ingredient(String name,double num,String unit,String preparation) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.num=num;
		this.unit=unit;
		this.preparation=preparation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getNum() {
		return num;
	}
	public void setNum(double num) {
		this.num = num;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getPreparation() {
		return preparation;
	}
	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}
	
}
