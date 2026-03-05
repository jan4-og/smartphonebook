package phonebook_smart;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * This class handles the Menu-Driven Interface, user input orchestration,
 * and coordinates the data flow between the UI and the SmartPhonebook engine.
 */
public class Main {
    private static Scanner input = new Scanner(System.in);
    private static SmartPhonebook book = new SmartPhonebook();

    /**
     * Main execution loop. Implements a while-loop and switch-branching 
     * to manage the application lifecycle.
     */
    public static void main(String[] args) {
        Preload.seed(book); // Initializing system with mock data
        boolean running = true;
        
        while (running) {
            System.out.println("\n⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊");
            System.out.println("     SMART PHONEBOOK & INTERACTION LOGGER     ");
            System.out.println("⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊⚊");
            System.out.println("[1] Open Global Directory ⧉");
            System.out.println("[2] Quick Email Lookup ⚡");
            System.out.println("[3] Attribute-Based Smart Search ⌕");
            System.out.println("[4] Interaction History Manager 🕒");
            System.out.println("[5] Register New Contact ⎘");
            System.out.println("[6] Modify Existing Records ✎");
            System.out.println("[7] Remove Contact from System ⌦");
            System.out.println("[8] Secure Exit ⍈");
            System.out.print("\n[➤] Select an operation: ");
            
            String choice = input.nextLine().trim();
            switch (choice) {
                case "1" -> displayAll(book.getSortedByLastName());
                case "2" -> lookupMenu();
                case "3" -> smartSearchMenu();
                case "4" -> loggerMenu();
                case "5" -> addContactFlow();
                case "6" -> updateContactFlow();
                case "7" -> deleteContactFlow();
                case "8" -> {
                    System.out.println("\n[⌛] Saving system state...");
                    System.out.println("Goodbye! ₍ᐢ› ̫ ‹ᐢ₎");
                    running = false;
                }
                default -> System.out.println("[✕] Invalid choice. Please enter 1-8.");
            }
        }
    }

    /**
     * Facilitates the creation of a new Contact object.
     * Demonstrates rigorous input validation using custom utility checks.
     */
    private static void addContactFlow() {
        System.out.println("\n--- [⎘] NEW CONTACT REGISTRATION ---");
        String f = getValidInput("First Name: ", "[✕] First Name is required.");
        String m = getValidInput("Middle Name: ", "[✕] Middle Name is required.");
        String l = getValidInput("Last Name: ", "[✕] Last Name is required.");
        
        String email;
        while (true) {
            System.out.print("Email Address: ");
            email = input.nextLine();
            if (Utilities.isValidEmail(email)) break;
            System.out.println("[✕] Invalid format (example@domain.com).");
        }

        String phone;
        while (true) {
            System.out.print("Contact Number: ");
            phone = input.nextLine();
            if (Utilities.isValidPhone(phone)) break;
            System.out.println("[✕] Invalid number (Must be 7-15 digits).");
        }

        String loc = getValidInput("Current Location: ", "[✕] Location is required.");
        String cat = getValidInput("Contact Category: ", "[✕] Category is required.");

        LocalDate bday = null;
        while (bday == null) {
            System.out.print("Birthday (YYYY-MM-DD): ");
            try { bday = LocalDate.parse(input.nextLine()); } 
            catch (DateTimeParseException e) { System.out.println("[✕] Use format: YYYY-MM-DD."); }
        }

        book.addContact(new Contact(f, m, l, email, phone, loc, cat, bday));
        System.out.println("[✔] Contact synchronized successfully.");
    }

