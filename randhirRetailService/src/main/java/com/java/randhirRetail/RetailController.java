package com.java.randhirRetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.randhirRetail.dto.Employee;
import com.java.randhirRetail.dto.Product;
import com.java.randhirRetail.repository.ProductRepository;

/**
 * 
 * @author randhirkumar
 *
 */
@RestController
public class RetailController {
	private static final Logger logger = LoggerFactory.getLogger(RetailController.class);
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	
	
	@GetMapping("/employee")
	public Employee getEmployeeTest(@RequestParam(value = "id", defaultValue = "randhir") String id) {
		List<Employee> list= new ArrayList<Employee>();
		PreparedStatementSetter preparedStatementSetter = new PreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement arg0) throws SQLException {
				// TODO Auto-generated method stub
				arg0.setString(1, id);
			}
		};
		jdbcTemplate.query("select * from employee where empId= ?",preparedStatementSetter, new RowCallbackHandler() {
	           public void processRow(ResultSet resultSet) throws SQLException {
	               while (resultSet.next()) {
	               	Employee emp = new Employee(resultSet.getString("empName"), resultSet.getString("empId"),resultSet.getString("empSalary"));
	               	list.add(emp);
	               }
	           }
	       });
		return list.get(0);
	}
	
	@GetMapping("/employeeList")
	public List<Employee> getEmployeeList() {
		List<Employee> list= new ArrayList<Employee>();
        // try RowCallbackHandler for large data
		jdbcTemplate.query("select * from employee", new RowCallbackHandler() {
           public void processRow(ResultSet resultSet) throws SQLException {
               while (resultSet.next()) {
               	Employee emp = new Employee(resultSet.getString("empName"), resultSet.getString("empId"),resultSet.getString("empSalary"));
               	list.add(emp);
               }
           }
       });
		return list;	 
	}
	
	@PostMapping("/productList")
	public List<Product> getProductList() {
		 logger.debug("getProductList enter");
		 List<Product> list= new ArrayList<Product>();
	        // try RowCallbackHandler for large data
			jdbcTemplate.query("select * from product", new RowCallbackHandler() {
	           public void processRow(ResultSet resultSet) throws SQLException {
	               while (resultSet.next()) {
	            	   Product product = new Product(resultSet.getString("productId"), resultSet.getString("brandName"),resultSet.getString("productType"),resultSet.getString("itemId"));
	               	list.add(product);
	               }
	           }
	       });
			logger.debug("getProductList exit");
			return list;
	}
		
	
	void startBookApp() {

		logger.info("Creating tables for testing...");

        jdbcTemplate.execute("DROP TABLE books IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE books(" +
                "id SERIAL, name VARCHAR(255), price NUMERIC(15, 2))");

       

    }
}
