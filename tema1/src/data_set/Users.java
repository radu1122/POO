package data_set;

import actor.Actor;
import user.User;
import video.Video;

import java.util.*;

public class Users {
    private final Map<String, User> users = new HashMap< >();

    public void addUser(String username, User user) {
        this.users.put(username, user);
    }

    public Map<String, User> getUsers() {
        return this.users;
    }

    public ArrayList<User> getRatingList(int n, String sortType) {
        ArrayList<User> users = new ArrayList<>();

        // get all the users into an arrayList
        for (Map.Entry<String, User> mapElement : this.users.entrySet()) {
            User value = mapElement.getValue();
            if (value.getRating() != 0) {
                users.add(value);
            }
        }

        Comparator<User> compareByRatingUser = (User o1, User o2) -> {
            if (o1.getRating() == o2.getRating()) {
                return o1.getUsername().compareTo(o2.getUsername());
            } else {
                return Double.compare(o1.getRating(), o2.getRating());
            }
        };

        // sort the array
        if (sortType.equals("asc")) {
            users.sort(compareByRatingUser);

        } else {
            users.sort(compareByRatingUser.reversed());
        }

        ArrayList<User> usersFinal = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i == users.size()) {
                break;
            }
            usersFinal.add(users.get(i));
        }
        return usersFinal;
    }

    public int addFavorite(String username, String movieTitle) {
        User user = users.get(username);
        return user.addFavorite(movieTitle);

    }

    public int addView(String username, String movieTitle) {
        User user = users.get(username);
        return user.addView(movieTitle);
    }
    public int addRating(String username, String movieTitle, Double value) {
        User user = users.get(username);
        return user.addRating(movieTitle, value);
    }

    public String recommendationStandard(String username, Shows showsRaw, ArrayList<String> showsTitle) {
        if (!users.containsKey(username)) {
            return "x";
        }
        User user = users.get(username);
        Map<String, Video> shows = showsRaw.getShows();

        for (String element : showsTitle) {
            if (!user.getHistory().containsKey(element)) {
                return element;
            }
        }
        return "x";
    }

    public String recommendationBestUnseen(String username, Shows showsRaw) {
        if (!users.containsKey(username)) {
            return "x";
        }
        User user = users.get(username);
        Map<String, Video> shows = showsRaw.getShows();

        String finalTitle = "";
        double maxRating = 0;

        for (Map.Entry<String, Video> element : shows.entrySet()) {
            String title = element.getKey();
            double rating = element.getValue().getRating();
            System.out.println(title + " RATING  titlu " + rating);
            System.out.println(finalTitle + " RATING  BUN " + maxRating);

            if (!user.getHistory().containsKey(title)) {
                if (finalTitle.equals("")) {
                    finalTitle = title;
                    maxRating = rating;
                }
                if (Double.compare(rating, maxRating) > 0) {
                    finalTitle = title;
                    maxRating = rating;
                }
                if (Double.compare(rating, maxRating) == 0) {
                    if (finalTitle.compareTo(title) > 0) {
                        finalTitle = title;
                        maxRating = rating;
                    }
                }
            }

        }

        if (finalTitle.equals("")) {
            return "x";
        } else {
            return finalTitle;
        }
    }

    public ArrayList<String> GenerateGenres() {
        ArrayList<String> genres = new ArrayList<>();
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Drama");
        genres.add("Comedy");
        genres.add("Crime");
        genres.add("Romance");
        genres.add("War");
        genres.add("History");
        genres.add("Thriller");
        genres.add("Mystery");
        genres.add("Family");
        genres.add("Horror");
        genres.add("Fantasy");
        genres.add("Science Fiction");
        genres.add("Action & Adventure");
        genres.add("Sci-Fi & Fantasy");
        genres.add("Animation");
        genres.add("Kids");
        genres.add("Western");
        genres.add("Tv Movie");
        return genres;
    }

    public String recommendationPopular(String username, Shows showsRaw) {
        if (!users.containsKey(username)) {
            return "x";
        }
        if (users.get(username).getSubscriptionType().equals("BASIC")) {
            return "x";
        }
        ArrayList<String> genres = GenerateGenres();
        Map<String, Video> shows = showsRaw.getShows();
        User user = users.get(username);
        String finalTitle = "";
        int maxViews = 0;
        while (true) {
            String bestGenre = "";
            int mostViews = 0;
            int position = 0;
            int i = 0;
            for (String genre : genres) {
                int views = 0;
                for (Map.Entry<String, Video> element : shows.entrySet()) {
                    if (element.getValue().getGenres().contains(genre)) {
                        views = views + element.getValue().getViewCount();
                    }
                }
                if (bestGenre.equals("")) {
                    bestGenre = genre;
                    mostViews = views;
                    position = i;
                }
                if (views == mostViews) {
                    if (bestGenre.compareTo(genre) > 0) {
                        bestGenre = genre;
                        mostViews = views;
                    }
                }
                if (views > mostViews) {
                    bestGenre = genre;
                    mostViews = views;
                    position = i;
                }
                i++;
            }

            for (Map.Entry<String, Video> element : shows.entrySet()) {
                if (!user.getHistory().containsKey(element.getValue().getTitle())) {
                    if (element.getValue().getGenres().contains(bestGenre)) {
                        if (finalTitle.equals("")) {
                            finalTitle = element.getValue().getTitle();
                            maxViews = element.getValue().getViewCount();
                        }
                        if (maxViews < element.getValue().getViewCount()) {
                            finalTitle = element.getValue().getTitle();
                            maxViews = element.getValue().getViewCount();
                        }
                    }
                }
            }
            if (finalTitle.equals("")) {
                genres.remove(position);
            } else {
                break;
            }
            if (genres.size() == 0) {
                break;
            }
        }
        if (finalTitle.equals("")) {
            return "x";
        } else {
            return finalTitle;
        }

    }

    public String recommendationFavorite(String username, Shows showsRaw){
        if (!users.containsKey(username)) {
            return "x";
        }
        if (users.get(username).getSubscriptionType().equals("BASIC")) {
            return "x";
        }
        Map<String, Video> shows = showsRaw.getShows();
        User user = users.get(username);

        String finalTitle = "";
        int maxFav = 0;

        for (Map.Entry<String, Video> element : shows.entrySet()) {
            String title = element.getKey();
            int fav = element.getValue().getFavoriteCount();
            if (!user.getHistory().containsKey(title)) {
                if (finalTitle.equals("")) {
                    finalTitle = title;
                    maxFav = fav;
                }
                if (fav > maxFav) {
                    finalTitle = title;
                    maxFav = fav;
                }
                if (fav == maxFav) {
                    if (finalTitle.compareTo(title) > 0) {
                        finalTitle = title;
                        maxFav = fav;
                    }
                }
            }
        }
        if (finalTitle.equals("")) {
            return "x";
        } else {
            return finalTitle;
        }
    }

    public ArrayList<Video> recommendationSearch(String username,String genre, Shows showsRaw) {
        ArrayList<Video> exportShows = new ArrayList<>();
        if (!users.containsKey(username)) {
            return exportShows;
        }
        if (users.get(username).getSubscriptionType().equals("BASIC")) {
            return exportShows;
        }
        Map<String, Video> shows = showsRaw.getShows();
        User user = users.get(username);

        for (Map.Entry<String, Video> element : shows.entrySet()) {
            String title = element.getKey();
            if (!user.getHistory().containsKey(title)) {
                if (element.getValue().getGenres().contains(genre)) {
                    exportShows.add(element.getValue());
                }
            }
        }

        Comparator<Video> compareByRating = (Video o1, Video o2) -> {
            if (o1.getRating() == o2.getRating()) {
                return o1.getTitle().compareTo(o2.getTitle());
            } else {
                return Double.compare(o1.getRating(), o2.getRating());
            }
        };
        exportShows.sort(compareByRating);

        return exportShows;
    }
}
