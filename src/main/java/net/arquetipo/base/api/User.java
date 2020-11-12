package net.arquetipo.base.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Entidad User, inmutable + builder
 */
@JsonDeserialize(builder = User.Builder.class)
public final class User {
    private final String name;

    @Pattern(regexp=".+@.+\\.[a-z]+")
    private final String email;

    private final LocalDateTime created;

    private final UserType type;

    private int memoizedHashCode;

    private User(String name, String email, LocalDateTime created, UserType type) {
        validateFields(name, email, created, type);
        this.name = name;
        this.email = email;
        this.created = created;
        this.type = type;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    @JsonProperty("email")
    public String getEmail() {
        return this.email;
    }

    @JsonProperty("created")
    public LocalDateTime getCreated() {
        return this.created;
    }

    @JsonProperty("type")
    public UserType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof User && equalTo((User) other));
    }

    private boolean equalTo(User other) {
        return this.name.equals(other.name)
                && this.email.equals(other.email)
                && this.created.isEqual(other.created)
                && this.type.equals(other.type);
    }

    @Override
    public int hashCode() {
        int result = memoizedHashCode;
        if (result == 0) {
            result = Objects.hash(this.name, this.email, this.created.toInstant(OffsetDateTime.now().getOffset()), this.type);
            memoizedHashCode = result;
        }
        return result;
    }

    @Override
    public String toString() {
        return "User{name: " + name + ", email: " + email + ", created: " + created + ", type: " + type + '}';
    }

    private static void validateFields(String name, String email, LocalDateTime created, UserType type) {
        List<String> missingFields = null;
        missingFields = addFieldIfMissing(missingFields, name, "name");
        missingFields = addFieldIfMissing(missingFields, email, "email");
        missingFields = addFieldIfMissing(missingFields, created, "created");
        missingFields = addFieldIfMissing(missingFields, type, "type");
        if (missingFields != null) {
            throw new IllegalArgumentException(
                    "Some required fields have not been set: " + missingFields.stream().collect(Collectors.joining(",")));
        }
    }

    private static List<String> addFieldIfMissing(List<String> prev, Object fieldValue, String fieldName) {
        List<String> missingFields = prev;
        if (fieldValue == null) {
            if (missingFields == null) {
                missingFields = new ArrayList<>(4);
            }
            missingFields.add(fieldName);
        }
        return missingFields;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static final class Builder {
        private String name;

        private String email;

        private LocalDateTime created;

        private UserType type;

        private Builder() {
        }

        public Builder from(User other) {
            name(other.getName());
            email(other.getEmail());
            created(other.getCreated());
            type(other.getType());
            return this;
        }

        @JsonSetter("name")
        public Builder name(@NotEmpty String name) {
            this.name = name;
            return this;
        }

        @JsonSetter("email")
        public Builder email(@NotEmpty String email) {
            this.email = email;
            return this;
        }

        @JsonSetter("created")
        public Builder created(@NotEmpty LocalDateTime created) {
            this.created = created;
            return this;
        }

        @JsonSetter("type")
        public Builder type(@NotEmpty UserType type) {
            this.type = type;
            return this;
        }

        public User build() {
            return new User(name, email, created, type);
        }
    }
}
