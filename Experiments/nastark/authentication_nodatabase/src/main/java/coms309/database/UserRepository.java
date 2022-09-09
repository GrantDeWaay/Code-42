package coms309.database;

import java.util.HashMap;
import java.util.Optional;

public class UserRepository {

    private HashMap<String, User> database;

    public UserRepository() {
        database = new HashMap<>();

        User user = new User("nathan", "5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8", 155997894);

        database.put(user.getUsername(), user);
    }

    public Optional<User> findById(String id) {
        if(!database.containsKey(id)) return Optional.empty();

        return Optional.of(database.get(id));
    }
    
}
