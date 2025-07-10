package com.ggardet.nullsafe.model;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * User's model
 */
public record User(@NonNull String id, @NonNull String name, @Nullable String email, @NonNull String department) {

    @Override
    public @NonNull String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
