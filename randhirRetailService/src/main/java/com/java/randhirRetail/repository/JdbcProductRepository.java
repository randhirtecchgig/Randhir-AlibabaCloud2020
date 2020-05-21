package com.java.randhirRetail.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.java.randhirRetail.dto.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 
 * @author randhirkumar
 *
 */
@Repository
public class JdbcProductRepository implements ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public int save(Product product) {
        return jdbcTemplate.update(
                "insert into product (productId, brandName,productType,itemId) values(?,?,?,?)",
                product.getProductId(), product.getBrandName(),product.getProductType(),product.getItemId());
    }

    @Override
    public int update(Product product) {
        return jdbcTemplate.update(
                "update product set itemId = ? where productId = ?",
                product.getItemId(), product.getProductId());
    }


    @Override
    public int deleteById(String  productId) {
        return jdbcTemplate.update(
                "delete product where id = ?",
                productId);
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(
                "select * from product",
                (rs, rowNum) ->
                	new Product(
                        rs.getString("productId"),
                        rs.getString("brandName"),
                        rs.getString("productType"),
                        rs.getString("itemId")
                	));
    }

    // jdbcTemplate.queryForObject, populates a single object
    @Override
    public Optional<Product> findById(String productId) {
        return jdbcTemplate.queryForObject(
                "select * from product where id = ?",
                new Object[]{productId},
                (rs, rowNum) ->
                        Optional.of(new Product(
                                rs.getString("productId"),
                                rs.getString("brandName"),
                                rs.getString("productType"),
                                rs.getString("itemId")
                        	))
        		);
    }

    @Override
    public List<Product> findByProductNameAndItemId(String productname, String itemId) {
        return jdbcTemplate.query(
                "select * from product where productname like ? and itemId <= ?",
                new Object[]{"%" + productname + "%", itemId},
                (rs, rowNum) ->
                	new Product(
                        rs.getString("productId"),
                        rs.getString("brandName"),
                        rs.getString("productType"),
                        rs.getString("itemId")
                	)
        		);
    }

    @Override
    public String findNameById(String productId) {
        return jdbcTemplate.queryForObject(
                "select name from product where productId = ?",
                new Object[]{productId},
                String.class
        );
    }

    @Override
    public int[] batchUpdate(List<Product> products) {

        return this.jdbcTemplate.batchUpdate(
                "update product set brandName = ? where productId = ?",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, products.get(i).getBrandName());
                        ps.setString(2, products.get(i).getProductId());
                    }

                    public int getBatchSize() {
                        return products.size();
                    }

                });

    }

    @Override
    public int[][] batchUpdate(List<Product> products, int batchSize) {

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "update product set itemId = ? where productId = ?",
                products,
                batchSize,
                new ParameterizedPreparedStatementSetter<Product>() {
                    public void setValues(PreparedStatement ps, Product argument) throws SQLException {
                        ps.setString(1, argument.getItemId());
                        ps.setString(2, argument.getProductId());
                    }
                });
        return updateCounts;

    }

    @Override
    public int[] batchInsert(List<Product> products) {

        return this.jdbcTemplate.batchUpdate(
                "insert into product (itemId, productId,brandName,productType) values(?,?,?,?)",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, products.get(i).getItemId());
                        ps.setString(2, products.get(i).getProductId());
                        ps.setString(3, products.get(i).getBrandName());
                        ps.setString(4, products.get(i).getProductType());
                    }

                    public int getBatchSize() {
                        return products.size();
                    }

                });
    }

    // Any failure causes the entire operation to roll back, none of the book will be added
    @Transactional
    @Override
    public int[][] batchInsert(List<Product> products, int batchSize) {

        int[][] updateCounts = jdbcTemplate.batchUpdate(
        	    "insert into product (itemId, productId,brandName,productType) values(?,?,?,?)",
                products,
                batchSize,
                new ParameterizedPreparedStatementSetter<Product>() {
                    public void setValues(PreparedStatement ps, Product product) throws SQLException {
                    	 ps.setString(1, product.getItemId());
                         ps.setString(2, product.getProductId());
                         ps.setString(3, product.getBrandName());
                         ps.setString(4, product.getProductType());
                    }
                });
        return updateCounts;

    }

  }