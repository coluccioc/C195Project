package model;

/**
 * Model Class for Customer
 */
public class Customer {
    private int customer_ID;
    private String customer_Name;
    private String address;
    private String postal_Code;
    private String phone;
    private int division_ID;

    /**
     * Customer Constructor
     * @param customer_ID
     * @param customer_Name
     * @param address
     * @param postal_Code
     * @param phone
     * @param division_ID
     */
    public Customer(int customer_ID, String customer_Name, String address, String postal_Code, String phone, int division_ID) {
        this.customer_ID = customer_ID;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division_ID = division_ID;
    }

    /**
     * toString() override to help populate comboboxes
     * @return name
     */
    public String toString(){
        return customer_Name;
    }

    /**
     * Getter for Customer ID
     * @return ID
     */
    public int getCustomer_ID() {
        return customer_ID;
    }
    /**
     * Getter for Customer Name
     * @return name
     */
    public String getCustomer_Name() {
        return customer_Name;
    }
    /**
     * Getter for Customer Address
     * @return Address
     */
    public String getAddress() {
        return address;
    }
    /**
     * Getter for Customer Postal Code
     * @return postal_code
     */
    public String getPostal_Code() {
        return postal_Code;
    }
    /**
     * Getter for Customer phone
     * @return phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Getter for Customer Division ID
     * @return Division ID
     */
    public int getDivision_ID() {
        return division_ID;
    }
}