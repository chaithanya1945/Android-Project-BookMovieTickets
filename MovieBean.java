package movie.com.bookmovietickets;

import java.io.Serializable;

/**
 * Created by nageshtelugunti on 17/09/16.
 */
public class MovieBean implements Serializable{

    private String movieName;
    private byte[] imageArray;
    private String place;
    private long id;
    private String moviewTime;
    private String movieDate;
    private String seats;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public byte[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(byte[] imageArray) {
        this.imageArray = imageArray;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMoviewTime() {
        return moviewTime;
    }

    public void setMoviewTime(String moviewTime) {
        this.moviewTime = moviewTime;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}
