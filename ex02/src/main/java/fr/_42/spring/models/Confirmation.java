package fr._42.spring.models;

public enum Confirmation {
    CONFIRMED("confirmed"),
    NOT_COMFIRMED("not_confirmed");

    private final String displayName;

    Confirmation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
