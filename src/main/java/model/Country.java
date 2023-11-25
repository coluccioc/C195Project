package model;

import javafx.collections.ObservableList;

import java.util.List;

public class Country {
    private String name;
    private ObservableList<String> firstLevelDivisions;
    public Country(String name,ObservableList<String> firstLevelDivisions){
        this.name = name;
        this.firstLevelDivisions = firstLevelDivisions;
    }
    public ObservableList<String> getFirstLevelDivisions() {
        return firstLevelDivisions;
    }
    @Override
    public String toString(){
        return name;
    }
}
