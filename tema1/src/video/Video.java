package video;

import java.util.ArrayList;

/**
 * Se extinde catre Movie si Serial.
 */
public class Video {
  /**
   * Show's title.
   */
  private final String title;
  /**
   * The year the show was released.
   */
  private final int year;
  /**
   * Show casting.
   */
  private final ArrayList<String> cast;
  /**
   * Show genres.
   */
  private final ArrayList<String> genres;

  private int favoriteCount = 0;

  private int viewCount = 0;


  public final int getFavoriteCount() {
    return favoriteCount;
  }

  public final int getViewCount() {
    return viewCount;
  }

  /**
   * se face ovverride in Movie si Serial.
   */
  public double getRating() {
    return 0;
  }

  /**
   * se incrementeaza favorite.
   */
  public final void addFavorite() {
    this.favoriteCount++;
  }

  /**
   * se adauga x viewCount.
   */
  public final void addMoreViews(final int x) {
    this.viewCount = this.viewCount + x;
  }

  /**
   * se incrementeaza viewCount.
   */
  public final void addView() {
    this.viewCount++;
  }

  public Video(final String title, final int year,
               final ArrayList<String> cast, final ArrayList<String> genres) {
    this.title = title;
    this.year = year;
    this.cast = cast;
    this.genres = genres;
  }

  /**
   * se face ovverride in Movie si Serial.
   */
  public void addRating(final double rating, final int season) {
  }

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

  /**
   * se face ovverride in Movie si Serial.
   */
  public String getMovieType() {
    return "";
  }

  /**
   * se face ovverride in Movie si Serial.
   */
  public int getDuration() {
    return 0;
  }

  /**
   * se face ovverride aici.
   */
  @Override
  public String toString() {
    return title;
  }
}
