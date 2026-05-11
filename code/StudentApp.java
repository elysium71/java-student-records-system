/*
Class: StudentApp
Author: LAM GORDON 22134321

-Menu-driven student records system using
 arrays, file I/O (CSV)
 modular methods.-
 
*/

import java.io.*;
import java.util.Scanner;       // for user input from the console

public class StudentApp
{
    // constants
    public static final int MAX_STUDENTS = 1000; // maximum capacity of the array
    public static final String DATA_FILENAME = "data.csv";  // filename to load/save
    public static final double EPS = 0.0001; // tolerance for double compare


    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);                 // create scanner for user input
        Student[] students = new Student[MAX_STUDENTS];      // array to store students
        int usedCount = 0;                                   // number of students actually stored

        usedCount = loadFromCSV(DATA_FILENAME, students);    // try loading students from file

        int choice = 0;
        do
        {
            choice = menuAndGetChoice(sc);   // ask user for menu option
            switch (choice)
            {
                case 1: usedCount = addStudent(sc, students, usedCount); break; // add new
                case 2: editStudent(sc, students, usedCount); break;            // edit existing
                case 3: viewAll(students, usedCount); break;                    // view all
                case 4: filterByCourse(sc, students, usedCount); break;         // filter by course
                case 5: filterByStatus(sc, students, usedCount); break;         // filter by status
                case 6: highestCWA(students, usedCount); break;                 // find highest CWA
                case 7: averageCWAByCourse(students, usedCount); break;         // average per course
                case 8: creditAnalysis(students, usedCount); break;             // graduation check
                case 9:
                    saveToCSV(DATA_FILENAME, students, usedCount);              // save before exit
                    System.out.println("Goodbye!");
                    break;
                default: System.out.println("Unknown option."); break;
            }
        } while (choice != 9); // repeat until user chooses Exit

        sc.close(); // close scanner
    }

    // prints menu, asks user for choice 1..9, and validates input
    public static int menuAndGetChoice(Scanner sc)
    {
        int choice = 0;
        do {
            System.out.println();
            System.out.println("=============================================");
            System.out.println("Student Records Menu");
            System.out.println("=============================================");
            System.out.println("Your options for this system are listed below\n");
            System.out.println("1) Add student");
            System.out.println("2) Edit student");
            System.out.println("3) View all");
            System.out.println("4) Filter by course");
            System.out.println("5) Filter by status (FT/PT)");
            System.out.println("6) Highest CWA");
            System.out.println("7) Average CWA by course");
            System.out.println("8) Credit analysis");
            System.out.println("9) Exit");
            choice = readInt(sc, "Enter choice 1-9: "); 
        } while (choice < 1 || choice > 9);
        return choice;   // return validated choice
    }

    // loads student records from CSV file (lecture 8)
    public static int loadFromCSV(String filename, Student[] students)
    {
        int count = 0;
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(filename)); // open file for reading
            String line = br.readLine();                       // read first line
            while (line != null && count < MAX_STUDENTS)       // while not End Of File
            {
                line = line.trim();
                if (line.length() > 0)                         // skip empty lines
                {
                    Student s = parseStudent(line);            // try to turn CSV row into Student
                    if (s != null)                              // only add valid rows
                    {
                        students[count] = s;                   // store in array
                        count++;
                    }
                }
                line = br.readLine();                          // read next line
            }
        }
        //exception case to make a new file for importing value from empty)
        
        catch (IOException ex)      
        {
            System.out.println("Warning: could not load file '" + filename + "'. Starting with empty data.");
        }
        //This code will always execute
        finally
        {
            try { if (br != null) br.close(); } catch (IOException e) { }  // only close when not null
        }
        System.out.println("Loaded " + count + " record(s).");
        return count;
    }

    // saves student records to CSV file (leature 8)
    public static void saveToCSV(String filename, Student[] students, int count)
    {
        PrintWriter pw = null;  // writer handle
        try
        {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(filename))); // open file for writing
            for (int i = 0; i < count; i++)
            {
                pw.println(formatCSVRow(students[i])); // write each student as CSV row
            }
            System.out.println("Saved " + count + " record(s).");  // confirm to user
        }
        catch (IOException ex)
        {
            System.out.println("Error: could not save file '" + filename + "'.");
        }
        // This code will always execute
        finally
        {
            if (pw != null) pw.close();  // PrintWriter close is safe to call
        }
    }

    // parses one CSV line into a Student object
    public static Student parseStudent(String csvRow)
    {
        String[] tokens = csvRow.split(",", -1); // split by commas
        if (tokens.length < 8)	// must have 8 fields if not a execption case.
        {
            System.out.println("Skipping invalid row (wrong number of fields): " + csvRow);
            return null;    // Exception case for error
        }
        try
        {
            String id = tokens[0].trim();   // student ID (String)
            String first = tokens[1].trim();    // first name
            String last = tokens[2].trim();     // last name
            String course = tokens[3].trim();   // course code/name
            int year = Integer.parseInt(tokens[4].trim());  // parse year to int
            double cwa = Double.parseDouble(tokens[5].trim());  // parse CWA to double
            String status = tokens[6].trim();   // FT/PT text
            int credits = Integer.parseInt(tokens[7].trim());   // parse credits to int

            Details info = new Details(course, year, cwa, status, credits); // make Details
            Student s = new Student(id, first, last, info); 		    // make Student
            return s;
        }
        catch (Exception ex)
        {
            System.out.println("Skipping invalid row (parse/validation): " + csvRow);
            return null;  // Exception case for error
        }
    }

    // formats a Student object into one CSV line
    public static String formatCSVRow(Student s) {
        Details d = s.getInfo();        // get a (defensive) copy of Details
        return s.getStudentID() + "," + // id
           s.getFirstName() + "," + // first
           s.getLastName() + "," +  // last
           d.getCourse() + "," +    // course
           d.getYearLevel() + "," + // year
           String.format("%.2f", d.getCwa()) + "," +    // CWA formatted to 2dp
           d.getStatus() + "," + // FT/PT
           d.getCredits();  // credits
    }


    // add new student via user input
    public static int addStudent(Scanner sc, Student[] students, int usedCount)
    {
    	//excpetion case if database is full
        if (usedCount >= MAX_STUDENTS)
        {
            System.out.println("Database full.");
            return usedCount;
        }

        String id; // declare a variable to hold the new student's ID
        do {
            id = readLine(sc, "Student ID: ").trim();
            // repeat if ID is empty OR already exists in the array
        } while (id.length() == 0 || findIndexByID(students, usedCount, id) != -1);

	// Ask the user and import all details, excpection case, ask again if the value is not valid.
        String first = readNonEmptyLine(sc, "First name: ");
        String last  = readNonEmptyLine(sc, "Last name: ");
        String course= readNonEmptyLine(sc, "Course: ");
        
        // read an int in bounds
	int year = readIntInRange(sc, 
        "Year level (" + Details.MIN_YEAR + "-" + Details.MAX_YEAR + "): ", 
        Details.MIN_YEAR, Details.MAX_YEAR);
        
        // read a double in bounds
	double cwa = readDoubleInRange(sc, 
        "CWA (" + Details.MIN_CWA + "-" + Details.MAX_CWA + "): ", 
        Details.MIN_CWA, Details.MAX_CWA);

        // only FT or PT
        String status= readStatus(sc, "Status (FT/PT): ");
        
	// read credits in bounds
	int credits = readIntInRange(sc, 
        "Credits (" + Details.MIN_CREDITS + "-" + Details.MAX_CREDITS + "): ", 
        Details.MIN_CREDITS, Details.MAX_CREDITS);
        
        Details info = new Details(course, year, cwa, status, credits); // build validated Details
        students[usedCount] = new Student(id, first, last, info); // store in array
        System.out.println("Added.");
        return usedCount + 1; // return new number of students stored (one more than before)
    }


    // edit existing student by ID
    public static void editStudent(Scanner sc, Student[] students, int usedCount)
    {
        if (usedCount == 0)     // if no students stored yet, exit
        {
            System.out.println("No records.");
            return;
        }
            // ask user for the student ID they want to edit
        String id = readLine(sc, "Enter Student ID to edit: ").trim();
        
            // search for that ID in the array
        int idx = findIndexByID(students, usedCount, id);
            // if not found, tell user and stop
        if (idx == -1)
        {
            System.out.println("Not found.");
            return;
        }
         
        Student s = students[idx]; // get the Student object from the array
        Details d = s.getInfo();     // get a copy of its Details

        System.out.println("Press Enter to keep current value.");

	// ask for new details (optional), only update if user typed something
        String newFirst = readOptionalLine(sc, "First name [" + s.getFirstName() + "]: ");
        if (newFirst.length() > 0) s.setFirstName(newFirst);

        String newLast = readOptionalLine(sc, "Last name [" + s.getLastName() + "]: ");
        if (newLast.length() > 0) s.setLastName(newLast);

        String newCourse = readOptionalLine(sc, "Course [" + d.getCourse() + "]: ");
        if (newCourse.length() > 0) d.setCourse(newCourse);

	// read year text or blank
	String yStr = readOptionalLine(sc, 
	"Year [" + d.getYearLevel() + "] " + Details.MIN_YEAR + "-" + Details.MAX_YEAR + ": ");


        if (yStr.length() > 0) // if user typed something
        {
            // try validate in setter
            try { d.setYearLevel(Integer.parseInt(yStr)); }
            catch (Exception ex) { System.out.println("Ignored invalid year."); }  // on error, keep old year
        }

	// read CWA text or blank
	String cwaStr = readOptionalLine(sc, 
    	"CWA [" + String.format("%.2f", d.getCwa()) + "] " 
    	+ Details.MIN_CWA + "-" + Details.MAX_CWA + ": ");
        if (cwaStr.length() > 0)
        {
            // try validate in setter
            try { d.setCwa(Double.parseDouble(cwaStr)); }
            catch (Exception ex) { System.out.println("Ignored invalid CWA."); }
        }
        
	// read FT/PT text or blank
        String stStr = readOptionalLine(sc, "Status [" + d.getStatus() + "] FT/PT: ");
        if (stStr.length() > 0)
        {
            // try validate in setter
            try { d.setStatus(stStr); }
            catch (Exception ex) { System.out.println("Ignored invalid status."); }
        }

	// read Credit text or blank
	String crStr = readOptionalLine(sc, 
  	  "Credits [" + d.getCredits() + "] " 
   	 + Details.MIN_CREDITS + "-" + Details.MAX_CREDITS + ": ");
        if (crStr.length() > 0) // only process if the user typed something
        {
            // try validate in setter
            try { d.setCredits(Integer.parseInt(crStr)); }
            catch (Exception ex) { System.out.println("Ignored invalid credits."); }
        }

        // write back updated details
        s.setInfo(d);       // write the edited Details back
        students[idx] = s;  // store the data
        System.out.println("Updated.");
    }

    // display all students in the array
    public static void viewAll(Student[] students, int usedCount)
    {
        // if no records exist, show message and return
        if (usedCount == 0)
        {
            System.out.println("No records.");
            return;
        }
        printHeader(); // print table header
        for (int i = 0; i < usedCount; i++) // loop through array
        {
            printStudentLine(students[i]); // print each student
        }
    }

