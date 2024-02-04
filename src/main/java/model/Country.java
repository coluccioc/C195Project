package model;

import javafx.collections.ObservableList;

import java.util.List;

public class Country {
    private String name;
    private int ID;
    public Country(String name,int ID){
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