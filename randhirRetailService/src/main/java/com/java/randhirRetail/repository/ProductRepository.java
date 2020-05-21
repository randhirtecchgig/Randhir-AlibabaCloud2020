package com.java.randhirRetail.repository;



import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.java.randhirRetail.dto.Product;

public interface ProductRepository {

    int count();

    int save(Product product);

    int update(Product product);

    int deleteById(String productId);

    List<Product> findAll();

    List<Product>  findByProductNameAndItemId(String productName, String item);

    Optional<Product> findById(String productId);

    String findNameById(String productId);

    int[] batchInsert(List<Product> products);

    int[][] batchInsert(List<Product> products, int batchSize);

    int[] batchUpdate(List<Product> products);

    int[][] batchUpdate(List<Product> products, int batchSize);

}
