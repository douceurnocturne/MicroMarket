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
public class Product {

    private final int productid;
    private final float price;
    private final int inStockQuantity;
    private final boolean available;
    private final String manufacturer;
    private final String description;

    public Product(int productID, float price, int availableQuantity, boolean available,
            String description, String manufacturers) {
        this.productid = productID;
        this.price = price;
        this.inStockQuantity = availableQuantity;
        this.available = available;
        this.manufacturer = manufacturers;
        this.description = description;
    }

    public int getProductid() {
        return this.productid;
    }

    public String getDescription() {
        return this.description;
    }

    public int getInStockQuantity() {
        return this.inStockQuantity;
    }

    public float getPrice() {
        return this.price;
    }

    public boolean isAvailable() {
        return this.available;
    }
}
