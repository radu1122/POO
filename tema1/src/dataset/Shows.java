package dataset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import video.Video;



public final class Shows {
  private final Map<String, Video> shows = new HashMap<>();

  public Map<String, Video> getShows() {
    return shows;
  }

  /**
   * adauga show.
   */
  public void addShow(final String title, final Video video) {
    shows.put(title, video);
  }

  /**
   * trigger pe add favorite show.
   */
  public void addFavorite(final String title) {
    Video video = shows.get(title);
    video.addFavorite();
  }

  /**
   * trigger pe add view show.
   */
  public void addView(final String title) {
    Video video = shows.get(title);
    video.addView();
  }

  /**
   * trigger pe add rating show.
   */
  public void addRating(final String title, final double rating, final int season) {
    Video video = shows.get(title);
    video.addRating(rating, season);
  }

  /**
   * returneaza ratingul dupa title.
   */
  public double getRating(final String title) {
    if (shows.get(title) == null) {
      return -1;
    }
    return shows.get(title).getRating();
  }

  /**
   * returneaza rezultatul queryurilor pentru videos.
   */
  public ArrayList<Video> getListQuery(final int n, final String year, final String genre,
                                       final String movieType, final String sortType,
                                       final String queryType) {
    ArrayList<Video> shows = new ArrayList<>();

    for (Map.Entry<String, Video> element : this.shows.entrySet()) {
      boolean isGood = true;
      Video video = element.getValue();

      if (year != null) {
        if (!String.valueOf(video.getYear()).equals(year)) {
          isGood = false;
        }
      }
      if (genre != null) {
        if (!video.getGenres().contains(genre)) {
          isGood = false;
        }
      }
      if (!movieType.equals(video.getMovieType())) {
        isGood = false;
      }
      if (queryType.equals("most_viewed") && video.getViewCount() == 0) {
        isGood = false;
      }

      if (queryType.equals("favorite") && video.getFavoriteCount() == 0) {
        isGood = false;
      }

      if (isGood) {
        shows.add(video);
      }
    }
    Comparator<Video> comparator = switch (queryType) {
      case "favorite" -> (Video o1, Video o2) -> {
        if (o1.getFavoriteCount() == o2.getFavoriteCount()) {
          return o1.getTitle().compareTo(o2.getTitle());
        } else {
          return o1.getFavoriteCount() - o2.getFavoriteCount();
        }
      };
      case "ratings" -> (Video o1, Video o2) -> {
        if (o1.getRating() == o2.getRating()) {
          return o1.getTitle().compareTo(o2.getTitle());
        } else {
          return Double.compare(o1.getRating(), o2.getRating());
        }
      };
      case "most_viewed" -> (Video o1, Video o2) -> {
        if (o1.getViewCount() == o2.getViewCount()) {
          return o1.getTitle().compareTo(o2.getTitle());
        } else {
          return o1.getViewCount() - o2.getViewCount();
        }
      };
      default -> (Video o1, Video o2) ->  {
        if (o1.getDuration() == o2.getDuration()) {
          return o1.getTitle().compareTo(o2.getTitle());
        } else {
          return o1.getDuration() - o2.getDuration();
        }
      };
    };


    if (sortType.equals("asc")) {
      shows.sort(comparator);
    } else {
      shows.sort(comparator.reversed());
    }

    ArrayList<Video> finalShows = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      if (i == shows.size()) {
        break;
      }
      finalShows.add(shows.get(i));
    }

    return finalShows;
  }


}
