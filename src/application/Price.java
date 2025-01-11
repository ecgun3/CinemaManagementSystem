package application;

public class Price {
    
    private double price;
    private int taxPercentage;
    private int discountPercentage;

    public Price(){
        this.price = 0;
        this.taxPercentage = 0;
        this.discountPercentage = 0;
    }
    
    public Price(double price, int taxPercentage, int discountPercentage){
        this.price = price;
        this.taxPercentage = taxPercentage;
        this.discountPercentage = discountPercentage;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getTaxPercentage() {
        return taxPercentage;
    }
    public void setTaxPercentage(int taxPercentage) {
        this.taxPercentage = taxPercentage;
    }
    public int getDiscountPercentage() {
        return discountPercentage;
    }
    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

}
