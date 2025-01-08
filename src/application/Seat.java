package application;
public class Seat {
    private int id;
    private String seatNumber;
    private Session session;
    private boolean isOccupied;
    private double price;

    public Seat(int id, String seatNumber, Session session, boolean isOccupied, double price) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.session = session;
        this.isOccupied = isOccupied;
        this.price = price;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Session getSession() { return session; }
    public void setSession(Session session) { this.session = session; }

    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}