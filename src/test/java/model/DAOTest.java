/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.sql.DataSource;
import org.junit.*;
import static org.junit.Assert.*;
import model.Order;

/**
 *
 * @author xzait
 */
public class DAOTest {
    
    public DAOTest() {
    }
    
    @Before
    public void setUp() {
        //On créer le Customer
        int customerid = 1000;
        String name= "Douvat";
        String address1 = "test";
        String state = "FL";
        String city = "Miami";
        String email = "test@test";
        Customer client = new Customer(customerid, name, address1, state, city, email);
        //On créer le produit
        int productID = 50;
        float price = 10;
        int availableQuantity = 15;
        boolean available = true;
        String description = "un superbe sac";
        String manufacturers = "test";
        Product produit = new Product(productID, price, availableQuantity, available, description, manufacturers);
        //Order(int ordernumber, Customer customer, Product product, int quantity, float shippingcost, Date date, String freight);
    }
    
    @After
    public void tearDown() {
    }
    
}
