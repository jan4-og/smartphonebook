package phonebook_smart;

import java.time.LocalDate;

/**
 * Database simulation class.
 * This class handles the initial population of the SmartPhonebook 
 * to ensure the system is functional upon startup.
 */
public class Preload {
    
    /**
     * Seeds the SmartPhonebook with 12 contact records.
     * Each contact is assigned a unique birth month and Philippine-based details.
     * * @param manager - The SmartPhonebook instance to be populated.
     */
    public static void seed(SmartPhonebook manager) {
        Contact c1 = new Contact("Mickey", "Mouse", "Daga", "mickey@mouse.com",
                "09171234567", "Apalit, Pampanga", "Family", LocalDate.of(1928, 1, 18));
        
        Contact c2 = new Contact("Minnie", "Mouse", "Bubwit", "minnie@mouse.com",
                "09182345678", "Quezon City", "Family", LocalDate.of(1928, 2, 14));
        
        Contact c3 = new Contact("Donald", "Fauntleroy", "Duck", "donald@duck.com",
                "09193456789", "Davao City", "Work", LocalDate.of(1934, 3, 9));
        
        Contact c4 = new Contact("Daisy", "Duck", "Anducks", "daisy@duck.com",
                "09204567890", "Cebu City", "Friend", LocalDate.of(1940, 4, 7));
        
        Contact c5 = new Contact("Goofy", "Goof", "Aso", "goofy@disney.com",
                "09215678901", "Baguio City", "Personal", LocalDate.of(1932, 5, 25));

        Contact c6 = new Contact("Lilo", "Pelekai", "Stitch", "lilo@ohana.com",
                "09226789012", "Palawan", "Family", LocalDate.of(2002, 6, 16));

        Contact c7 = new Contact("Ariel", "Triton", "Sirena", "ariel@ocean.com",
                "09237890123", "Boracay, Aklan", "Friend", LocalDate.of(1989, 7, 21));

        Contact c8 = new Contact("Simba", "Mufasa", "Hakuna", "simba@pride.com",
                "09248901234", "Zamboanga", "Personal", LocalDate.of(1994, 8, 15));

        Contact c9 = new Contact("Belle", "Maurice", "Cow", "belle@beast.com",
                "09259012345", "Vigan, Ilocos Sur", "Work", LocalDate.of(1991, 9, 29));

        Contact c10 = new Contact("Fa", "Mulan", "Han", "mulan@honor.com",
                "09260123456", "Manila", "Work", LocalDate.of(1998, 10, 5));

        Contact c11 = new Contact("Moana", "Waialiki", "Havana", "moana@motunui.com",
                "09271234567", "Siargao", "Friend", LocalDate.of(2016, 11, 23));

        Contact c12 = new Contact("Elsa", "Iduna", "Manaloto", "elsa@arendelle.com",
                "09282345678", "Tagaytay", "Personal", LocalDate.of(2013, 12, 22));

        // Using an array to facilitate an iterative synchronization process
        Contact[] allContacts = {c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12};
        
        for (Contact c : allContacts) {
            /**
             * Ensures the HashMap and ArrayList point to the exact same object in memory.
             */
            manager.addContact(c);
            
            /**
             * This populates the LinkedList within each Contact object with 
             * a starting history, fulfilling the sequential log requirement.
             */
            seedLogs(c);
        }

        // Final UI feedback to confirm system initialization
        System.out.println("[ℹ]" + allContacts.length + " contacts synchronized.");
    }

    /**
	 * Gives every pre-loaded contact an activity history.
     * * @param c - The contact whose history is being initialized.
     */
    private static void seedLogs(Contact c) {
        c.recordActivity("Initial system synchronization.");
        c.recordActivity("Contact information verified.");
        c.recordActivity("Added to global directory.");
        c.recordActivity("Set as " + c.getCategory() + " contact.");
        c.recordActivity("Interaction history initialized.");
    }
}