package model;

import java.util.List;

public class Country {
    private String name;
    private List<String> firstLevelDivisions;
    public Country(String name,List<String> firstLevelDivisions){
        this.name = name;
        this.firstLevelDivisions = firstLevelDivisions;
    }
    public List<String> getFirstLevelDivisions() {
        return firstLevelDivisions;
    }
    @Override
    public String toString(){
        return name;
    }
}
