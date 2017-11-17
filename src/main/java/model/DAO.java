package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class DAO {

    private final DataSource myDataSource;

    /**
     *
     * @param dataSource la source de données à utiliser
     */
    public DAO(DataSource dataSource) {
        this.myDataSource = dataSource;
    }

    public int AddOrder(Order or) throws DAOException {
        
        int resualt = 0;
        String sql = "INSERT INTO PURCHASE_ORDER (ORDER_NUM, CUSTOMER_ID,"
                + " PRODUCT_ID, QUANTITY, SHIPPING_COST, SALES_DATE,"
                + " SHIPPING_DATE, FREIGHT_COMPANY)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, or.GetNumber());
            stmt.setInt(2, or.GetCustomer().GetID());
            stmt.setInt(3, or.GetProduct().GetID());
            stmt.setInt(4, or.GetQuantity());
            stmt.setFloat(5, or.GetShippingCost());
            stmt.setDate(6, or.GetDate());
            stmt.setDate(7, or.GetDate());  /// toDo
            stmt.setString(7, or.GetFreight());
            
            resualt = stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
        return resualt;
    }

    public void UpdateOrder(Order or) {

    }

    public void DeleteOrder(Order or) {

    }

    public Customer Login(String user, int pass) throws DAOException {
        Customer result = null;

        String sql = "SELECT * FROM APP.CUSTOMER WHERE EMAIL=? AND CUSTOMER_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, user);
            stmt.setInt(2, pass);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    String name = rs.getString("NAME");
                    String address = rs.getString("ADDRESSLINE1");
                    String state = rs.getString("STATE");
                    String city = rs.getString("CITY");

                    result = new Customer(pass, name, address, state, city, user);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
        return result;
    }

    public List<Product> GetListProduct() {
        List<Product> result = new LinkedList<>();
        String sql = "SELECT * FROM APP.PRODUCT";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    int productID = rs.getInt("PRODUCT_ID");
                    int price = rs.getInt("PURCHASE_COST");
                    int availableQuantity = rs.getInt("QUANTITY_ON_HAND");
                    boolean available = rs.getBoolean("AVAILABLE");
                    int manufacturers = rs.getInt("MANUFACTURER_ID");

                    Product pro = new Product(productID, price,
                            availableQuantity, available, manufacturers);

                    result.add(pro);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public List<Customer> GetCustomerList() {
        List<Customer> result = new LinkedList<>();
        String sql = "SELECT * FROM APP.CUSTOMER";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    int customerID = rs.getInt("CUSTOMER_ID");
                    String name = rs.getString("NAME");
                    String address1 = rs.getString("ADDRESSLINE1");
                    String state = rs.getString("STATE");
                    String city = rs.getString("CITY");
                    String email = rs.getString("EMAIL");

                    Customer customer = new Customer(customerID, name, address1,
                            state, city, email);

                    result.add(customer);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<String> GetListCode() {
        List<String> result = new LinkedList<>();

        return result;
    }

    public float GetBenefictsByProductCodeAndDate(String productCode, Date startDate, Date endDate) {
        float result = 0;

        return result;
    }

    public float GetBenefictsByState(String state) {
        float result = 0;

        return result;
    }

    public float GetBenefictsByCustomerAndDate(Customer custmer, Date startDate, Date endDate) {
        float result = 0;

        return result;
    }

}
