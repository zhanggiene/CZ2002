import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable{
    private String courseCode; //CZ2002
    private String name;
    private ArrayList<String> courseGroups;
    private School school;

    public Course(String courseCode, String name, School school)
    {
        this.courseCode=courseCode;
        this.name=name;
        this.school=school;
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

    public boolean courseGroupExist(String courseGroupIndex){
        return courseGroups.contains(courseGroupIndex);
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
