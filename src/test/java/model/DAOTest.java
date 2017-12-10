/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author xzait
 */
public class DAOTest {

    private static DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection;
    private DAO dao;

    public static DataSource getDataSource() throws SQLException {
        org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }

    @Before
    public void setUp() throws SQLException, IOException, URISyntaxException, SqlToolError {
        // On crée la connection vers la base de test "in memory"
		myDataSource = getDataSource();
		myConnection = myDataSource.getConnection();
		// On initialise la base avec le contenu d'un fichier de test
		String sqlFilePath = DAOTest.class.getResource("CREATE.sql").getFile();
		SqlFile sqlFile = new SqlFile(new File(sqlFilePath));
		sqlFile.setConnection(myConnection);
		sqlFile.execute();
		sqlFile.closeReader();
		// On crée l'objet à tester
                dao = new DAO(myDataSource);
    }

    @After
    public void tearDown() throws SQLException {

        myConnection.close();
        dao = null; // Pas vraiment utile
    }

    @Test
    public void addValidOrder() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        assertEquals(dao.AddOrder(ord),1);
    }
    
    //On test add order avec des order invalides
    @Test
    public void addInvalidOrder() throws SQLException, DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(1, customer, product, -2, (float) 2.00, date, date, "test");
        assertEquals(dao.AddOrder(ord),0);
        Order ord1 = new Order(0, customer, product, 1, (float) 2.00, date, date, "test");
        assertEquals(dao.AddOrder(ord1),0);
        Order ord2 = new Order(1, customer, product, 1, (float) -5.00, date, date, "test");
        assertEquals(dao.AddOrder(ord2),0);
    }
    
    @Test
    public void updateValidOrder() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        Order ord1 = new Order(3, customer, product, 6, (float) 2.00, date, date, "test");
        assertEquals(dao.UpdateOrder(ord1),1);
    }
    
    @Test
    public void updtaeInvalidOrder() throws SQLException, DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order or = new Order(1, customer, product, 2, (float) 2.00, date, date, "test");       
        Order ord = new Order(1, customer, product, -2, (float) 2.00, date, date, "test");
        Order ord1 = new Order(1, customer, product, 1, (float) -5.00, date, date, "test");
        dao.AddOrder(or);
        assertEquals(dao.UpdateOrder(ord),0);
        assertEquals(dao.UpdateOrder(ord1),0);
    }
    
    //On essaye d'update un order qui n'existe pas
    @Test
    public void updateNonExistOrder() throws SQLException, DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(1, customer, product, 2, (float) 2.00, date, date, "test");
        assertEquals(dao.UpdateOrder(ord),0);
    }
    
    @Test
    public void deleteOrder() throws SQLException, DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(1, customer, product, 2, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        assertEquals(dao.DeleteOrder(1),1);
    }
    
    @Test
    public void deleteNonExistOrder() throws SQLException, DAOException {
        assertEquals(dao.DeleteOrder(1),0);
    }
    
    @Test
    public void validLogin() throws SQLException, DAOException {
        Customer cust = dao.Login("www.bigbill.example.com", 1);
        assertEquals(cust.getName(),"Big Bill Company");
    }
    
    //On se log avec le mauvais user name
    @Test
    public void wrongUser() throws SQLException, DAOException {
        assertEquals(dao.Login("test", 1),null);
    }
    
    //On se log avec le mauvais mot de passe
    @Test
    public void wrongPwd() throws SQLException, DAOException {
        assertEquals(dao.Login("www.bigbill.example.com", 2),null);
    }
    
    @Test
    public void getProdCods() throws DAOException {
        List<String> listProd = Arrays.asList("BK","CB","FW","HW","MS","SW");
        assertEquals(dao.getProductCodes(),listProd);
    }
    
    @Test
    public void getCustomerList() throws DAOException {
        assertEquals(dao.GetCustomerList().get(0).getID(),1);
        assertEquals(dao.GetCustomerList().get(0).getName(),"Big Bill Company");
        assertEquals(dao.GetCustomerList().get(0).getcity(),"MIAMI");
    }
    
    @Test
    public void getProductListValidCat(){
        List<Product> productList = dao.getProductList("SW");
        for (int i=0; i<productList.size();i++){
            assertEquals(productList.get(i).getCode(),"SW");
        }
        assertEquals(productList.size(),1);
    }
    
    @Test
    public void getProductListUnexistCat(){
        List<Product> productList = dao.getProductList("FF");
        assertEquals(productList.size(),0);
    }
    
    @Test
    public void getProductListInvalidCat(){
        List<Product> productList = dao.getProductList("FFF");
        assertEquals(productList.size(),0);
    }
    
    @Test
    public void getProductValidId() throws DAOException {
        assertEquals(dao.getProductByid(3).getProductid(),3);
        assertEquals(dao.getProductByid(3).getCode(),"SW");
        assertEquals(dao.getProductByid(3).getDescription(),"test");
        assertEquals(dao.getProductByid(3).getInstockquantity(),50);
    }
    
    //Un id invalide est forcement un id qui n'existe pas donc pas besoin de
    //traiter le cas ou l'id est invalide.
    @Test
    public void getProductUnexistId() throws DAOException {
        assertEquals(dao.getProductByid(2),null);
    }
    
    @Test
    public void getOrderValidCustomerValidNumber() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        Order order = dao.getCustomerOrderByid(customer, 3);
        assertEquals(order.getOrdernumber(),ord.getOrdernumber());
        assertEquals(order.getCustomer(),ord.getCustomer());
        assertEquals(order.getQuantity(),ord.getQuantity());
        assertEquals(order.getSaledate(),ord.getSaledate());
        assertEquals(order.getShippingdate(),ord.getShippingdate());
        assertEquals(order.getFreight(),ord.getFreight());
    }
    
    //Un nombre invalide est forcement un nombre qui n'existe pas donc pas besoin de
    //traiter le cas ou le nombre est invalide.
    @Test 
    public void getOrderValidCustomerUnexistNumber() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        assertEquals(dao.getCustomerOrderByid(customer, 1),null);
    }
    
    @Test
    public void getOrderValidCustomer() throws DAOException{
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        List<Order> listOrder = dao.GetOrderByCustomer(customer);
        assertEquals(listOrder.size(),1);
        assertEquals(listOrder.get(0).getCustomer(),customer);
        assertEquals(listOrder.get(0).getFreight(),"test");
        assertEquals(listOrder.get(0).getOrdernumber(),3);
        assertEquals(listOrder.get(0).getQuantity(),5);
        assertEquals(listOrder.get(0).getSaledate(),date);
        assertEquals(listOrder.get(0).getShippingdate(),date);
    }
    
    @Test
    public void getOrderInvalidCustomer() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        Customer cust = new Customer(5, "Big Bill Company", "20 rue de la paix", "FL", "MIAMI", "www.bigbill.example.com");
        List<Order> listeVide = new LinkedList<>();
        assertEquals(dao.GetOrderByCustomer(cust),listeVide);
    }
    
    @Test
    public void GetBenefitsByProductCodesAndWrongDate() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        Map<String, Float> mapVierge = new HashMap<>();
        Date dateDeb = new Date(2011,05,25);
        Date dateFin = new Date(2011,05,26);
        Map<String, Float> mapFonction = dao.GetBenefitsByProductCodesAndDate(dateDeb, dateFin);
        assertEquals(mapFonction, mapVierge);
    }
    
    @Test
    public void GetBenefitsByProductCodesAndValidDate() throws DAOException {
        //On ajoute la commande a la bdd :        
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        //On créer une carte vierge :
        Map<String, Float> mapVierge = new HashMap<>();
        //On créer la carte modifier :
        Date dateDeb = new Date(2010,04,23);
        Date dateFin = new Date(2012,06,25);
        Map<String, Float> mapFonction = dao.GetBenefitsByProductCodesAndDate(dateDeb, dateFin);
        assertEquals(mapFonction.size(),1);
        assertNotEquals(mapFonction, mapVierge);
    }
    
    @Test
    public void GetBenefitsByNoProductCodesAndValidDate() throws DAOException {
        //On créer une carte vierge :
        Map<String, Float> mapVierge = new HashMap<>();
        //On créer la carte modifier :
        Date dateDeb = new Date(2010,04,23);
        Date dateFin = new Date(2012,06,25);
        Map<String, Float> mapFonction = dao.GetBenefitsByProductCodesAndDate(dateDeb, dateFin);
        assertEquals(mapFonction.size(),0);
        assertEquals(mapFonction, mapVierge);
    }
    
    //Si on inverse date de debut et date de fin
    @Test
    public void GetBenefitsByProductCodesAndErrorDate() throws DAOException {
        //On ajoute la commande a la bdd :        
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        //On créer une carte vierge :
        Map<String, Float> mapVierge = new HashMap<>();
        //On créer la carte modifier :
        Date dateDeb = new Date(2010,04,23);
        Date dateFin = new Date(2012,06,25);
        Map<String, Float> mapFonction = dao.GetBenefitsByProductCodesAndDate(dateFin, dateDeb);
        assertEquals(mapFonction.size(),0);
        assertEquals(mapFonction, mapVierge);
    }
    
    @Test
    public void GetBenefitsByStateAndWrongDate() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        Map<String, Float> mapVierge = new HashMap<>();
        Date dateDeb = new Date(2011,05,25);
        Date dateFin = new Date(2011,05,26);
        Map<String, Float> mapFonction = dao.GetBenefitsByState(dateDeb, dateFin);
        assertEquals(mapFonction, mapVierge);
    }
    
    @Test
    public void GetBenefitsByStateValidDate() throws DAOException {
        //On ajoute la commande a la bdd :        
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        //On créer une carte vierge :
        Map<String, Float> mapVierge = new HashMap<>();
        //On créer la carte modifier :
        Date dateDeb = new Date(2010,04,23);
        Date dateFin = new Date(2012,06,25);
        Map<String, Float> mapFonction = dao.GetBenefitsByState(dateDeb, dateFin);
        assertEquals(mapFonction.size(),1);
        assertNotEquals(mapFonction, mapVierge);
    }
    
    @Test
    public void GetBenefitsByNoStateValidDate() throws DAOException {
        //On créer une carte vierge :
        Map<String, Float> mapVierge = new HashMap<>();
        //On créer la carte modifier :
        Date dateDeb = new Date(2010,04,23);
        Date dateFin = new Date(2012,06,25);
        Map<String, Float> mapFonction = dao.GetBenefitsByState(dateDeb, dateFin);
        assertEquals(mapFonction.size(),0);
        assertEquals(mapFonction, mapVierge);
    }
    
    //Si on inverse date de debut et date de fin
    @Test
    public void GetBenefitsByStateErrorDate() throws DAOException {
        //On ajoute la commande a la bdd :        
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        //On créer une carte vierge :
        Map<String, Float> mapVierge = new HashMap<>();
        //On créer la carte modifier :
        Date dateDeb = new Date(2010,04,23);
        Date dateFin = new Date(2012,06,25);
        Map<String, Float> mapFonction = dao.GetBenefitsByState(dateFin, dateDeb);
        assertEquals(mapFonction.size(),0);
        assertEquals(mapFonction, mapVierge);
    }

    @Test
    public void GetBenefitsByCustomerWrongDate() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        Map<String, Float> mapVierge = new HashMap<>();
        Date dateDeb = new Date(2011,05,25);
        Date dateFin = new Date(2011,05,26);
        Map<String, Float> mapFonction = dao.GetBenefitsByCustomerAndDate(dateDeb, dateFin);
        assertEquals(mapFonction, mapVierge);
    }
    
    @Test
    public void GetBenefitsByCustomerAndValidDate() throws DAOException {
        //On ajoute la commande a la bdd :        
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        //On créer une carte vierge :
        Map<String, Float> mapVierge = new HashMap<>();
        //On créer la carte modifier :
        Date dateDeb = new Date(2010,04,23);
        Date dateFin = new Date(2012,06,25);
        Map<String, Float> mapFonction = dao.GetBenefitsByCustomerAndDate(dateDeb, dateFin);
        assertEquals(mapFonction.size(),1);
        assertNotEquals(mapFonction, mapVierge);
    }
    
    @Test
    public void GetBenefitsByNoCustomerAndValidDate() throws DAOException {
        //On créer une carte vierge :
        Map<String, Float> mapVierge = new HashMap<>();
        //On créer la carte modifier :
        Date dateDeb = new Date(2010,04,23);
        Date dateFin = new Date(2012,06,25);
        Map<String, Float> mapFonction = dao.GetBenefitsByCustomerAndDate(dateDeb, dateFin);
        assertEquals(mapFonction.size(),0);
        assertEquals(mapFonction, mapVierge);
    }
    
    //Si on inverse date de debut et date de fin
    @Test
    public void GetBenefitsByCustomerAndErrorDate() throws DAOException {
        //On ajoute la commande a la bdd :        
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        //On créer une carte vierge :
        Map<String, Float> mapVierge = new HashMap<>();
        //On créer la carte modifier :
        Date dateDeb = new Date(2010,04,23);
        Date dateFin = new Date(2012,06,25);
        Map<String, Float> mapFonction = dao.GetBenefitsByCustomerAndDate(dateFin, dateDeb);
        assertEquals(mapFonction.size(),0);
        assertEquals(mapFonction, mapVierge);
    }
    
    @Test
    public void findExistingProduct() throws DAOException {
        List<String> str= dao.getProductCodes();
        assertEquals("Yes",str.size(), 6);

        //..............
    }
    
    //revoir la methode get customer order by id
    @Test
    public void getCustomerOrderValidNumber() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        //Customer cust = new Customer(5, "Big Bill Company", "20 rue de la paix", "FL", "MIAMI", "www.bigbill.example.com");
        assertEquals(dao.getCustomerOrderByid(customer, 3).getCustomer(),customer);
        assertEquals(dao.getCustomerOrderByid(customer, 3).getFreight(),"test");
        assertEquals(dao.getCustomerOrderByid(customer, 3).getSaledate(),date);
    }
    
    @Test
    public void getCustomerOrderinValidNumber() throws DAOException {
        List<Customer> listCustomer = dao.GetCustomerList();
        Customer customer = listCustomer.get(0);
        Product product = new Product(3, (float) 4.00, 5, true, "test", "1", "2");
        Date date = new Date(2011,05,24);
        Order ord = new Order(3, customer, product, 5, (float) 2.00, date, date, "test");
        dao.AddOrder(ord);
        //Customer cust = new Customer(5, "Big Bill Company", "20 rue de la paix", "FL", "MIAMI", "www.bigbill.example.com");
        assertEquals(dao.getCustomerOrderByid(customer, 2),null);
    }

}
