package user;

import java.util.ArrayList;
import java.util.Map;

public class User {
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

    private Map<String, Double> ratings;

    private int ratingCounter = 0;

    public void addFavorite(String movieTitle) {
        favoriteMovies.add(movieTitle);
    }

    public void addView(String movieTitle) {
        if (history.containsKey(movieTitle) == true) {
            history.put(movieTitle, history.get(movieTitle) + 1);
        } else {
            history.put(movieTitle, 1);
        }
    }

    public void addRating() {
        this.ratingCounter++;
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
                Map<String, Integer> history, ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
    }
}
