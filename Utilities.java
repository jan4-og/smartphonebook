package phonebook_smart;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * This is the class that provides the utility methods for data validation and formatting.
 * It ensures that all data entering the system is standardized and valid.
 */
public class Utilities {
    // Regex pattern to ensure emails follow standard conventions (user@domain.ext)
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]+$";
    
    // Formatter for interaction logs to ensure consistent timestamping
    private static final DateTimeFormatter LOG_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Standardizes text input by capitalizing each word.
     * If the input is already all caps (e.g., 'QC'), 
     * it remains unchanged to preserve official abbreviations.
     * @param input - The raw string to be formatted.
     * @return - The properly capitalized or preserved acronym string.
     */
    public static String smartFormat(String input) {
        if (input == null || input.isEmpty()) return input;
        
        // Acronym check: Skip formatting if the word is already intended as an acronym
        if (input.equals(input.toUpperCase()) && input.length() > 1) return input;

        String[] words = input.toLowerCase().split("\\s+");
        StringBuilder formatted = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                formatted.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1)).append(" ");
            }
        }
        return formatted.toString().trim();
    }

    /**
     * Converts various month formats (Name, Abbreviation, or Number) into an integer.
     * @param input - User input (e.g., 'January', 'Feb', or '3').
     * @return - The integer value of the month (1-12), or -1 if the input is unrecognizable.
     */
    public static int getMonthNumber(String input) {
        if (input == null || input.isEmpty()) return -1;
        
        // Logical Branch 1: Try parsing as a raw number (1-12)
        try {
            int m = Integer.parseInt(input.trim());
            if (m >= 1 && m <= 12) return m;
        } catch (NumberFormatException e) {
            // Logical Branch 2: Match against Month enum names if input is text
            String search = input.trim().toUpperCase();
            for (Month m : Month.values()) {
                if (m.name().startsWith(search)) {
                    return m.getValue();
                }
            }
        }
        return -1;
    }

    /**
     * Validates an email string using Regular Expressions (Regex).
     * @param email - The email to check.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_REGEX, email);
    }

    /**
     * Validates a phone number based on length and numeric content.
     * @param phone - The numeric string to check.
     * @return true if numeric and between 7-15 characters.
     */
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d+") && phone.length() >= 7 && phone.length() <= 15;
    }

    /**
     * Simple check to prevent empty fields from being synchronized.
     * @param input - The string to check.
     * @return true if the string contains actual text.
     */
    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Generates a current timestamp for the Interaction Logger.
     * @return A formatted date-time string.
     */
    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(LOG_FORMAT);
    }
}