import java.time.LocalDateTime;

// Session.java
public class Session {
    private int id;
    private Movie movie;
    private LocalDateTime dateTime;
    private String hall;
    private int totalSeats;
    private int availableSeats;

    public Session(int id, Movie movie, LocalDateTime dateTime, String hall, int totalSeats, int availableSeats) {
        this.id = id;
        this.movie = movie;
        this.dateTime = dateTime;
        this.hall = hall;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public String getHall() { return hall; }
    public void setHall(String hall) { this.hall = hall; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}
