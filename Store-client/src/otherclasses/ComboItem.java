/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otherclasses;

/**
 *
 * @author sylar
 */
public class ComboItem {
    String key;
    Integer value;
     public ComboItem(String key, Integer value)
    {
        this.key = key;
        this.value = value;
    }
     
    @Override
    public String toString()
    {
        return key.toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
    
}
