package fr._42.spring.models;

public enum Role {
    ADMIN("Administrator"),
    USER("User"),
    MODERATOR("Moderator");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
