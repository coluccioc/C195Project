package model;

/**
 * Model Class for GroupByTotals
 */
public class GroupByTotals {
    private String group;
    private int total;

    /**
     * Constructor for GroupByTotals
     * @param group group definition/name
     * @param total total count
     */
    public GroupByTotals(String group, int total){
        this.group=group;
        this.total = total;
    }

    /**
     * Getter for Group name
     * @return group
     */
    public String getGroup() {
        return group;
    }

    /**
     * Getter for group Total
     * @return total
     */
    public int getTotal(){
        return total;
    }
}
