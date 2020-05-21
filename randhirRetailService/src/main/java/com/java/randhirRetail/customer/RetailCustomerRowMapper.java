package com.java.randhirRetail.customer;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RetailCustomerRowMapper implements RowMapper<RetailCustomer> {

    @Override
    public RetailCustomer mapRow(ResultSet rs, int rowNum) throws SQLException {

        RetailCustomer retailCustomer = new RetailCustomer();
        retailCustomer.setID(rs.getLong("ID"));
        retailCustomer.setName(rs.getString("NAME"));
        retailCustomer.setAge(rs.getInt("AGE"));
        retailCustomer.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());

        return retailCustomer;

    }
}
