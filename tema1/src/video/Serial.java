package video;

import entertainment.Season;

import java.util.ArrayList;
import java.util.List;

public class Serial extends Video {
    /**
     * Number of seasons
     */
    private int numberOfSeasons;
    /**
     * Season list
     */
    private ArrayList<Season> seasons;

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
    }

    @Override
    public void addRating(double rating, int season) {
        List<Double> ratings = this.seasons.get(season).getRatings();
        ratings.add(rating);
        this.seasons.get(season).setRatings(ratings);
        this.calculeRating();
    }

    private void calculeRating() {
        double rating = 0;
        for (Season season : seasons) {
            double seasonRating = 0;
            int size = season.getRatings().size();
            for (Double ratingBuff : season.getRatings()) {
                seasonRating = seasonRating + ratingBuff;
            }
            seasonRating = seasonRating / (double) size;
            rating = rating + seasonRating;
        }
        rating = rating / (double) numberOfSeasons;
        this.rating = rating;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public int getDuration() {
        return duration;
    }
}
