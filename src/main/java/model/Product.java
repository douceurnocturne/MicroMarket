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
    private int quantity;
    
    public Product(int pr, int qnt){
        this.price = pr;
        this.quantity=qnt;
    }
    
    public int GetQuantity(){
        return this.quantity;
    } 
}
