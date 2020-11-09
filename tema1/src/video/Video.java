package video;

import java.util.ArrayList;

public class Video {
    /**
     * Show's title
     */
    private String title;
    /**
     * The year the show was released
     */
    private int year;
    /**
     * Show casting
     */
    private ArrayList<String> cast;
    /**
     * Show genres
     */
    private ArrayList<String> genres;

    private int favoriteCount = 0;

    private int viewCount = 0;

    public double getRating() {return 0;};

    public void addFavorite() {
        this.favoriteCount++;
    }
    public void addView() {
        this.viewCount++;
    }

    public Video(final String title, final int year,
                 final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public void addRating(double rating, int season)  { return; }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

}
