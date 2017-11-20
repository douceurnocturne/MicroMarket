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

    private final int customerid;
    private final String name;
    private final String addressline1;
    private final String state;
    private final String city;
    private final String email;

    public Customer(int customerid, String name, String address1, String state, String city, String email) {
        this.customerid = customerid;
        this.name = name;
        this.addressline1 = address1;
        this.state = state;
        this.city = city;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getaddress() {
        return this.addressline1;
    }

    public int getcustomerid() {
        return this.customerid;
    }

    public String getstate() {
        return this.state;
    }

    public String getcity() {
        return this.city;
    }

    public String getemail() {
        return this.email;
    }

}