// filter students by course name
    public static void filterByCourse(Scanner sc, Student[] students, int usedCount)
    {
        if (usedCount == 0) // exit if empty
        {
            System.out.println("No records.");
            return;
        }
            // ask user which course to filter
        String target = readNonEmptyLine(sc, "Course to filter: "); // prompt for course (must not be empty)
        boolean found = false;  // track if any match was printed
        printHeader(); // show table header
        for (int i = 0; i < usedCount; i++) // scan all students
        {
                // compare student's course with target
            if (equalsIgnoreCase(students[i].getInfo().getCourse(), target))
            {
                printStudentLine(students[i]);	// print if match
                found = true;
            }
        }
        if (!found) System.out.println("(No matches.)");  // feedback when no course matched
    }

// filter students by FT/PT status
    public static void filterByStatus(Scanner sc, Student[] students, int usedCount)
    {
        if (usedCount == 0) // exit if empty
        {
            // ask user for FT or PT
            System.out.println("No records.");
            return;
        }
        String target = readStatus(sc, "Status to filter (FT/PT): ");
        boolean found = false;      // track if any match was printed
        printHeader(); // show header
        for (int i = 0; i < usedCount; i++)
        {
            if (equalsIgnoreCase(students[i].getInfo().getStatus(), target))
            {
                printStudentLine(students[i]);  // print if status matches
                found = true;
            }
        }
        if (!found) System.out.println("(No matches.)");
    }

