package storage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Role;
import model.User;

/**
 * Persists simple user records identified by name and role.
 */
public class UserRepository {
    private static final String HEADER = "name,role";

    private final Path filePath;

    public UserRepository(Path filePath) {
        this.filePath = filePath;
    }

    public void save(User user) {
        StorageSupport.ensureFileWithHeader(filePath, HEADER);
        StorageSupport.appendLine(filePath, CsvUtils.toCsvLine(user.getName(), user.getRole().name()));
    }

    public Optional<User> findByName(String name) {
        return findAll().stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    private List<User> findAll() {
        StorageSupport.ensureFileWithHeader(filePath, HEADER);
        List<User> users = new ArrayList<>();
        for (String line : StorageSupport.readDataLines(filePath)) {
            List<String> values = CsvUtils.parseLine(line);
            if (values.size() < 2) {
                continue;
            }
            users.add(new User(values.get(0), Role.valueOf(values.get(1))));
        }
        return users;
    }
}
