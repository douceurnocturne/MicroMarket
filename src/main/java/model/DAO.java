package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
            
            if (or.getOrdernumber()>0 && or.getCustomer().getID()>0 && or.getProduct().getProductid()>0 && 
                    or.getQuantity()>0 && or.getShippingcost()>0.0){
                stmt.setInt(1, or.getOrdernumber());
                stmt.setInt(2, or.getCustomer().getcustomerid());
                stmt.setInt(3, or.getProduct().getProductid());
                stmt.setInt(4, or.getQuantity());
                stmt.setFloat(5, or.getShippingcost());
                stmt.setDate(6, or.getSaledate());
                stmt.setDate(7, or.getShippingdate());
                stmt.setString(8, or.getFreight());

                resualt = stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
        return resualt;
    }

    public int UpdateOrder(Order or) throws DAOException {
        int resualt = 0;
        String sql = "UPDATE PURCHASE_ORDER "
                + "SET PRODUCT_ID = ?, QUANTITY = ?, SHIPPING_COST = ?, "
                + "SALES_DATE = ?, SHIPPING_DATE = ?, FREIGHT_COMPANY = ? "
                + "WHERE ORDER_NUM = ? ";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (or.getOrdernumber()>0 && or.getCustomer().getID()>0 && or.getProduct().getProductid()>0 && 
                or.getQuantity()>0 && or.getShippingcost()>0.0){
            stmt.setInt(1, or.getProduct().getProductid());
            stmt.setInt(2, or.getQuantity());
            stmt.setFloat(3, or.getShippingcost());
            stmt.setDate(4, or.getSaledate());
            stmt.setDate(5, or.getShippingdate());
            stmt.setString(6, or.getFreight());
            stmt.setInt(7, or.getOrdernumber());

            resualt = stmt.executeUpdate();
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }

        return resualt;
    }

    public int DeleteOrder(int ordernumber) throws DAOException {
        int resualt = 0;

        String sql = "DELETE FROM PURCHASE_ORDER WHERE ORDER_NUM=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, ordernumber);
            resualt = stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }
        return resualt;
    }

    public Customer Login(String user, int pass) throws DAOException {
        Customer result = null;

        String sql = "SELECT * FROM CUSTOMER WHERE EMAIL=? AND CUSTOMER_ID=?";
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

    public List<String> getProductCodes() {
        List<String> result = new LinkedList<>();
        String sql = "SELECT distinct PROD_CODE FROM PRODUCT_CODE";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String cat = rs.getString("PROD_CODE");
                    result.add(cat);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Customer> GetCustomerList() {
        List<Customer> result = new LinkedList<>();
        String sql = "SELECT * FROM CUSTOMER";
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

    public List<Product> getProductList(String cat) {
        List<Product> result = new LinkedList<>();
        String sql = "SELECT distinct PRODUCT_ID,PURCHASE_COST,QUANTITY_ON_HAND,"
                + " AVAILABLE,DESCRIPTION,MANUFACTURER.NAME AS \"MANUFACTURER_Name\" "
                + "FROM PRODUCT, MANUFACTURER WHERE "
                + "PRODUCT.PRODUCT_CODE=? AND "
                + "MANUFACTURER.MANUFACTURER_ID =PRODUCT.MANUFACTURER_ID";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cat);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    int product_id = rs.getInt("PRODUCT_ID");
                    float price = rs.getFloat("PURCHASE_COST");
                    boolean available = rs.getBoolean("AVAILABLE");
                    int quantity = rs.getInt("QUANTITY_ON_HAND");
                    String desc = rs.getString("DESCRIPTION");
                    String man_name = rs.getString("MANUFACTURER_Name");
                    Product product = new Product(product_id, price, quantity,
                            available, desc, man_name, cat);

                    result.add(product);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Map<String, Float> GetBenefitsByProductCodesAndDate(Date startDate, Date endDate) {

        Map<String, Float> result = new HashMap<>();

        String sql = "SELECT PRODUCT_CODE.DESCRIPTION, "
                + "SUM(PRODUCT.PURCHASE_COST*PURCHASE_ORDER.QUANTITY) "
                + "AS Earnings FROM PRODUCT,PURCHASE_ORDER,PRODUCT_CODE "
                + "WHERE PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID "
                + "AND PRODUCT_CODE.PROD_CODE=PRODUCT.PRODUCT_CODE "
                + "AND PURCHASE_ORDER.SALES_DATE BETWEEN ? AND ? "
                + "GROUP BY PRODUCT_CODE.DESCRIPTION";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    float earning = rs.getFloat("EARNINGS");
                    String desc = rs.getString("DESCRIPTION");
                    result.put(desc, earning);

                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public Map<String, Float> GetBenefitsByState(Date startDate, Date endDate) {

        Map<String, Float> result = new HashMap<>();

        String sql = "SELECT CUSTOMER.STATE, "
                + "SUM(PURCHASE_ORDER.QUANTITY*PRODUCT.PURCHASE_COST) AS earnings "
                + "FROM CUSTOMER,PURCHASE_ORDER, PRODUCT "
                + "WHERE PURCHASE_ORDER.CUSTOMER_ID=CUSTOMER.CUSTOMER_ID "
                + "AND PRODUCT.PRODUCT_ID=PURCHASE_ORDER.PRODUCT_ID "
                + "AND PURCHASE_ORDER.SALES_DATE BETWEEN ? AND ? "
                + "GROUP BY CUSTOMER.STATE";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    float earning = rs.getFloat("EARNINGS");
                    String desc = rs.getString("STATE");
                    result.put(desc, earning);

                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public Map<String, Float> GetBenefitsByCustomerAndDate(Date startDate, Date endDate) {

        Map<String, Float> result = new HashMap<>();

        String sql = "SELECT CUSTOMER.NAME, "
                + "SUM(PRODUCT.PURCHASE_COST*PURCHASE_ORDER.QUANTITY) "
                + "AS EARNINGS FROM "
                + "PURCHASE_ORDER, CUSTOMER, PRODUCT "
                + "WHERE CUSTOMER.CUSTOMER_ID=PURCHASE_ORDER.CUSTOMER_ID "
                + "AND PURCHASE_ORDER.PRODUCT_ID=PRODUCT.PRODUCT_ID "
                + "AND PURCHASE_ORDER.SALES_DATE BETWEEN ? AND ? "
                + "GROUP BY CUSTOMER.NAME";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    float earning = rs.getFloat("EARNINGS");
                    String desc = rs.getString("NAME");
                    result.put(desc, earning);

                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public Product getProductByid(int productid) throws DAOException {
        Product product = null;
        String sql = "SELECT PRODUCT.PRODUCT_ID, PRODUCT.PRODUCT_CODE, "
                + "PRODUCT.PURCHASE_COST, PRODUCT.QUANTITY_ON_HAND, "
                + "PRODUCT.AVAILABLE,PRODUCT.DESCRIPTION, "
                + "PRODUCT.PRODUCT_CODE, MANUFACTURER.NAME AS MANAME,"
                + "PRODUCT.MANUFACTURER_ID"
                + " FROM MANUFACTURER,PRODUCT WHERE "
                + "MANUFACTURER.MANUFACTURER_ID=PRODUCT.MANUFACTURER_ID"
                + " AND PRODUCT.PRODUCT_ID=?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, productid);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    float price = rs.getFloat("PURCHASE_COST");
                    int availableQuantity = rs.getInt("QUANTITY_ON_HAND");
                    boolean available = rs.getBoolean("AVAILABLE");
                    String description = rs.getString("DESCRIPTION");
                    String manufacturers = rs.getString("MANAME");
                    int manufacturersid = rs.getInt("MANUFACTURER_ID");
                    String cat = rs.getString("PRODUCT_CODE");
                    product = new Product(productid, price, availableQuantity,
                            available, description, manufacturers, cat);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);
            throw new DAOException(ex.getMessage());
        }

        return product;

    }

    public Order getCustomerOrderByid(Customer c, int ordernumber) {

        Order order = null;
        String sql = "SELECT * FROM PURCHASE_ORDER WHERE ORDER_NUM = ?";
        try (Connection connection = myDataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, ordernumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    float shipping_cost = rs.getFloat("SHIPPING_COST");
                    int quantity = rs.getInt("QUANTITY");
                    int productid = rs.getInt("PRODUCT_ID");
                    java.sql.Date sale_date = new java.sql.Date(rs.getDate("SALES_DATE").getTime());
                    java.sql.Date shipping_date = new java.sql.Date(rs.getDate("SHIPPING_DATE").getTime());
                    String freight = rs.getString("FREIGHT_COMPANY");

                    order = new Order(ordernumber, c, this.getProductByid(productid), quantity, shipping_cost,
                            sale_date, shipping_date, freight);
                }
            } catch (DAOException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger("DAO").log(Level.SEVERE, null, ex);

        }

        return order;
    }

    public List<Order> GetOrderByCustomer(Customer customer) {
        List<Order> result = new LinkedList<>();
        String sql = "SELECT * FROM PURCHASE_ORDER, PRODUCT, MANUFACTURER"
                + " WHERE MANUFACTURER.MANUFACTURER_ID=PRODUCT.MANUFACTURER_ID"
                + " AND PURCHASE_ORDER.PRODUCT_ID=PRODUCT.PRODUCT_ID "
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
                    Date saledate = rs.getDate("SALES_DATE");
                    Date shippingdate = rs.getDate("SHIPPING_DATE");
                    String freight = rs.getString("FREIGHT_COMPANY");
                    String description = rs.getString("DESCRIPTION");
                    String cat = rs.getString("PRODUCT_CODE");

                    Product pro = new Product(productID, price, availableQuantity,
                            available, description, manufacturers, cat);
                    Order order = new Order(ordernumber, customer, pro, quantity,
                            shippingcost, saledate, shippingdate, freight);

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
