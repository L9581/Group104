package app;

public enum UserRole {
    TA,
    MO;

    @Override
    public String toString() {
        return name();
    }
}
