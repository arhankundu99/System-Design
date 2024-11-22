package services;
import java.util.HashMap;
import java.util.Map;
import models.User;
public class UserService {
    private Map<String, User> userMap;

    public User getUserById(String id) {
        if (userMap.containsKey(id)) {
            return userMap.get(id);
        } else {
            throw NotFoundException("User is not found for the given id");
        }
    }

    public User addUser(String name, String email) {
        User user = new User(name, email);
        userMap.put(user.getId(), user);
        return user;
    }
}