package data_set;

import user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Users {
    private Map<String, User> users;

    public void addUser(String username, User user) {
        users.put(username, user);
    }

    public Map<String, User> getUsers() {
        return this.users;
    }

    public ArrayList<User> numRatings(int n, String sortType) {
        ArrayList<User> users = new ArrayList<User>();

        // get all the users into an arrayList
        for (Map.Entry<String, User> mapElement : this.users.entrySet()) {
            User value = mapElement.getValue();
            users.add(value);
        }

        // sort the array
        if (sortType.equals("asc")) {
            Collections.sort(users);
        } else {
            Collections.sort(users, Collections.reverseOrder());
        }

        ArrayList<User> usersFinal = new ArrayList<User>();
        for (int i = 0; i < n; i++) {
            usersFinal.add(users.get(i));
        }
        return usersFinal;
    }

    public void addFavorite(String username, String movieTitle) {
        User user = users.get(username);
        user.addFavorite(movieTitle);
    }

    public int addView(String username, String movieTitle) {
        User user = users.get(username);
        return user.addView(movieTitle);
    }
    public int addRating(String username, String movieTitle, Double value) {
        User user = users.get(username);
        return user.addRating(movieTitle, value);
    }


}
