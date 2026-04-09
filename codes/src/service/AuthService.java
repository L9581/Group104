package service;

import java.util.Optional;

import model.Role;
import model.User;
import storage.UserRepository;

/**
 * Handles name-based login or registration and role consistency checks.
 */
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginOrRegister(String rawName, Role role) {
        String name = rawName == null ? "" : rawName.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        Optional<User> existingUser = userRepository.findByName(name);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.getRole() != role) {
                throw new IllegalArgumentException("This name is already registered with another role.");
            }
            return user;
        }

        User newUser = new User(name, role);
        userRepository.save(newUser);
        return newUser;
    }
}
