package storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

/**
 * Shared file helpers used by the repositories.
 */
public final class StorageSupport {
    private StorageSupport() {
    }

    public static void ensureFileWithHeader(Path filePath, String header) {
        ensureParentDirectory(filePath);
        if (Files.exists(filePath)) {
            return;
        }

        writeAllLines(filePath, Collections.singletonList(header));
    }

    public static List<String> readDataLines(Path filePath) {
        ensureParentDirectory(filePath);
        if (!Files.exists(filePath)) {
            return Collections.emptyList();
        }

        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            if (lines.size() <= 1) {
                return Collections.emptyList();
            }
            return lines.subList(1, lines.size());
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read file: " + filePath, exception);
        }
    }

    public static void appendLine(Path filePath, String line) {
        ensureParentDirectory(filePath);
        try {
            byte[] content = (line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
            Files.write(
                    filePath,
                    content,
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to write file: " + filePath, exception);
        }
    }

    public static void writeAllLines(Path filePath, List<String> lines) {
        ensureParentDirectory(filePath);
        try {
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to write file: " + filePath, exception);
        }
    }

    private static void ensureParentDirectory(Path filePath) {
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to create directory: " + filePath.getParent(), exception);
        }
    }
}
