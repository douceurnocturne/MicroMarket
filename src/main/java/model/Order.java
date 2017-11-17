/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Ehsan
 */
public class Order {

    private final Customer customer;
    private final int ordernumber;
    private final int quantity;
    private final Product produit;
    private final float shippingcost;
    private final Date date;

    public Order(int ordernumber, Customer customer, Product produit, int quantity, float shippingcost, Date date) {
        this.customer = customer;
        this.ordernumber = ordernumber;
        this.produit = produit;
        this.quantity = quantity;
        this.shippingcost = shippingcost;
        this.date = date;

    }
    
    public Date GetDate(){
        return this.date;
    }
    
    public float GetOrderCostWithShipping(){
        return (this.quantity*this.produit.GetPrice())+this.shippingcost;
    }
    
    public int GetNumber(){
        return this.ordernumber;
    }
}
