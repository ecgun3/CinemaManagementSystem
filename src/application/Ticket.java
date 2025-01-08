package application;
import java.time.LocalDateTime;

// Ticket.java
public class Ticket {
    private int id;
    private Customer customer;
    private Seat seat;
    private double originalPrice;
    private double discountedPrice;
    private double vatAmount;
    private LocalDateTime purchaseDateTime;

    public Ticket(int id, Customer customer, Seat seat, double originalPrice,
                  double discountedPrice, double vatAmount, LocalDateTime purchaseDateTime) {
        this.id = id;
        this.customer = customer;
        this.seat = seat;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.vatAmount = vatAmount;
        this.purchaseDateTime = purchaseDateTime;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }

    public double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(double originalPrice) { this.originalPrice = originalPrice; }

    public double getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(double discountedPrice) { this.discountedPrice = discountedPrice; }

    public double getVatAmount() { return vatAmount; }
    public void setVatAmount(double vatAmount) { this.vatAmount = vatAmount; }

    public LocalDateTime getPurchaseDateTime() { return purchaseDateTime; }
    public void setPurchaseDateTime(LocalDateTime purchaseDateTime) { this.purchaseDateTime = purchaseDateTime; }
}