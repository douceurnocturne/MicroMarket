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
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Ehsan
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
    public void findExistingProduct() throws DAOException {
        List<String> str= dao.getProductCodes();
        assertEquals("Yes",str.size(), 6);

        //..............
    }

}
