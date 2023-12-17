package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.util.Arrays;
import java.util.List;

public class CountryHelper {
    // need to make a list of countries
    public static ObservableList<Country> listOfCountries = FXCollections.observableArrayList();
    private static ObservableList<String> usaFLDs = FXCollections.observableArrayList();
    static {
        usaFLDs.addAll("NY","NJ","KY","MO","CA","TX","RI","NC","SC","GA","FL");
    }
    private static ObservableList<String> canadaFLDs = FXCollections.observableArrayList();
    static {
        canadaFLDs.addAll("Canada1","Canada2","Canada3","Canada4","Canadia","Quebec");
    }
    public static Country usa = new Country("United States",1);
    public static Country canada = new Country("Canadia",2);

}