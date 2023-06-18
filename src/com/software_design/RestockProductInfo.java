package com.software_design;

public class RestockProductInfo {
	
	private String productBarcode;
	private String productName;
	private String unit;
	private int quantityPerUnit;
	private int quantity;
	private double price, total;
	
	public RestockProductInfo(String productName, String unit, int quantityPerUnit, int quantity, double price, double total, String productBarcode) {
		this.setProductBarcode(productBarcode);
		this.setProductName(productName);
		this.setUnit(unit);
		this.setQuantityPerUnit(quantityPerUnit);
		this.setQuantity(quantity);
		this.setPrice(price);
		this.setTotal(total);
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getQuantityPerUnit() {
		return quantityPerUnit;
	}
	public void setQuantityPerUnit(int quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public void increaseProductQuantity() {
		this.quantity++;
		increaseProductTotal();
	}
	public void decreaseProductQuantity() {
		this.quantity--;
		decreaseProductTotal();
	}
	
	public void increaseQuantityPerUnit() {
		this.quantityPerUnit++;
	}
	public void decreaseQuantityPerUnit() {
		this.quantityPerUnit--;
	}
	
	public void increaseProductTotal() {
		this.total += this.getPrice();
	}
	
	public void decreaseProductTotal() {
		this.total -= this.getPrice();
	}

	public String getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(String productBarcode) {
		this.productBarcode = productBarcode;
	}
	
	
}
