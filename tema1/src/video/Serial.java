package video;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

public class Serial extends Video {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    private int duration = 0;

    private double rating = 0;

    public Serial(final String title, final ArrayList<String> cast,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<Season> seasons,
                  final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        for (Season season : seasons) {
            this.duration = season.getDuration() + this.duration;
        }
        this.calculateRating();
    }

    @Override
    public void addRating(double rating, int season) {
        List<Double> ratings = this.seasons.get(season - 1).getRatings();
        ratings.add(rating);
        this.seasons.get(season - 1).setRatings(ratings);
        this.calculateRating();
    }

    private void calculateRating() {
        double rating = 0;
        for (Season season : seasons) {
            double seasonRating = 0;
            int size = season.getRatings().size();
            for (Double ratingBuff : season.getRatings()) {
                seasonRating = seasonRating + ratingBuff;
            }
            if (seasonRating != 0) {
                seasonRating = seasonRating / (double) size;
                rating = rating + seasonRating;
            }

        }
        rating = rating / (double) numberOfSeasons;
        this.rating = rating;
    }
    
    public double getRating() {
        return rating;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public final String getMovieType() {return "shows";}

}
