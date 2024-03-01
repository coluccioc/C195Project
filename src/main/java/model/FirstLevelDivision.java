package model;

/**
 * Model Class for FLDs
 */
public class FirstLevelDivision {
    private String name;
    private int ID;

    /**
     * Constructor for FLDs
     * @param name name
     * @param ID ID
     */
    public FirstLevelDivision(String name,int ID){
        this.name = name;
        this.ID = ID;
    }

    /**
     * Getter for FLD ID
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * toString() override. Helps to populate comboboxes
     * @return name
     */
    @Override
    public String toString(){
        return name;
    }
}