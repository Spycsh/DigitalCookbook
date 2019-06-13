package PixivCookbook.Model;

import java.io.Serializable;

/**
 * entity class forbidden pair
 * @author Spycsh

 */
public class ForbiddenPair implements Serializable {

    private static final long serialVersionUID = -7024797644331895467L;
    
	private String key;
    private String value;
    private int id;
    
    
    /**
     * constructor
     * @param key
     * @param value
     * @param id
     */
    public ForbiddenPair(String key, String value,int id) {
        setId(id);
        setKey(key);
        setValue(value);
    }


    /**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}



    @Override
    public String toString() {
        return id+":" + key + "="+value;
    }
}
