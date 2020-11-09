package user;

import data_set.Shows;
import org.jetbrains.annotations.NotNull;
import video.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Comparable {
    private String username;
    /**
     * Subscription Type
     */
    private String subscriptionType;
    /**
     * The history of the movies seen
     */
    private Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private ArrayList<String> favoriteMovies;

    private final Map<String, Double> rating = new HashMap<>();


    private int ratingCounter = 0;

    public int addFavorite(String movieTitle) {
        if (favoriteMovies.contains(movieTitle)) {
            return 0;
        } else if (!history.containsKey(movieTitle)) {
            return 2;
        } else {
            favoriteMovies.add(movieTitle);
            return 1;
        }
    }

    public int addView(String movieTitle) {
        if (history.containsKey(movieTitle)) {
            history.put(movieTitle, history.get(movieTitle) + 1);
        } else {
            history.put(movieTitle, 1);
        }
        return history.get(movieTitle);
    }

    public int addRating(String movieTitle, Double value) {
        if (rating.containsKey(movieTitle)) {
            return 2;
        }
        if (history.containsKey(movieTitle)) {
            this.ratingCounter++;
            rating.put(movieTitle, value);
            return 1;
        }
        return 0;
    }

    public int getRating() {
        return this.ratingCounter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public User(String username, String subscriptionType,
                Map<String, Integer> history, ArrayList<String> favoriteMovies, Shows showsRaw) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;

        Map<String, Video> shows = showsRaw.getShows();
        for (Map.Entry<String, Integer> element : history.entrySet()) {
            shows.get(element.getKey()).addMoreViews(element.getValue());
        }
        for (String element : favoriteMovies) {
            shows.get(element).addFavorite();
        }
    }

    @Override
    public int compareTo(@NotNull Object o) {
        int compare = ((User)o).getRating();
        return this.ratingCounter - compare;
    }

    @Override
    public String toString() {
        return username;
    }
}
