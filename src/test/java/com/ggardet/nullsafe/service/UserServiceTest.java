package com.ggardet.nullsafe.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test class demonstrating NPE scenarios that are excluded from NullAway analysis.
 */
@SpringBootTest
class UserServiceTest {
    private final UserService userService = new UserService();

    @Test
    void testDirectNullPointerDereference() {
        assertThrows(NullPointerException.class, () -> {
            final var user = userService.findById("");
            final var username = user.name(); // NPE will occur here
            System.out.println(username); // This line will not be reached
        });
    }
}
