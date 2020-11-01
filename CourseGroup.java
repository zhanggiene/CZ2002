import java.io.Serializable;
import java.util.ArrayList;

public class CourseGroup implements Serializable{
    private String indexNumber;
    private int totalSize;
    private String courseCode; 
    private int vacancy;
    private ArrayList<PeriodClass> lessons;
    private ArrayList<String> students;
    private ArrayList<String> studentsWaiting; //Q for student queing for this tutorial 
    


    public CourseGroup(String index, int totalSize, String courseCode){
        this.indexNumber = index;
        this.totalSize = totalSize;
        this.courseCode = courseCode;
        this.vacancy = totalSize;
        this.lessons = new ArrayList<>();
        this.students = new ArrayList<>();
        this.studentsWaiting = new ArrayList<>();
    }

    public void printStudentList()
    // print all the student within this tutorial group
    {

    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }


    public void enrol(String matriculationNumber){
        //still need to add logic 
        //If length of confirmedStudents is >= vacancy, add student to waitlist
        //If length of confirmedStudents is <vacancy, add student to confirmedStudents 
        //list and add this index to Student with addToConfirmedIndex
        //this is just testing code
        students.add(matriculationNumber);
    }





    
    
}
