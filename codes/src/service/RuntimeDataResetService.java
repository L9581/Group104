package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Clears runtime CSV data under the data directory while preserving folders and non-CSV files.
 */
public class RuntimeDataResetService {
    private final Path dataDirectory;

    public RuntimeDataResetService(Path dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public void resetRuntimeData() {
        try {
            Files.createDirectories(dataDirectory);
            try (Stream<Path> paths = Files.walk(dataDirectory)) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".csv"))
                        .sorted(Comparator.reverseOrder())
                        .forEach(this::deleteFile);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to reset runtime data.", exception);
        }
    }

    private void deleteFile(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to delete file: " + filePath, exception);
        }
    }
}
