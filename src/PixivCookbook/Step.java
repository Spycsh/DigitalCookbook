/**
 * 
 */
package PixivCookbook;

import java.io.Serializable;

/**
 * @author Ling Wei
 *
 */
public class Step implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String content;
	private int stepNumber;
	
	/**
	 * constructor for class Step
	 * @param inputContent the content of the step
	 * @param stepNumber the current sequence number of this step
	 */
	public Step(String inputContent, int stepNumber) {
		this.setContent(inputContent);
		this.stepNumber = stepNumber; 
	}

	/**
	 * default getter for the content of the step
	 * @return the content of the step
	 */
	public String getContent() {
		return content;
	}

	/**
	 * default setter for the content of the step
	 * @param content input the content for the step
	 */
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Step"+stepNumber+"[content=" + content + "]";
	}
}