// find and display student(s) with the highest CWA
    public static void highestCWA(Student[] students, int usedCount)
    {
        if (usedCount == 0)
        {
            System.out.println("No records.");
            return;
        }
        // assume first student has max CWA
        double max = students[0].getInfo().getCwa();    // init max with first student's CWA
            // loop to find maximum CWA
        for (int i = 1; i < usedCount; i++) // check the rest of the students
        {
            double c = students[i].getInfo().getCwa();  // current student's CWA
            if (c > max) max = c;   // update max when a larger value is found
        }
            // print all students whose CWA is equal to max (within EPS tolerance)
        System.out.println("Highest CWA = " + String.format("%.2f", max));  // show the max value with 2 dp
        printHeader();      // header for the list of ties
        for (int i = 0; i < usedCount; i++)     // scan again to print all ties
        {
            double c = students[i].getInfo().getCwa();      // current CWA
            if (Math.abs(c - max) <= EPS)       // treat as equal if within small tolerance
            {
                printStudentLine(students[i]);      // print tied student
            }
        }
    }

// calculate and display average CWA grouped by course
    public static void averageCWAByCourse(Student[] students, int usedCount)
    {
        if (usedCount == 0)
        {
            System.out.println("No records.");
            return;
        }
            // arrays for storing course names, sum of CWAs, and counts
        String[] courseNames = new String[MAX_STUDENTS];        // stores  course names
        double[] courseSum   = new double[MAX_STUDENTS];        // sum of CWAs per course
        int[] courseCnt      = new int[MAX_STUDENTS];       // number of students per course
        int uniqueCount = 0;  // number of unique courses found

        for (int i = 0; i < usedCount; i++)     // loop over all students
        {
            String c = students[i].getInfo().getCourse(); // get this student's course
            int pos = indexOf(courseNames, uniqueCount, c);  // check if course already stored
            if (pos == -1)  // new course
            {
                courseNames[uniqueCount] = c;       // store new course name
                courseSum[uniqueCount] = students[i].getInfo().getCwa();    // start sum with current CWA
                courseCnt[uniqueCount] = 1; // start count at 1
                uniqueCount++;  // increment number of unique courses
            }
            else  // course already exists, add to totals
            {
                courseSum[pos] += students[i].getInfo().getCwa();   // add sum
                courseCnt[pos] += 1; // add count
            }
        }

    // print averages
        System.out.println("Course, Average CWA, Count");
        for (int j = 0; j < uniqueCount; j++)
        {
            double avg = courseSum[j] / courseCnt[j];   // compute average CWA
            System.out.println(courseNames[j] + ", " + String.format("%.2f", avg) + ", " + courseCnt[j]);
        }
    }

