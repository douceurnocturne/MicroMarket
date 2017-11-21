/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        //On créer la commande
        int ordernumber = 5;
        int quantity = 5;
        float shippingcost = 10;
        String freight = "test";
        Date date = new Date(1, 2, 2010);
        Order commande = new Order(ordernumber, client, produit, quantity, shippingcost, (Date) date, freight);
    }
    
    @Test
    public void testOrderByCustomer(){
        //Le client d'id = 3
        Customer client3 = new Customer(3, "Small Bill Company", "8585 South Upper Murray Drive", "GA", "Alanta", "www.smallbill.example.com");
        //Son produit commander
        Product produit3 = new Product(980030, (float) 59.95, 250, true, "10Gb Ram", "Poney Express");
        //La seul commande de ce client
        Date date3 = new Date(24, 05, 2011);
        Order commande3 = new Order(10398004, client3, produit3, 10, (float) 275.00, date3, "Poney Express");
        DAO dao = new DAO(DataSourceFactory.getDataSource(DataSourceFactory.DriverType.embedded));
        List<Order> L = dao.GetOrderByCustomer(client3);
        System.out.println(L);
        assertEquals(L.get(0),commande3);
    }
    
    @Test
    public void testGetBenefictsByState(){
        //manuf id = 19986196
        //product id = 980030
        //quant = 10
        //unit price = 59.95
        //ship cost = 275
        float res = (float)59.95*10-275;
        DAO dao = new DAO(DataSourceFactory.getDataSource(DataSourceFactory.DriverType.embedded));
        float benef = dao.GetBenefictsByState("IL");
        assertEquals(benef,res,0.02);
    }
    
    @After
    public void tearDown() {
    }
    
}
