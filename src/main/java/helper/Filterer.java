package helper;

import javafx.collections.ObservableList;

/**
 * A Functional Interface class to allow lambda expressions to implement Filters
 * Intended to be used to filter results of DAO Queries
 * Extract method to be used in lambdas
 * @param <T> input to determine the type of object being filtered. Can change upon implementation
 */
public interface Filterer<T> {
    ObservableList<T> extract(ObservableList<T> inputList,int filterInt);
}
