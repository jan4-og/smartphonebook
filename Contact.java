package phonebook_smart;

import java.util.LinkedList;
import java.time.LocalDate;

/**
 * This class is the blueprint for Contact objects in the Smart Phonebook.
 * It encapsulates contact details and manages an interaction history.
 */
public class Contact {
	// Static counter to ensure all contact gets a unique id(auto-incremented).
    private static int idCounter = 1001;
    
    private String contactID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String location;
    private String category;
    private LocalDate birthday;
    
    /**
     * LinkedList used for activity log management (chronological ordered by time).
     * This allows 0(1) insertions and removals at the beginning/end.
     */
    private LinkedList<String> activityLogs;

    /**
     * This constructor initializes a contact with smart formatting applied to names and locations.
     * @param firstName - Contact's given name
     * @param middleName - Contacts' middle name
     * @param lastName - Contacts' last name
     * @param email - Contact's email as a unique identifies used as a key in the HashMap
     * @param contactNumber - Numeric phone string of the contact
     * @param location - Contact's Location (City or Province)
     * @param category - Relationship tag (e.g., Work, Family)
     * @param birthday - Contact's date of birth
     */
    public Contact(String firstName, String middleName, String lastName, String email, 
                   String contactNumber, String location, String category, LocalDate birthday) {
        this.contactID = "CID-" + (idCounter++);
        
        // Smart Formatting via Utilities
        // Data Sanitization: Ensures uniform casing regardless of user input style
        this.firstName = Utilities.smartFormat(firstName);
        this.middleName = Utilities.smartFormat(middleName);
        this.lastName = Utilities.smartFormat(lastName);
        this.location = Utilities.smartFormat(location);
        this.category = Utilities.smartFormat(category);
        
        this.email = email.toLowerCase();
        this.contactNumber = contactNumber;
        this.birthday = birthday;
        
        this.activityLogs = new LinkedList<>();
    }

    /**
     * Records a new interaction in the contact's history.
     * If the history exceeds 5 entries, 
     * the oldest entry is removed to save memory.
     * @param note Description of the interaction
     */
    public void recordActivity(String note) {
        String logEntry = "[" + Utilities.getCurrentTimestamp() + "] " + note;
        if (activityLogs.size() >= 5) {
            activityLogs.removeFirst();
        }
        activityLogs.add(logEntry);
    }

    // --- Getters and Setters ---
    // Note: Setters include smartFormat to maintain data integrity during updates.
    
    // CONTACT ID
    public String getContactID() {
    	return contactID;
    }
    
    // FIRST NAME
    public String getFirstName() {
    	return firstName;
    }
    public void setFirstName(String firstName) {
    	this.firstName = Utilities.smartFormat(firstName);
    }
    
    // MIDDLE NAME
    public String getMiddleName() {
    	return middleName;
    }
    public void setMiddleName(String middleName) {
    	this.middleName = Utilities.smartFormat(middleName);
    }
    
    // LAST NAME
    public String getLastName() {
    	return lastName;
    }
    public void setLastName(String lastName) {
    	this.lastName = Utilities.smartFormat(lastName);
    }
    
    // EMAIL
    public String getEmail() {
    	return email;
    }
    public void setEmail(String email) {
    	this.email = email.toLowerCase();
    }
    
    // CONTACT NUMBER
    public String getContactNumber() {
    	return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
    	this.contactNumber = contactNumber;
    }
    
    // LOCATION
    public String getLocation() {
    	return location;
    }
    public void setLocation(String location) {
    	this.location = Utilities.smartFormat(location);
    }
    
    // CATEGORY
    public String getCategory() {
    	return category;
    }
    public void setCategory(String category) {
    	this.category = Utilities.smartFormat(category);
    }
    
    // BIRTHDAY
    public LocalDate getBirthday() {
    	return birthday;
    }
    public void setBirthday(LocalDate birthday) {
    	this.birthday = birthday;
    }
    
    // ACTIVITY LOGS
    public LinkedList<String> getActivityLogs() {
    	return activityLogs;
    }

    /**
     * Formats the name
     * @return - Returns the full name string in this format: LastName, FirstName, MiddleName
     */
    public String getFullName() {
        return String.format("%s, %s %s", lastName, firstName, middleName);
    }

    /**
     * Overrides the toString method to generate a clean table for the contact list.
     * Uses fixed-width padding for terminal alignment.
     */
    @Override
    public String toString() {
        return String.format("%-10s %-35s %-25s %-15s %-20s %-12s %-10s",
                contactID, getFullName(), email, contactNumber, location, category, birthday);
    }
}