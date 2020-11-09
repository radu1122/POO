package data_set;

import user.User;
import video.Video;

import java.util.HashMap;
import java.util.Map;

public class Shows {
    private Map<String, Video> shows = new HashMap<String, Video>();

    public void addShow(String title, Video video) {
        shows.put(title, video);
    }

    public void addFavorite(String title) {
        Video video = shows.get(title);
        video.addFavorite();
    }

    public void addView(String title) {
        Video video = shows.get(title);
        video.addView();
    }

    public void addRating(String title, double rating, int season) {
        Video video = shows.get(title);
        video.addRating(rating, season);
    }

    public double getRating(String title) {
        return shows.get(title).getRating();
    }
}
