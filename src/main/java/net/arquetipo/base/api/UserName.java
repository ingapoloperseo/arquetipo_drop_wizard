package net.arquetipo.base.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.constraints.NotEmpty;

/**
 * Utility class for request: {@link UserName} instead of {@link String}
 */
public class UserName {
    @NotEmpty
    private final String value;

    private UserName(@NotEmpty String value) {
        this.value = value;
    }

    @JsonValue
    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof UserName && this.value.equals(((UserName) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public static UserName valueOf(String value) {
        return of(value);
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static UserName of(@NotEmpty String value) {
        return new UserName(value);
    }
}
