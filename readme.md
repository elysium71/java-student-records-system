
##  Overview

This program is a **University Student Records System** written pseudocode to Java, with`IMPORT/EXPORT/ASSERTION` design and fully-commented code.

The system allows users to:

1. Add new students
2. Edit existing students by ID
3. View all students
4. Filter students by course
5. Filter students by status (FT/PT)
6. Find student(s) with the highest CWA
7. Calculate average CWA grouped by course
8. Perform credit analysis (graduation eligibility)
9. Save & exit

Data is stored in **`data.csv`** and automatically loaded/saved on when program start/exit.

Default Constants range for different fields is used, it's possible to change the value in the file Details.java

------------------------------------------------------------------------
## Dependencies

- **Java JDK:** 8 or later (tested with **OpenJDK 17**).  
  _Check_: `java -version` and `javac -version`
- **Runtime:** Windows / macOS / Linux (terminal/command prompt available)
- **Standard Library only:**  
  `java.io` (BufferedReader, BufferedWriter, FileReader, FileWriter, PrintWriter, IOException)  
  `java.util` (Scanner)  
  > No external libraries or collections (assignment requirement).
- **File access:** Read/write permission in the working directory to create/update `data.csv`


------------------------------------------------------------------------

## How the three files work together


| File                  | Role                           | Description                                                                                                                                                |
| --------------------- | ------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`Details.java`**    |  *Data Holder*               | Stores a student’s **academic information** such as course, year, CWA, credits, and status.                                                                |
| **`Student.java`**    |  *Student Record*         | Stores a student’s **personal information** (ID, first name, last name) and **has-a** `Details` object that contains academic info.                        |
| **`StudentApp.java`** |  *Main Application (Driver)* | The main program that manages an **array of `Student` objects**, provides the **menu**, handles **user input**, and performs **file I/O** (load/save CSV). |

------------------------------------------------------------------------

##  Files Included

-   `data.csv` -> sample dataset (auto-created if missing).
-   `COMP1007_Assignment_Pseudocode.md` -> pseudocode design
    (keyboard-friendly).
-   `README.md` (this file).
-   `code/Details.java` -> class for course/year/CWA/status/credits
    (encapsulation, validation).
-   `code/Student.java` -> class for student ID, name, and a `Details` object
    (aggregation).
-   `code/StudentApp.java` -> the main program with menu, file I/O, and
    features.

------------------------------------------------------------------------

##  Compilation & Execution

To compile:

``` bash
javac Details.java Student.java StudentApp.java
```

To run:

``` bash
java StudentApp
```

`data.csv` need to be same directory. If it doesn't exist, the program starts with an empty list and creates it when exiting.

------------------------------------------------------------------------

##  Data Format (`data.csv`)

Each student is stored as **one line of CSV**:

    studentID,firstName,lastName,course,yearLevel,cwa,status,credits

### Example:

    S1234567,John,Doe,COMP1007,1,78.50,FT,100
    S2345678,Jane,Smith,COMP1007,3,85.00,PT,300

-   `yearLevel` = integer (1--4)
-   `cwa` = double (0.00--100.00)
-   `status` = FT or PT
-   `credits` = integer (0--400)

------------------------------------------------------------------------

## Submission Checklist & Reflection
- **[x] Add new student**
- **[x] Edit student**
- **[x] View all students**
- **[x] Filter by course**
- **[x] Filter by status**
- **[x] Highest CWA**
- **[x] Average CWA per course**
- **[x] Credit analysis**
- **[x] Loads at start / Saves on exit**


- **[x] Encapsulation** (`private` fields + getters/setters).
- **[x] Aggregation** (`Student` has-a `Details`).
- **[x] Constructors** (default, parameterised, copy).
- **[x] Menu system** (options 1--9).
- **[x] Validation** (year 1--4, CWA 0--100, credits 0--400, FT/PT).
- **[x] File I/O** (BufferedReader/PrintWriter).
- **[x] EPS tolerance** for comparing doubles.
- **[x] Defensive copying** in getters/setters.

- **Parameters**: to make methods reusable and work with different data efficiency. 

------------------------------------------------------------------------



