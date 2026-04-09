package model;

/**
 * Represents a simple user identified only by name and role.
 */
public class User {
    private final String name;
    private final Role role;

    public User(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
}
