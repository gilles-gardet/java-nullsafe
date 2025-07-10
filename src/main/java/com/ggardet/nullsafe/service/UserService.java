package com.ggardet.nullsafe.service;

import com.ggardet.nullsafe.model.User;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * User management service.
 */
@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    /**
     * Constructor to initialize some sample users.
     * This simulates a database or external data source.
     */
    public UserService() {
        users.put("1", new User("1", "Alice Smith", "alice@example.com", "IT"));
        users.put("2", new User("2", "Bob Johnson", null, "HR")); // Null email for testing
        users.put("3", new User("3", "Charlie Brown", "charlie@example.com", "Finance"));
    }

    /**
     * Finds a user by their ID.
     *
     * @param id the user ID (cannot be null)
     * @return the found user or null if not found
     */
    public @Nullable User findById(@NonNull String id) {
        return users.get(id);
    }

    /**
     * Returns all users.
     *
     * @return a non-null list of users
     */
    public @NonNull List<User> findAll() {
        return List.copyOf(users.values());
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create (cannot be null)
     * @return the created user
     */
    public @NonNull User createUser(@NonNull User user) {
        users.put(user.id(), user);
        return user;
    }

    /**
     * Method with an intentional bug to demonstrate NullAway.
     * This method will trigger a compilation error with NullAway
     * because it attempts to call a method on a potentially null object.
     */
    public @NonNull String getUserEmailUppercase(@NonNull String userId) {
        final var user = findById(userId); // can return null
        return user.email().toUpperCase(); // expected compilation error
    }

    /**
     * Corrected version of the previous method.
     */
    public @NonNull String getUserEmailUppercaseSafe(@NonNull String userId) {
        final var user = findById(userId);
        if (Objects.isNull(user)) {
            return "USER_NOT_FOUND";
        }
        final var email = user.email();
        if (Objects.isNull(email)) {
            return "EMAIL_NOT_PROVIDED";
        }
        return email.toUpperCase();
    }
}
