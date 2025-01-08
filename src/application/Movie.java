package application;
public class Movie {
    private int id;
    private String title;
    private int year;
    private String genre;
    private String summary;
    private byte[] posterImage;

    public Movie(){
        this.id = 0;
        this.title = null;
        this.year = 0;
        this.genre = null;
        this.summary = null;
        this.posterImage = null;
    }

    public Movie(int id, String title, int year, String genre,  String summary, byte[] posterImage) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.summary = summary;
        this.posterImage = posterImage;
    }

    // Getter ve Setter metodları
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public byte[] getPosterImage() { return posterImage; }
    public void setPosterImage(byte[] posterImage) { this.posterImage = posterImage; }


}