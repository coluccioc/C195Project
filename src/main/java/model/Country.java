package model;

/**
 * Model Class for Country
 */
public class Country {
    private String name;
    private int ID;
    /**
     * Country constructor given name and ID
     * @param name name
     * @param ID ID
     */
    public Country(String name,int ID){
        this.name = name;
        this.ID = ID;
    }

    /**
     * ID Getter
     * @return ID
     */
    public int getID() {
        return ID;
    }
    /**
     * toString() Override to help populate comboboxes
     * @return
     */
    @Override
    public String toString(){
        return name;
    }
}