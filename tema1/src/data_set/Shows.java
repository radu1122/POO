package data_set;

import video.Video;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Shows {
    private final Map<String, Video> shows = new HashMap<>();

    public Map<String, Video> getShows() {
        return shows;
    }

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
        if (shows.get(title) == null) {
            return -1;
        }
        return shows.get(title).getRating();
    }

    public ArrayList<Video> getListQuery(int n, String year, String genre, String movieType, String sortType, String queryType) {
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
            if (isGood) {
                shows.add(video);
            }
        }
        Comparator<Video> comparator = switch (queryType) {
            case "rating" -> Comparator.comparing(Video::getRating);
            case "favorite" -> Comparator.comparing(Video::getFavoriteCount);
            case "most_viewed" -> Comparator.comparing(Video::getViewCount);
            default -> Comparator.comparing(Video::getDuration);
        };

        if (sortType.equals("asc")) {
            shows.sort(comparator);
        } else {
            assert comparator != null;
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
