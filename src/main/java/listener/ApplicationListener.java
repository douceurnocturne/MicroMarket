package listener;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import model.DAO;
import model.DataSourceFactory;
import org.apache.derby.tools.ij;

/**
 * Web application lifecycle listener, initialise la base de données au
 * démarrage de l'application si nécessaire
 */
@WebListener()
public class ApplicationListener implements ServletContextListener, HttpSessionListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (!databaseExists()) {
            initializeDatabase();
        }
        sce.getServletContext().setAttribute("numberConnected", 0);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private boolean databaseExists() {
        boolean result = false;

        DAO dao = new DAO(DataSourceFactory.getDataSource(DataSourceFactory.DriverType.server));
        try {
            dao.getProductCodes();
            Logger.getLogger("MicroMarket").log(Level.INFO, "Database already exists");
            result = true;
        } catch (Exception ex) {
            Logger.getLogger("MicroMarket").log(Level.INFO, "Database does not exist");
        }
        return result;
    }

    private void initializeDatabase() {
        OutputStream nowhere = new OutputStream() {
            @Override
            public void write(int b) {
            }
        };

        Logger.getLogger("MicroMarket").log(Level.INFO, "Creating databse from SQL script");
        try {
            Connection connection = DataSourceFactory.getDataSource(DataSourceFactory.DriverType.server).getConnection();
            int result = ij.runScript(connection, this.getClass().getResourceAsStream("CREATE.sql"), "UTF-8", System.out, "UTF-8");
            if (result == 0) {
                Logger.getLogger("MicroMarket").log(Level.INFO, "Database succesfully created");
            } else {
                Logger.getLogger("MicroMarket").log(Level.SEVERE, "Errors creating database");
            }
        } catch (UnsupportedEncodingException | SQLException e) {
            Logger.getLogger("MicroMarket").log(Level.SEVERE, null, e);
        }

    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("sessionCreated - add one session into counter");
        se.getSession().getServletContext().log("Creating session");
        // On incrémente le nombre d'utilisateurs
        int connected = (Integer) se.getSession().getServletContext().getAttribute("numberConnected");
        connected++;
        // On stocke ce nombre dans le contexte d'application
        se.getSession().getServletContext().setAttribute("numberConnected", connected);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("sessionDestroyed - deduct one session from counter");
		se.getSession().getServletContext().log("Destroying session");
                int connected=0;
                HttpSession session = se.getSession();
                if (session != null ) {
                    connected = (Integer) session.getAttribute("numberConnected");
		connected--;
                session.getServletContext().setAttribute("numberConnected", connected);
                } 
		// On décrémente le nombre d'utilisateurs
		
		// On stocke ce nombre dans le contexte d'application
		
    }
    
}
