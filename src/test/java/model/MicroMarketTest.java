/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import static model.DataSourceFactory.getDataSource;
import org.apache.derby.tools.ij;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Ehsan
 */
public class MicroMarketTest {

    private static DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection;
    private DAO dao;

    @Before
    public void setUp() throws SQLException, IOException, URISyntaxException {
        // On crée la connection vers la base de test "in memory"
        myDataSource = getDataSource(DataSourceFactory.DriverType.embedded);
        myConnection = myDataSource.getConnection();
        // On initialise la base avec le contenu d'un fichier de test
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("model/DROP.sql").getFile());

        ij.runScript(myConnection, this.getClass().getResourceAsStream("genDrop.sql"), "UTF-8", new FileOutputStream(file), "UTF-8");

        ij.runScript(myConnection, this.getClass().getResourceAsStream("DROP.sql"), "UTF-8", System.out, "UTF-8");

        int result = ij.runScript(myConnection, this.getClass().getResourceAsStream("CREATE.sql"), "UTF-8", System.out, "UTF-8");
        if (result == 0) {
            Logger.getLogger("MicroMarket").log(Level.INFO, "Database succesfully created");
        } else {
            Logger.getLogger("MicroMarket").log(Level.SEVERE, "Errors creating database");
        }

        // On crée l'objet à tester
        dao = new DAO(myDataSource);
    }

    @After
    public void tearDown() throws SQLException {

        myConnection.close();
        dao = null; // Pas vraiment utile
    }

    @Test
    public void findExistingProduct() throws DAOException {
        Product product = dao.getProductByid(980001);
        assertEquals("Yes", product.getCode(), "SW");

        //..............
    }

}
