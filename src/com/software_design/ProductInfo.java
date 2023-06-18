package com.software_design;

public class ProductInfo {
	private String productName;
	private int productQuantity;
	private double productPrice, productTotal;
	
	ProductInfo(String productName, int productQuantity, double productTotal, double productPrice) {
		this.setProductName(productName);
		this.setProductQuantity(productQuantity);
		this.setProductTotal(productTotal);
		this.setProductPrice(productPrice);
		
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public double getProductTotal() {
		return productTotal;
	}

	public void setProductTotal(double productTotal) {
		this.productTotal = productTotal;
	}

	public void increaseProductQuantity() {
		this.productQuantity++;
		increaseProductTotal();
	}
	
	public void decreaseProductQuantity() {
		this.productQuantity--;
		decreaseProductTotal();
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	
	public void increaseProductTotal() {
		this.productTotal += this.getProductPrice();
	}
	
	public void decreaseProductTotal() {
		this.productTotal -= this.getProductPrice();
	}
}
