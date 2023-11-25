package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountryHelper {
    // need to make a list of countries
    public static ObservableList<Country> listOfCountries = FXCollections.observableArrayList();
    private static List<String> usaFLDs = Arrays.asList("NY","KY");
    private static List<String> canadaFLDs = Arrays.asList("Canada1","Canada2");
    public static Country usa = new Country("United States",usaFLDs);
    public static Country canada = new Country("Canadia",canadaFLDs);

}