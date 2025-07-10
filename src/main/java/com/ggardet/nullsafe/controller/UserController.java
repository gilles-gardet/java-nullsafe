package com.ggardet.nullsafe.controller;

import com.ggardet.nullsafe.model.User;
import com.ggardet.nullsafe.service.UserService;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final @NonNull UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves all users.
     */
    @GetMapping
    public @NonNull ResponseEntity<List<User>> getAllUsers() {
        final var users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user by their ID.
     */
    @GetMapping("/{id}")
    public @NonNull ResponseEntity<User> getUserById(@PathVariable @NonNull String id) {
        final var user = userService.findById(id);
        if (Objects.isNull(user)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Creates a new user.
     */
    @PostMapping
    public @NonNull ResponseEntity<User> createUser(final @RequestBody @NonNull User user) {
        final var createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Endpoint with an intentional bug to demonstrate NullAway.
     * This method will trigger a compilation error.
     */
    @GetMapping("/{id}/email-uppercase")
    public @NonNull ResponseEntity<String> getUserEmailUppercase(final @PathVariable @NonNull String id) {
        final var emailUppercase = userService.getUserEmailUppercase(id); // expected compilation error
        return ResponseEntity.ok(emailUppercase);
    }

    /**
     * Corrected version of the previous endpoint.
     */
    @GetMapping("/{id}/email-uppercase-safe")
    public @NonNull ResponseEntity<String> getUserEmailUppercaseSafe(final @PathVariable @NonNull String id) {
        final var emailUppercase = userService.getUserEmailUppercaseSafe(id);
        return ResponseEntity.ok(emailUppercase);
    }

    /**
     * Method with another example of NullAway bug.
     * Demonstrates a direct dereferencing problem without null checking.
     */
    @GetMapping("/{id}/info")
    public @NonNull ResponseEntity<String> getUserInfo(final @PathVariable @Nullable String id) {
        final var info = String.format("User: %s", id.toUpperCase()); // expected compilation error
        return ResponseEntity.ok(info);
    }

    /**
     * Corrected version of getUserInfo.
     */
    @GetMapping("/{id}/info-safe")
    public @NonNull ResponseEntity<String> getUserInfoSafe(@PathVariable @Nullable String id) {
        if (Objects.isNull(id)) {
            return ResponseEntity.badRequest().build();
        }
        final var info = String.format("User: %s", id.toUpperCase());
        return ResponseEntity.ok(info);
    }
}
