import java.io.Serializable;
import java.util.ArrayList;

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
        this.courseAU=courseAU;
        this.courseGroups = new ArrayList<>();
    
    }
    
    public String getcourseCode()
    {
        return this.courseCode;
    }

    public void setCourseCode(String newCode){
        this.courseCode = newCode;
    }

    public void setCourseName (String newName){
        this.name = newName;
    }

    public void addCourseGroup(String courseGroup)
    {
        courseGroups.add(courseGroup);
    }

    // public ArrayList<String> getIndexes()
    // {
    //     return this.indexes;
    // }

    public boolean courseGroupExist(String courseGroupIndex)
    {
        return courseGroups.contains(courseGroupIndex);
    }

    /**
     * Gets list of Course Groups of a Course.
     * @author Wang Li Rong
     * @return List of Course Group Indexes
     */
    public ArrayList<String> getCourseGroups() 
    {
        return courseGroups;
    }

    /**
     * Format to print Course
     * Andrew
     */
    public void printCourse(){
        System.out.print(courseCode+"\t"+school+"\t"+name+"\t" + courseAU + "\t");
    }



	public ArrayList<String> getCourseGroup() {
		return courseGroups;
	}

    //Now each coursegroup has a totalSize instead
    // public void changeSize(int newSize)
    // {
    //     this.totalSize=newSize;
    // }

    // public String toString()
    // {
    //     return Integer.toString(this.totalSize);
    // }
    
    /**
     * Getter for name of course
     * @author Wang Li Rong
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * @author Wenyu
     * @return
     */
    public int getCourseAU(){
        return this.courseAU;
    }

    public void setCourseAU(int numAU){
        this.courseAU = numAU;
    }
}
