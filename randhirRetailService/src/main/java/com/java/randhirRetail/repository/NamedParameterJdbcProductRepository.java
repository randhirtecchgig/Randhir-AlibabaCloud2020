package com.java.randhirRetail.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.java.randhirRetail.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author randhirkumar
 *
 */
@Repository
public class NamedParameterJdbcProductRepository extends JdbcProductRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int update(Product product) {
        return namedParameterJdbcTemplate.update(
                "update books set price = :price where id = :id",
                new BeanPropertySqlParameterSource(product));
    }

    @Override
    public Optional<Product> findById(String productId) {
        return namedParameterJdbcTemplate.queryForObject(
                "select * from product where productId = :productId",
                new MapSqlParameterSource("productId", productId),
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
    public List<Product> findByProductNameAndItemId(String productName, String item) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("productName", "%" + productName + "%");
        mapSqlParameterSource.addValue("itemId", "%" + item + "%");

        return namedParameterJdbcTemplate.query(
                "select * from product where productName like :productName and itemId like :item",
                mapSqlParameterSource,
                (rs, rowNum) ->
                new Product(
                        rs.getString("productId"),
                        rs.getString("brandName"),
                        rs.getString("productType"),
                        rs.getString("itemId")
                )
        );
    }

}
