/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ehsan
 */
public class Customer {

    private final int customerId;
    private final String name;
    private final String addressLine1;
    private final String state;
    private final String city;
    private final String email;

    public Customer(int customerID, String name, String address1, String state, String city, String email) {
        this.customerId = customerID;
        this.name = name;
        this.addressLine1 = address1;
        this.state = state;
        this.city = city;
        this.email = email;
    }

    public String GetName() {
        return this.name;
    }

    public String GetAddress() {
        return this.addressLine1;
    }

    public int GetID() {
        return this.customerId;
    }

    public String GetState() {
        return this.state;
    }

    public String GetCity() {
        return this.city;
    }

    public String GetEmail() {
        return this.email;
    }

}
