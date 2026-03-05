# Smart Directory & Interaction Logger (Terminal-Based)

## Project Overview
The **Smart Directory & Interaction Logger** is a high-performance terminal-based Java application designed to manage contact information while maintaining a rolling chronological history of interactions. 

Unlike traditional flat-file contact lists, this system demonstrates **Data Structure Synergy**. By leveraging multiple collections simultaneously, it achieves $O(1)$ search speeds for specific lookups while maintaining the flexibility of a sorted global directory.



---

## Tech Stack
* **Language:** Java (JDK 11+)
* **Architecture:** Reference-based Synchronization (Single Source of Truth)
* **Data Structures:**
    * **HashMap:** Used for instant $O(1)$ email-to-contact retrieval.
    * **ArrayList:** Used for dynamic storage and $O(n \log n)$ alphabetical sorting.
    * **LinkedList:** Used within each contact to manage a "Rolling 5" interaction history.

---

## The Sync Mechanism
The core of this project is the **Sync Mechanism**. When a contact is registered, the system does not create multiple copies. Instead, it creates one `Contact` object in memory and distributes **references** to it across different structures.

* **HashMap Indexing:** Uses the unique Email as a key for lightning-fast lookups.
* **ArrayList Directory:** Stores the same reference for structural browsing and sorting.
* **Shared State:** Because both structures point to the same object, any update made to a contact via the Search menu (HashMap) is instantly visible when viewing the Global Directory (ArrayList).

---

## Key Features

1. **Global Directory:** A formatted table view of all contacts, automatically sorted by Last Name for professional organization.
2. **Quick Email Lookup:** Leverages HashMap logic to find a contact instantly without iterating through the entire list.
3. **Smart Search Engine:** A multi-layered search tool that filters by Name, Location, Category, or Birth Month (supporting both names like "January" and numbers like "01").
4. **Interaction History Manager:** A rolling log system that keeps the 5 most recent activities for every contact, automatically purging the oldest entries to manage memory.
5. **Data Sanitization:** Automatically formats names and locations (e.g., "pampanga" becomes "Pampanga") while preserving acronyms (e.g., "QC").

---

## How to Run (Eclipse IDE)

1. **Open Eclipse:** Ensure you have the Java Development Tools (JDT) installed.
2. **Create Project:** Go to `File > New > Java Project`. Name it `SmartPhonebook`.
3. **Import Files:** Drag and drop the `phonebook_smart` package folder into the `src` directory of your new project.
4. **Locate Main:** Expand the package and right-click on `Main.java`.
5. **Execute:** Select `Run As > Java Application`.
6. **Interact:** Use the console window to navigate the menu using numbers 1-8.

---

## Sample Input/Output

### [1] Open Global Directory
Shows all preloaded contacts sorted by Last Name.

```
[➤] Select an operation: 1

=======================================================================================================================================
ID         Full Name (Last, First Middle)      Email                     Phone           Location             Category     Birthday  
---------------------------------------------------------------------------------------------------------------------------------------
CID-1007   Sirena, Ariel Triton                ariel@ocean.com           09237890123     Boracay, Aklan       Friend       1989-07-21
CID-1009   Cow, Belle Maurice                  belle@beast.com           09259012345     Vigan, Ilocos Sur    Work         1991-09-29
...
=======================================================================================================================================
```

### [2] Quick Email Lookup
O(1) Efficiency

```
[➤] Select an operation: 2
[⚡] Enter Email for instant lookup: lilo@ohana.com

=======================================================================================================================================
ID         Full Name (Last, First Middle)      Email                     Phone           Location             Category     Birthday  
---------------------------------------------------------------------------------------------------------------------------------------
CID-1006   Stitch, Lilo Pelekai                lilo@ohana.com            09226789012     Palawan              Family       2002-06-16
=======================================================================================================================================
```

### [3] Attribute-Based Smart Search
The system allows searching by category, location, or birth month.

```
[➤] Select an operation: 3
--- [⌕] SMART SEARCH ENGINE ---
[4] Birth Month Search (Text or Number)
Search criteria: 4
Enter keywords: March

(Results display Donald Duck with CID-1003)
```


### [4] Interaction History Manager
```
[➤] Select an operation: 4
[🕒] Enter Email to access logs: mickey@mouse.com
[1] View Rolling History | [2] Log New Activity
Selection: 1

Interaction Logs for Daga, Mickey Mouse:
[2026-03-05 22:40:01] Initial system synchronization.
[2026-03-05 22:40:01] Contact information verified.
```

### [5] Register New Contact
```
[➤] Select an operation: 5
--- [⎘] NEW CONTACT REGISTRATION ---
First Name: Joyce
Middle Name: 
[✕] Middle Name is required.
Middle Name: Guevarra
...
Email Address: joyce.com
[✕] Invalid format (example@domain.com).
```

### [6] Modify Existing Records
Updates synchronized across all collections.

```
[➤] Select an operation: 6
[✎] Enter Contact ID to modify: CID-1001
Modifying Record: Daga, Mickey Mouse
First Name [Mickey]: Mike
[✔] Updates applied and synchronized.
```

### [7] Remove Contact from System
```
[➤] Select an operation: 7
[⌦] Enter Contact ID to remove: CID-1012
[✔] Contact [CID-1012] purged from system.
```

### [8] Secure Exit
```
[➤] Select an operation: 8
[⌛] Saving system state...
Goodbye! ₍ᐢ› ̫ ‹ᐢ₎
```

---

## Member Contributions

**- Guevarra, Joyce**
Authored the `Preload.java` seeder, designed the initial README documentation, and drafted the Flowchart.

**- Lazaro, Naomi**
Developed the core `Contact.java` class, implemented the LinkedList interaction logging logic, and managed the Peer Evaluation Folder.

**- Marana, Ruth**
Engineered the `Utilities.java` class, including Regex validation and smart formatting, and designed the project's presentation slides.

**- Nollora, Janelyn**
Developed the `SmartPhonebook` engine and `Main.java`. Refined and optimized all classes, revised the README for technical accuracy, and finalized the group presentation and technical flowchart.
