# Student Management System — Java Swing

## Requirements
- JDK 17 or later (JDK 8+ should also work with this source)
- Visual Studio Code
- Extension Pack for Java in VS Code (recommended)

## Run in VS Code
1. Extract the ZIP file.
2. Open the `StudentManagementSystem` folder in VS Code.
3. Open the `src` folder.
4. Open `Main.java`, then click **Run** above the `main` method.

### Run from the terminal
Open a terminal in the `StudentManagementSystem` folder and run:

```bash
javac -d out src/*.java
java -cp out Main
```

On Windows PowerShell, these commands also work if a JDK is installed and on PATH.

## Features
1. Add student records
2. View all student records in a table
3. Search by student ID or name
4. Update selected student details
5. Delete selected records with confirmation
6. Calculate class average
7. Show total, passing, and failing counts

## Validation
- Student ID, name, and grade are required.
- Student IDs must be unique.
- Grade must be numeric and from 0 to 100.
- Year level must be from 1 to 4.
- Delete requires confirmation.
- Update and delete require a selected record.

## Storage note
Records are stored in an `ArrayList` in memory. They are cleared when the application closes. This matches the requested Memory storage option.

## Four OOP pillars
- **Encapsulation:** `Person`, `Student`, and `ScholarStudent` use private fields and public methods for controlled access.
- **Inheritance:** `Student extends Person`; `ScholarStudent extends Student`.
- **Polymorphism:** `getPersonType()` is abstract in `Person` and overridden in `Student` and `ScholarStudent`. The GUI calls it through a `Student` reference.
- **Abstraction:** `Person` is an abstract class that represents shared information and requires subclasses to define their person type.

Note: The GUI currently creates regular `Student` records. `ScholarStudent` is included to demonstrate meaningful inheritance and polymorphism in the class design; it can be extended with a scholarship selector in a future version.
