package storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Small CSV helper for escaping values and parsing simple quoted rows.
 */
public final class CsvUtils {
    private CsvUtils() {
    }

    public static String toCsvLine(String... values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(',');
            }
            builder.append(escape(values[i]));
        }
        return builder.toString();
    }

    public static List<String> parseLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            if (currentChar == '"') {
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    currentValue.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (currentChar == ',' && !insideQuotes) {
                values.add(currentValue.toString());
                currentValue.setLength(0);
            } else {
                currentValue.append(currentChar);
            }
        }

        values.add(currentValue.toString());
        return values;
    }

    private static String escape(String rawValue) {
        String safeValue = rawValue == null ? "" : rawValue;
        String escapedValue = safeValue.replace("\"", "\"\"");
        return "\"" + escapedValue + "\"";
    }
}
