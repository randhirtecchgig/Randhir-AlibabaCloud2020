package com.java.randhirRetail.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RetailCustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(RetailCustomer retailCustomer) {
        return jdbcTemplate.update(
                "insert into customer (name, age, created_date) values(?,?,?)",
                retailCustomer.getName(), retailCustomer.getAge(), LocalDateTime.now());
    }

    public RetailCustomer findByCustomerId(Long id) {

        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RetailCustomerRowMapper());

    }

    public RetailCustomer findByCustomerId2(Long id) {

        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";

        return (RetailCustomer) jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper(RetailCustomer.class));

    }

    public RetailCustomer findByCustomerId3(Long id) {

        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new RetailCustomer(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getTimestamp("created_date").toLocalDateTime()
                ));

    }

    public List<RetailCustomer> findAll() {

        String sql = "SELECT * FROM CUSTOMER";

        List<RetailCustomer> retailCustomers = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map row : rows) {
            RetailCustomer obj = new RetailCustomer();

            obj.setID(((Integer) row.get("ID")).longValue());
            //obj.setID(((Long) row.get("ID"))); no, ClassCastException
            obj.setName((String) row.get("NAME"));
            obj.setAge(((BigDecimal) row.get("AGE")).intValue()); // Spring returns BigDecimal, need convert
            obj.setCreatedDate(((Timestamp) row.get("CREATED_DATE")).toLocalDateTime());
            retailCustomers.add(obj);
        }

        return retailCustomers;
    }

    public List<RetailCustomer> findAll2() {

        String sql = "SELECT * FROM CUSTOMER";

        List<RetailCustomer> retailCustomers = jdbcTemplate.query(
                sql,
                new RetailCustomerRowMapper());

        return retailCustomers;
    }

    public List<RetailCustomer> findAll3() {

        String sql = "SELECT * FROM CUSTOMER";

        List<RetailCustomer> retailCustomers = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(RetailCustomer.class));

        return retailCustomers;
    }

    public List<RetailCustomer> findAll4() {

        String sql = "SELECT * FROM CUSTOMER";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new RetailCustomer(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getInt("age"),
                                rs.getTimestamp("created_date").toLocalDateTime()
                        )
        );
    }

    public String findCustomerNameById(Long id) {

        String sql = "SELECT NAME FROM CUSTOMER WHERE ID = ?";

        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, String.class);

    }

    public int count() {

        String sql = "SELECT COUNT(*) FROM CUSTOMER";

        // queryForInt() is Deprecated
        

        return jdbcTemplate.queryForObject(sql, Integer.class);

    }

}
