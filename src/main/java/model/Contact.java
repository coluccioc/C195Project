package model;

/**
 * Contact Model Class
 */
public class Contact{
    private int contact_ID;
    private String name;

    /**
     * Contact Constructor. Holds name and ID
     * @param contact_ID ID
     * @param name name
     */
    public Contact(int contact_ID,String name){
        this.contact_ID = contact_ID;
        this.name = name;
    }

    /**
     * Overrides the toString method to return the name on toString
     * Helps to populate ComboBoxes
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Getter for Contact ID
     * @return Contact ID
     */
    public int getContact_ID(){
        return contact_ID;
    }
}