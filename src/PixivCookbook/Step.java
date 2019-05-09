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
	
	public Step(String inputContent, int stepNumber) {
		this.setContent(inputContent);
		this.stepNumber = stepNumber; 
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Step"+stepNumber+"[content=" + content + "]";
	}
}
