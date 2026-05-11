/*
Comp1007 Final Assignment
Author: LAM GORDON 22134321
- Models a student with identity fields and aggregated Details.-
*/

public class Student
{
	// Private fields: cannot be accessed directly outside this class)
    private String studentID;   // unique ID string for student
    private String firstName;   // first name
    private String lastName;    // last name
    private Details info;       // each student has-a Details object

	// Default : creates a student with temporary values
    public Student()
    {
        this.studentID = "UNKNOWN";   // default ID is UNKNOWN
        this.firstName = "Unknown";   // default first name
        this.lastName  = "Unknown";   // default last name
        this.info = new Details();    // create a new default Details object
    }


    public Student(String pID, String pFirst, String pLast, Details pInfo)
    {
	//validate all the student value
        setStudentID(pID);     
        setFirstName(pFirst);  
        setLastName(pLast);    
        
        // If caller passes null for pInfo, create a new default Details
        if (pInfo == null)
        {
            this.info = new Details();  // default details object
        }
        else
        {
            this.info = new Details(pInfo);  //makes a copy to protect original
        }
    }


    // Get the value/detail and return it.
    public String getStudentID() { return studentID; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName;  }
    public Details getInfo()     { return new Details(info); } // defensive copy out



// Setter for StudentID, Exception case is used to make sure it must not be null/empty/error range.
    public void setStudentID(String pID)
    {
        if (pID == null || pID.trim().isEmpty())
        {
            throw new IllegalArgumentException("StudentID must be non-empty.");
        }
        this.studentID = pID.trim();
    }

// Setter for FirstName, Exception case is used to make sure it must not be null/empty/error range.
    public void setFirstName(String pFirst)
    {
        if (pFirst == null || pFirst.trim().isEmpty())
        {
            throw new IllegalArgumentException("First name must be non-empty.");
        }
        this.firstName = pFirst.trim();
    }

// Setter for LastName, Exception case is used to make sure it must not be null/empty/error range.
    public void setLastName(String pLast)
    {
        if (pLast == null || pLast.trim().isEmpty())
        {
            throw new IllegalArgumentException("Last name must be non-empty.");
        }
        this.lastName = pLast.trim();
    }

// Setter for Info, Exception case is used to make sure it must not be null/empty/error range.
    public void setInfo(Details pInfo)
    {
        if (pInfo == null)
        {
            throw new IllegalArgumentException("Details cannot be null.");
        }
        this.info = new Details(pInfo); // defensive copy in
    }

// A function to get the full name.
    public String getFullName()
    {
        return firstName + " " + lastName;
    }



    
}
