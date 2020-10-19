import java.util.ArrayList;

public class Course {
    String courseCode; //CZ2002
    int totalSize;
    int Vacancy;
    String name;
    ArrayList<String> CourseGroup;
    School school;

    public void printAllStudent()
    // requirement 6 print student list by course
    //make use of CourseGroup.printStudentList()
    {

    }

    public Course(String courseCode,int size,String name,School school)
    {
        this.courseCode=courseCode;
        this.totalSize=size;
        this.name=name;
        this.school=school;

    }

    public String getcourseCode()
    {
        return this.courseCode;
    }

    public void AddCourseGroup(String courseindex)
    {

    }
    public void changeSize(int newSize)
    {
        this.totalSize=newSize;
    }

    public String toString()
    {
        return Integer.toString(this.totalSize);
    }

    

    
}
