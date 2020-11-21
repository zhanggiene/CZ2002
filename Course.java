import java.io.Serializable;
import java.util.ArrayList;

/**
 * Course is an entity class storing all information about the course
 */
public class Course implements Serializable{
    private String courseCode; //CZ2002
    private String name;
    private ArrayList<String> courseGroups;
    private School school;
    private int courseAU;
    // private ArrayList<String> indexes;
    private static final long serialVersionUID = 4L;

    public Course(String courseCode, String name, School scse, int courseAU)
    {
        this.courseCode=courseCode;
        this.name=name;
        this.school=scse;
        this.courseAU = courseAU;
        this.courseGroups = new ArrayList<>();
    
    }
    /**
     * Getter for course code
     * @return course code string
     */
    public String getCourseCode()
    {
        return this.courseCode;
    }
    
    /**
     * Setter for course code
     */
    public void setCourseCode(String newCode){
        this.courseCode = newCode;
    }

    /**
     * Setter for course name
     * @param newName
     */
    public void setCourseName (String newName){
        this.name = newName;
    }

    /**
     * Adds index of courseGroup to list of course groups
     * @param courseGroup
     */
    public void addCourseGroup(String courseGroup)
    {
        courseGroups.add(courseGroup);
    }

    /**
     * Changes an index of a course group in the course
     * @param oldCourseGroup
     * @param newCourseGroup
     */
    public void setCourseGroup(String oldCourseGroup, String newCourseGroup){
        courseGroups.remove(oldCourseGroup);
        courseGroups.add(newCourseGroup);
    }

    /**
     * Gets list of Course Groups of a Course.
     * @author Wang Li Rong
     */
    public ArrayList<String> getCourseGroups() 
    {
        return courseGroups;
    }

    /**
     * Format to print Course
     * @author Andrew
     */
    public void printCourse(){
        System.out.print(courseCode+"\t"+school+"\t"+name+"\t" + courseAU + "\t");
    }
    

     /**
     * Getter for name of course
     * @author Wang Li Rong
     * @return name of course
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter for AUs
     * @author Wenyu
     * @return number of AUs for this course
     */
    public int getCourseAU(){
        return this.courseAU;
    }

    /**
     * Setter for School of this course
     * @author Andrew
     * @param school
     */
    public void setSchool(School school){
        this.school = school;
    }
    
}
