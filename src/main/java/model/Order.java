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
public class Order {

    private final Customer ct;
    private final int ordernum;
    private final Product produit;
    
    public Order(int ordernumber, Customer customer, Product prod) {
        this.ct = customer;
        this.ordernum = ordernumber;
        this.produit = prod;
    }

}
