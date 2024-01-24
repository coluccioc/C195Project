package model;

public class User {
    private int user_ID;
    private String name;
    public User(int user_ID,String name){
        this.user_ID = user_ID;
        this.name = name;
    }
    @Override
    public String toString() {
        return name;
    }
    public int getUser_ID(){
        return user_ID;
    }
}
