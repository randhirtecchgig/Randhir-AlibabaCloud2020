package com.java.randhirRetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import com.java.randhirRetail.customer.RetailCustomerRepository;
import com.java.randhirRetail.repository.ProductRepository;

@SpringBootApplication
public class RestServiceApplication {
	
	
    @Autowired
    //@Qualifier("jdbcBookRepository")              
    @Qualifier("namedParameterJdbcBookRepository")  
    protected   ProductRepository productRepository;

    @Autowired
    protected  RetailCustomerRepository retailCustomerRepository;

    
    public static void main(String[] args) {
        	SpringApplication.run(RestServiceApplication.class, args);
    }
    
    

}
