package PixivCookbook.Model;

import java.io.Serializable;

/**entity class step
 * @author Ling Wei
 *
 * 
 */
public class Step implements Serializable {
	
	private static final long serialVersionUID = -7024797642131895467L;
	
	private String content;
	private int stepNumber;
	

	/**
	 * constructor for class Step
	 * @param inputContent the content of the step
	 * @param stepNumber the current sequence number of this step
	 */
	public Step(String inputContent, int stepNumber) {
		this.setContent(inputContent);
		setStepNumber(stepNumber); 
	}



	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}



	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}



	/**
	 * @return the stepNumber
	 */
	public int getStepNumber() {
		return stepNumber;
	}



	/**
	 * @param stepNumber the stepNumber to set
	 */
	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}



	@Override
	public String toString() {
		return "Step"+stepNumber+"[content=" + content + "]";
	}
}