// analyze credits to check graduation eligibility
    public static void creditAnalysis(Student[] students, int usedCount)
    {
        if (usedCount == 0)
        {
            System.out.println("No records.");
            return;
        }
        final int GRAD_CREDITS = Details.MAX_CREDITS; // 400
        System.out.println("ID, Name, Credits, Eligible(Y/N)"); 
        for (int i = 0; i < usedCount; i++)
        {
            Details d = students[i].getInfo();  // get details
            boolean eligible = d.getCredits() >= GRAD_CREDITS;  // check if credits >= 400
            System.out.println(students[i].getStudentID() + ", " + students[i].getFullName()
                    + ", " + d.getCredits() + ", " + (eligible ? "Y" : "N"));
        }
    }

//
// Utility Methods
//
//

// helper: find index of student in array by ID
    public static int findIndexByID(Student[] students, int usedCount, String id)
    {
        for (int i = 0; i < usedCount; i++)
        {
            if (equalsIgnoreCase(students[i].getStudentID(), id)) return i; // found
        }
        return -1; // not found
    }

// case-insensitive string comparison
    public static boolean equalsIgnoreCase(String a, String b)
    {
        if (a == null || b == null) return false;
        return a.equalsIgnoreCase(b);
    }

// find index of string in array of strings
    public static int indexOf(String[] arr, int used, String key)
    {
        for (int i = 0; i < used; i++)
        {
            if (equalsIgnoreCase(arr[i], key)) return i;
        }
        return -1; // not found
    }

