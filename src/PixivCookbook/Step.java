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
	
	public Step(String inputContent) {
		this.setContent(inputContent);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
