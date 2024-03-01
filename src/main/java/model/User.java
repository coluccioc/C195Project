package model;

/**
 * Model Class for User
 */
public class User {
    private int user_ID;
    private String name;

    /**
     * Constructor for Users
     * @param user_ID ID
     * @param name name
     */
    public User(int user_ID,String name){
        this.user_ID = user_ID;
        this.name = name;
    }

    /**
     * toString() override. Helps to populate comboboxes
     * @return name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Getter for User ID
     * @return user_ID
     */
    public int getUser_ID(){
        return user_ID;
    }
}