package phonebook_smart;

import java.util.*;

/**
 * This the core class for the Smart Phonebook.
 * This class implements Data Synergy by maintaining two parallel data structures:
 * 1. HashMap: Optimized for O(1) instant lookups using Email as the key.
 * 2. ArrayList: Optimized for ordered storage and alphabetical sorting.
 */
public class SmartPhonebook {
    // Stores contacts for fast retrieval by email
    private Map<String, Contact> emailMap; 
    
    // Stores contacts and sorting operations
    private List<Contact> contactList;
    
    /**
     * Initializes the data structures. 
     * Both structures will point to the same Contact object references in memory.
     */
    public SmartPhonebook() {
        this.emailMap = new HashMap<>();
        this.contactList = new ArrayList<>();
    }
    
    /**
     * Synchronizes a new contact across both data structures.
     * @param contact The contact object to be added.
     */
    public void addContact(Contact contact) {
        if (contact == null) return;
        emailMap.put(contact.getEmail().toLowerCase(), contact);
        contactList.add(contact);
    }
    
    /**
     * Removes a contact from both structures using their unique ID.
     * Demonstrates synchronization accuracy during data removal.
     * @param id The unique CID (Contact ID) of the target.
     * @return true if the contact was successfully found and removed.
     */
    public boolean deleteByID(String id) {
        Contact toRemove = null;
        // Search through the list to find the matching reference
        for (Contact c : contactList) {
            if (c.getContactID().equalsIgnoreCase(id)) {
                toRemove = c;
                break;
            }
        }
        
        // Remove from both to maintain structural integrity
        if (toRemove != null) {
            contactList.remove(toRemove);
            emailMap.remove(toRemove.getEmail());
            return true;
        }
        return false;
    }

    /**
     * Used to find a contact reference by their ID.
     * @param id The CID to search for.
     * @return The Contact object reference if found, otherwise null.
     */
    public Contact getContactByID(String id) {
        for (Contact c : contactList) {
            if (c.getContactID().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    /**
     * Implements the O(1) Fast Lookup requirement using the HashMap.
     * @param email The unique email address key.
     * @return The matching Contact object.
     */
    public Contact getContactByEmail(String email) {
        return emailMap.get(email.toLowerCase());
    }

    /**
     * SMART SEARCH:
     * Utilizes nested algorithmic logic to filter contacts based on specific attributes.
     * Implements branching to handle different data types like Name, Location, and Date.
     * * @param value - The search term entered by the user.
     * @param type - The category of search (name, location, category, or month).
     * @return A list of contacts that satisfy the search criteria.
     */
    public List<Contact> searchByAttribute(String value, String type) {
        List<Contact> results = new ArrayList<>();
        String searchVal = value.toLowerCase().trim();

        for (Contact c : contactList) {
            boolean match = false;
            switch (type.toLowerCase()) {
                case "location" -> match = c.getLocation().toLowerCase().contains(searchVal);
                case "category" -> match = c.getCategory().toLowerCase().equalsIgnoreCase(searchVal);
                case "name" -> {
                    // Multi-field name matching
                    match = c.getFirstName().toLowerCase().contains(searchVal) ||
                            c.getMiddleName().toLowerCase().contains(searchVal) ||
                            c.getLastName().toLowerCase().contains(searchVal);
                }
                case "month" -> {
                    // Leverages Utilities to translate user text into numerical months
                    int monthNum = Utilities.getMonthNumber(searchVal);
                    match = (c.getBirthday().getMonthValue() == monthNum);
                }
            }
            if (match) results.add(c);
        }
        return results;
    }

    /**
     * Creates a shallow copy of the list and sorts it by Last Name.
     * @return A sorted list of all contacts.
     */
    public List<Contact> getSortedByLastName() {
        List<Contact> sorted = new ArrayList<>(contactList);
        sorted.sort(Comparator.comparing(Contact::getLastName));
        return sorted;
    }

    public List<Contact> getAllContacts() { return contactList; }
}