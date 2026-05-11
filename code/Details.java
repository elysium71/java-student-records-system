/*
Comp1007 Final Assignment
Author: LAM GORDON 22134321
-Holds per-student enrolment details.-
Notes that:
-Fields are private
-lecture 7
*/

public class Details
{
    // define the Constants ranges for different fields
    public static final int MIN_YEAR = 1;
    public static final int MAX_YEAR = 4;

    public static final double MIN_CWA = 0.0;
    public static final double MAX_CWA = 100.0;

    public static final int MIN_CREDITS = 0;
    public static final int MAX_CREDITS = 400;

    // Private fields for encapsulation (lecture 7)
    private String course;
    private int yearLevel;
    private double cwa;
    private String status;   // "FT" or "PT"
    private int credits;

    // Default constructor, creates Details with safe default values
    public Details()
    {
        this.course = "Unknown";   // default course
        this.yearLevel = MIN_YEAR; // default year = 1
        this.cwa = 0.0;            // default CWA = 0.0
        this.status = "FT";        // default status = Full Time
        this.credits = 0;          // default credits = 0
    }

    // Constructor with parameters, validates and assigns all fields
    // note that the p denotes it is a method parameter. (lecture 5)
    public Details(String pCourse, int pYearLevel, double pCwa, String pStatus, int pCredits)
    {
        setCourse(pCourse);        // check course not empty
        setYearLevel(pYearLevel);  // check year in [1..4]
        setCwa(pCwa);              // check CWA in [0..100]
        setStatus(pStatus);        // check status is FT/PT
        setCredits(pCredits);      // check credits in [0..400]
    }

    // makes a deep copy of another Details object
    public Details(Details pOther)
    {
        if (pOther == null)  
        {
            // Copy from null -> use defaults
            this.course = "Unknown";
            this.yearLevel = MIN_YEAR;
            this.cwa = 0.0;
            this.status = "FT";
            this.credits = 0;
        }
        else
        {
	    // Copy each field from the other object
            this.course = pOther.getCourse();
            this.yearLevel = pOther.getYearLevel();
            this.cwa = pOther.getCwa();
            this.status = pOther.getStatus();
            this.credits = pOther.getCredits();
        }
    }


    // Getter for all details.
    public String getCourse() { return course; }
    public int getYearLevel() { return yearLevel; }
    public double getCwa() { return cwa; }
    public String getStatus() { return status; }
    public int getCredits() { return credits; }
    
    
    

// Setter for Course, Exception case is used to make sure it must not be null/empty/error range.
    public void setCourse(String pCourse)
    {
        if (pCourse == null || pCourse.trim().isEmpty())
        {
            throw new IllegalArgumentException("Course must be non-empty.");
        }
        this.course = pCourse.trim();
    }

// Setter for YearLevel, Exception case is used to make sure it must not be null/empty/error range.
    public void setYearLevel(int pYearLevel)
    {
        if (pYearLevel < MIN_YEAR || pYearLevel > MAX_YEAR)
        {
            throw new IllegalArgumentException("Year level must be between " + MIN_YEAR + " and " + MAX_YEAR + ".");
        }
        this.yearLevel = pYearLevel;
    }

// Setter for Cwa, Exception case is used to make sure it must not be null/empty/error range.
    public void setCwa(double pCwa)
    {
        if (pCwa < MIN_CWA || pCwa > MAX_CWA)
        {
            throw new IllegalArgumentException("CWA must be between " + MIN_CWA + " and " + MAX_CWA + ".");
        }
        this.cwa = pCwa;
    }

// Setter for Status, Exception case is used to make sure it must not be null/empty/error range.
    public void setStatus(String pStatus)
    {
        if (pStatus == null) { throw new IllegalArgumentException("Status cannot be null."); }
        String t = pStatus.trim().toUpperCase();
        if (!t.equals("FT") && !t.equals("PT"))
        {
            throw new IllegalArgumentException("Status must be FT or PT.");
        }
        this.status = t;
    }

// Setter for Credits, Exception case is used to make sure it must not be null/empty/error range.
    public void setCredits(int pCredits)
    {
        if (pCredits < MIN_CREDITS || pCredits > MAX_CREDITS)
        {
            throw new IllegalArgumentException("Credits must be between " + MIN_CREDITS + " and " + MAX_CREDITS + ".");
        }
        this.credits = pCredits;
    }


}
