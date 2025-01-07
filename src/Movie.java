public class Movie {
    private int id;
    private String title;
    private String genre;
    private String summary;
    private byte[] posterImage;
    private double price;

    public Movie(int id, String title, String genre, String summary, byte[] posterImage, double price) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.summary = summary;
        this.posterImage = posterImage;
        this.price = price;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public byte[] getPosterImage() { return posterImage; }
    public void setPosterImage(byte[] posterImage) { this.posterImage = posterImage; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}