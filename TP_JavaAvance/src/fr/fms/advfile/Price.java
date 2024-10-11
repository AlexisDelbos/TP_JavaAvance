package fr.fms.advfile;

public class Price {
	
	private String nameItem;
	private double priceItem;
	
	public Price(String nameItem, double priceItem) {
 
		this.nameItem = nameItem;
		this.priceItem = priceItem;
	}
 
	public String getNameItem() {
		return nameItem;
	}
 
	public void setNameItem(String nameItem) {
		this.nameItem = nameItem;
	}
 
	public double getPriceItem() {
		return priceItem;
	}
 
	public void setPriceItem(double priceItem) {
		this.priceItem = priceItem;
	}
	
	
 
}