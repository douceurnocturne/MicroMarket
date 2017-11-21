package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

            stmt.setInt(1, or.getOrdernumber());
            stmt.setInt(2, or.getCustomer().getcustomerid());
            stmt.setInt(3, or.getProduct().getProductid());
            stmt.setInt(4, or.getQuantity());
            stmt.setFloat(5, or.getShippingcost());
            stmt.setDate(6, or.getDate());
            stmt.setDate(7, or.getDate());  /// toDo
            stmt.setString(7, or.getFreight());

            resualt = stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
        return resualt;
    }

    public void UpdateOrder(Order or) {

    }

    public int DeleteOrder(Order or) throws DAOException {
        int resualt = 0;

        String sql = "DELETE FROM APP.PURCHASE_ORDER WHERE ORDER_NUM=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, or.getOrdernumber());
            resualt = stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
        return resualt;
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
                    String manufacturers = rs.getString("NAME");
                    String description = rs.getString("DESCRIPTION");

                    Product pro = new Product(productID, price,
                            availableQuantity, available, description, manufacturers);

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

    public List<Product> getProductList() {
        List<Product> result = new LinkedList<>();
        String sql = "SELECT distinct PRODUCT_ID,PURCHASE_COST,QUANTITY_ON_HAND,"
                + " AVAILABLE,DESCRIPTION,APP.MANUFACTURER.NAME AS \"MANUFACTURER_Name\" "
                + "FROM APP.PRODUCT, APP.MANUFACTURER WHERE "
                + "APP.MANUFACTURER.MANUFACTURER_ID =APP.PRODUCT.MANUFACTURER_ID";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    int product_id = rs.getInt("PRODUCT_ID");
                    float price = rs.getFloat("PURCHASE_COST");
                    boolean available = rs.getBoolean("AVAILABLE");
                    int quantity = rs.getInt("QUANTITY_ON_HAND");
                    String desc = rs.getString("DESCRIPTION");
                    String man_name = rs.getString("MANUFACTURER_Name");

                    Product product = new Product(product_id, price, quantity,
                            available, desc, man_name);

                    result.add(product);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public List<Order> GetOrderByCustomer(Customer customer) {
        List<Order> result = new LinkedList<>();
        String sql = "SELECT * FROM APP.PURCHASE_ORDER, APP.PRODUCT, APP.MANUFACTURER"
                + " WHERE APP.MANUFACTURER.MANUFACTURER_ID=APP.PRODUCT.MANUFACTURER_ID"
                + " AND APP.PURCHASE_ORDER.PRODUCT_ID=APP.PRODUCT.PRODUCT_ID "
                + "AND PURCHASE_ORDER.CUSTOMER_ID = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customer.getcustomerid());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    //Product:
                    int productID = rs.getInt("PRODUCT_ID");
                    float price = rs.getFloat("PURCHASE_COST");
                    int availableQuantity = rs.getInt("QUANTITY_ON_HAND");
                    boolean available = rs.getBoolean("AVAILABLE");
                    String manufacturers = rs.getString("FREIGHT_COMPANY");

                    //Order:
                    int ordernumber = rs.getInt("ORDER_NUM");
                    int quantity = rs.getInt("QUANTITY");
                    float shippingcost = rs.getFloat("SHIPPING_COST");
                    Date date = rs.getDate("SALES_DATE");
                    String freight = rs.getString("FREIGHT_COMPANY");
                    String description = rs.getString("DESCRIPTION");

                    Product pro = new Product(productID, price, availableQuantity,
                            available, description, manufacturers);
                    Order order = new Order(ordernumber, customer, pro, quantity,
                            shippingcost, date, freight);

                    //Add to list
                    result.add(order);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
