package model;

public class FirstLevelDivision {
    private String name;
    private int ID;
    public FirstLevelDivision(String name,int ID){
        this.name = name;
        this.ID = ID;
    }
    public int getID() {
        return ID;
    }
    @Override
    public String toString(){
        return name;
    }
}