package PixivCookbook;

import java.io.Serializable;

public class ForbiddenPair implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7024797644331895467L;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String key;
    private String value;
    private int id;
    public ForbiddenPair(String key, String value,int id) {
        setId(id);
        setKey(key);
        setValue(value);
    }

    @Override
    public String toString() {
        return id+":" + key + "="+value;
    }
}
