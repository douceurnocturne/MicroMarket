package model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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

    
    public void AddOrder(Order or){
        
    }
    
    public void UpdateOrder(Order or) {
        
    }
    
    public void DeleteOrder(Order or) {
        
    }
    
    public void Login(String user, int pass) {
        
    }
    
    public List<Product> GetListProduct(){
        List<Product> result = new LinkedList<>();
        
        return result;
    }
    
    public List<Customer> GetCustomerList(){
        List<Customer> result = new LinkedList<>();
        
        return result;
    }
    
    public List<String> GetListCode(){
        List<String> result = new LinkedList<>();
        
        return result;
    }  
    
    public float GetBenefictsByDate(String productCode, Date startDate, Date endDate){
        float result = 0;
        
        return result;
    }
    
    
    
    
    
    
}