// print the header row for the student table
    public static void printHeader()
    {
        System.out.println("ID, First, Last, Course, Year, CWA, Status, Credits");
    }

// print a single student's data in one line
    public static void printStudentLine(Student s)
    {
        Details d = s.getInfo();
        System.out.println(s.getStudentID() + ", " + s.getFirstName() + ", " + s.getLastName() + ", "
                + d.getCourse() + ", " + d.getYearLevel() + ", "
                + String.format("%.2f", d.getCwa()) + ", "
                + d.getStatus() + ", " + d.getCredits());
    }

// input read strings and numbers safely with Scanner
// read any line of input (can be empty)
    public static String readLine(Scanner sc, String prompt)
    {
        System.out.print(prompt); // show the prompt message
        return sc.nextLine(); // return what the user typed, including empty string
    }
    
// read a non-empty line (forces user to type something)
    public static String readNonEmptyLine(Scanner sc, String prompt)
    {
        String s = "";
        do {
            System.out.print(prompt);
            s = sc.nextLine().trim();
        } while (s.length() == 0);
        return s;
    }

// read a line but allow it to be empty (used for editing fields)
    public static String readOptionalLine(Scanner sc, String prompt)
    {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

// read an integer, keep asking until valid number entered
    public static int readInt(Scanner sc, String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try
            {
                return Integer.parseInt(line);
            }
            catch (Exception ex)
            {
                System.out.println("Please enter a whole number.");
            }
        }
    }

// read a double, keep asking until valid number entered
    public static double readDouble(Scanner sc, String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try
            {
                return Double.parseDouble(line);
            }
            catch (Exception ex)
            {
                System.out.println("Please enter a valid number.");
            }
        }
    }

// read an int and force it to be inside [lo..hi]
    public static int readIntInRange(Scanner sc, String prompt, int lo, int hi)
    {
        int x;
        do {
            x = readInt(sc, prompt);
        } while (x < lo || x > hi); // repeat if outside range
        return x;
    }

    public static double readDoubleInRange(Scanner sc, String prompt, double lo, double hi)
    {
        double x;
        do {
            x = readDouble(sc, prompt);
        } while (x < lo || x > hi); // repeat if outside range
        return x;
    }

    public static String readStatus(Scanner sc, String prompt)
    {
        String s = "";
        do {
            System.out.print(prompt);
            s = sc.nextLine().trim().toUpperCase();
        } while (!(s.equals("FT") || s.equals("PT"))); // loop until "FT" or "PT"
        return s;
    }
}
