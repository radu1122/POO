package video;

import java.util.ArrayList;

public final class Movie extends Video {
  private final int duration;

  private double rating = 0;
  private int ratingCount = 0;

  @Override
  public int getDuration() {
    return duration;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(final double rating) {
    this.rating = rating;
  }

  @Override
  public void addRating(final double rating, final int season) {
    if (this.rating == 0) {
      this.rating = rating;
      this.ratingCount++;
    } else {
      this.rating = this.rating * (double) ratingCount;
      this.rating = this.rating + rating;
      this.ratingCount++;
      this.rating = this.rating / (double) ratingCount;
    }
  }

  public Movie(final String title, final ArrayList<String> cast,
               final ArrayList<String> genres, final int year,
               final int duration) {
    super(title, year, cast, genres);
    this.duration = duration;
  }

  @Override
  public String getMovieType() {
    return "movies";
  }
}
