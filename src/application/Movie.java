package application;
public class Movie {
    private int id;
    private String title;
    private int year;
    private String genre;
    private String summary;

    private String poster; // URL için

    public Movie(){
        this.id = 0;
        this.title = null;
        this.year = 0;
        this.genre = null;
        this.summary = null;
        this.poster = null;
    }

    public Movie(int id, String title, int year, String genre,  String summary, String poster) { //, double price
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.summary = summary;
        this.poster = poster; // veya URL'yi buraya ekle
    }

    // Getter ve Setter metodları

    //ID
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    //Title
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }


    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    //Summary
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

}