    /**
     * Handles the 'Modify'
     * Updating the object in-place, reflecting changes across all structures.
     */
    private static void updateContactFlow() {
        System.out.print("\n[✎] Enter Contact ID to modify (e.g., CID-1001): ");
        Contact c = book.getContactByID(input.nextLine().trim());
        if (c == null) {
            System.out.println("[✕] Record not found in system.");
            return;
        }

        System.out.println("\nModifying Record: " + c.getFullName());
        System.out.println("(Leave blank to keep existing data)");

        System.out.print("First Name [" + c.getFirstName() + "]: ");
        String f = input.nextLine();
        if (!f.isEmpty()) c.setFirstName(f);

        System.out.print("Middle Name [" + c.getMiddleName() + "]: ");
        String m = input.nextLine();
        if (!m.isEmpty()) c.setMiddleName(m);

        System.out.print("Last Name [" + c.getLastName() + "]: ");
        String l = input.nextLine();
        if (!l.isEmpty()) c.setLastName(l);

        System.out.print("Email [" + c.getEmail() + "]: ");
        String email = input.nextLine();
        if (!email.isEmpty() && Utilities.isValidEmail(email)) c.setEmail(email);

        System.out.print("Phone [" + c.getContactNumber() + "]: ");
        String p = input.nextLine();
        if (!p.isEmpty() && Utilities.isValidPhone(p)) c.setContactNumber(p);

        System.out.print("Location [" + c.getLocation() + "]: ");
        String loc = input.nextLine();
        if (!loc.isEmpty()) c.setLocation(loc);

        System.out.print("Category [" + c.getCategory() + "]: ");
        String cat = input.nextLine();
        if (!cat.isEmpty()) c.setCategory(cat);

        System.out.print("Birthday [" + c.getBirthday() + "]: ");
        String bdayStr = input.nextLine();
        if (!bdayStr.isEmpty()) {
            try { c.setBirthday(LocalDate.parse(bdayStr)); } 
            catch (DateTimeParseException e) { System.out.println("[✕] Invalid format. Date unchanged."); }
        }

        c.recordActivity("Record manually updated by admin.");
        System.out.println("[✔] Updates applied and synchronized.");
    }

    /**
     * Menu for the 'Smart Search' feature using nested algorithmic matching.
     */
    private static void smartSearchMenu() {
        System.out.println("\n--- [⌕] SMART SEARCH ENGINE ---");
        System.out.println("[1] Full Name Search");
        System.out.println("[2] Geographic Search (Location)");
        System.out.println("[3] Category Filtering");
        System.out.println("[4] Birth Month Search (Text or Number)");
        System.out.print("Search criteria: ");
        
        String type = switch(input.nextLine().trim()) {
            case "1" -> "name";
            case "2" -> "location";
            case "3" -> "category";
            case "4" -> "month";
            default -> null;
        };

        if (type == null) {
            System.out.println("[✕] Selection cancelled.");
            return;
        }

        System.out.print("Enter keywords: ");
        displayAll(book.searchByAttribute(input.nextLine().trim(), type));
    }

    /**
     * Removal logic that ensures synchronization accuracy by deleting 
     * from both the ArrayList and HashMap.
     */
    private static void deleteContactFlow() {
        System.out.print("\n[⌦] Enter Contact ID to remove: ");
        String id = input.nextLine().trim();
        if (book.deleteByID(id)) {
            System.out.println("[✔] Contact [" + id + "] purged from system.");
        } else {
            System.out.println("[✕] ID not found.");
        }
    }

    /**
     * Interface for the O(1) HashMap lookup.
     */
    private static void lookupMenu() {
        System.out.print("\n[⚡] Enter Email for instant lookup: ");
        Contact found = book.getContactByEmail(input.nextLine().trim());
        if (found != null) {
            displayHeader();
            System.out.println(found);
        } else System.out.println("[✕] No record matching that email.");
    }

    /**
     * Interface for viewing and managing the LinkedList-based activity logs.
     */
    private static void loggerMenu() {
        System.out.print("\n[🕒] Enter Email to access logs: ");
        Contact c = book.getContactByEmail(input.nextLine().trim());
        if (c != null) {
            System.out.println("[1] View Rolling History | [2] Log New Activity");
            String choice = input.nextLine().trim();
            if (choice.equals("1")) {
                System.out.println("\nInteraction Logs for " + c.getFullName() + ":");
                for (String log : c.getActivityLogs()) System.out.println(log);
            } else {
                System.out.print("Note: ");
                c.recordActivity(input.nextLine().trim());
                System.out.println("[✔] Activity logged.");
            }
        } else System.out.println("[✕] Contact not found.");
    }

    private static String getValidInput(String p, String e) {
        while (true) {
            System.out.print(p);
            String r = input.nextLine().trim();
            if (Utilities.isNotEmpty(r)) return r;
            System.out.println(e);
        }
    }

    private static void displayHeader() {
        System.out.println("\n" + "=".repeat(135));
        System.out.printf("%-10s %-35s %-25s %-15s %-20s %-12s %-10s\n",
                "ID", "Full Name (Last, First Middle)", "Email", "Phone", "Location", "Category", "Birthday");
        System.out.println("-".repeat(135));
    }

    private static void displayAll(List<Contact> list) {
        if (list.isEmpty()) {
            System.out.println("[ℹ] No results to display.");
            return;
        }
        displayHeader();
        for (Contact c : list) System.out.println(c);
        System.out.println("=".repeat(135));
    }
}