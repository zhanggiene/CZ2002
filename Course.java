import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable{
    private String courseCode; //CZ2002
    private String name;
    private ArrayList<String> courseGroups;
    private School school;
    // private ArrayList<String> indexes;
    private static final long serialVersionUID = 4L;

    public Course(String courseCode, String name, School scse)
    {
        this.courseCode=courseCode;
        this.name=name;
        this.school=scse;
        this.courseGroups = new ArrayList<>();
    
    }

    

    public String getcourseCode()
    {
        return this.courseCode;
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
     */
    public ArrayList<String> getCourseGroups() 
    {
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

    

    
}
