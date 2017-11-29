/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Ehsan
 */
public class Order {

    private final Customer customer;
    private final int ordernumber;
    private final int quantity;
    private final Product product;
    private final float shippingcost;
    private final Date saledate;
    private final Date shippingdate;
    private final String freight;

    public Order(int ordernumber, Customer customer, Product product,
            int quantity, float shippingcost, Date saledate, Date shippingdate, String freight) {
        this.customer = customer;
        this.ordernumber = ordernumber;
        this.product = product;
        this.quantity = quantity;
        this.shippingcost = shippingcost;
        this.saledate = saledate;
        this.shippingdate = shippingdate;
        this.freight = freight;

    }

    public Date getSaledate() {
        return this.saledate;
    }
    public Date getShippingdate() {
        return this.shippingdate;
    }
    public float getOrderCostWithShipping() {
        return (this.quantity * this.product.getPrice()) + this.shippingcost;
    }

    public int getOrdernumber() {
        return this.ordernumber;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Product getProduct() {
        return this.product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public float getShippingcost() {
        return this.shippingcost;
    }

    public String getFreight() {
        return this.freight;
    }
}
