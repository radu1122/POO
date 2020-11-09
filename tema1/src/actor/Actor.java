package actor;

import data_set.Shows;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private final String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    private int awardsCount = 0;

    private double rating = 0;

    public double getRating() {
        return rating;
    }


    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        for (Map.Entry<ActorsAwards, Integer> element : this.awards.entrySet()) {
            this.awardsCount = element.getValue() + this.awardsCount;
        }
    }

    public int getAwardsCount() {
        return awardsCount;
    }
    public String getCareerDescription() {
        return careerDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public void calculateRating(Shows shows) {
        double rating = 0;
        int size = 0;
        for (String movie : filmography) {
            if (shows.getRating(movie) != -1) {
                if (shows.getRating(movie) != 0) {
                    rating = shows.getRating(movie) + rating;
                    size++;
                }
            }

        }
        if (rating != 0) {
            rating = rating / (double) size;
            this.rating = rating;
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
