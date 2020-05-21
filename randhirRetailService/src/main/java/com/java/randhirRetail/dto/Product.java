package com.java.randhirRetail.dto;

import java.math.BigDecimal;

/**
 * 
 * @author randhirkumar
 *
 */
public class Product {

    private String productId;
    private String brandName;
    private String productType;
    private String itemId;

    public Product(String productId,String brandName,String productType,String itemId) {
    	this.productId = productId;
    	this.brandName = brandName;
    	this.productType = productType;
    	this.itemId = itemId;
    }
    public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getBrandName() {
		return brandName;
	}


	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getProductType() {
		return productType;
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	@Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", brandName='" + productType + '\'' +
                ", itemId=" + itemId +
                 ", brandName=" + brandName +
                '}';
    }
}