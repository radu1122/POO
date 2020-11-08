package data_set;

import actor.ActorsAwards;
import user.User;

import java.util.Map;

public class Users {
    private Map<String, User> users;

    public void addUser(String username, User user) {
        users.put(username, user);
    }

    public Map<String, User> getUsers() {
        return this.users;
    }

}
