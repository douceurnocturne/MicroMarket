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
    private final int instockquantity;
    private final boolean available;
    private final String manufacturer;
    private final String description;
    private final String code;

    /**
     *
     * @param productID
     * @param price
     * @param availableQuantity
     * @param available
     * @param description
     * @param manufacturers
     * @param code
     */
    public Product(int productID, float price, int availableQuantity, boolean available,
            String description, String manufacturers, String code) {
        this.productid = productID;
        this.price = price;
        this.instockquantity = availableQuantity;
        this.available = available;
        this.manufacturer = manufacturers;
        this.description = description;
        this.code = code;
    }

    public int getProductid() {
        return this.productid;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

    public int getInstockquantity() {
        return this.instockquantity;
    }

    public float getPrice() {
        return this.price;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public boolean isAvailable() {
        return this.available;
    }
}
