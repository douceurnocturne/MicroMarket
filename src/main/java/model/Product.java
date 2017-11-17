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

    private final int price;
    private final int inStockQuantity;
    private final boolean available;
    private final int manufacturersID;

    public Product(int price, int availableQuantity, boolean available, int manufacturers) {
        this.price = price;
        this.inStockQuantity = availableQuantity;
        this.available = available;
        this.manufacturersID = manufacturers;
    }

    public int GetQuantity() {
        return this.inStockQuantity;
    }

    public int GetPrice() {
        return this.price;
    }

    public boolean isAvailable() {
        return this.available;
    }
}
