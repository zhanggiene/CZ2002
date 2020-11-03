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

    public String getIndexNumber() {
        return indexNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @return List of Students Matriculation Numbers
     * @author Wang Li Rong
     */
    public ArrayList<String> getStudents() {
        return students;
    }

    public int getVacancy(){
        return vacancy;
    }

    public void isConfirmedStudent(String matriculationNumber){

    }

    public void enrol(String matriculationNumber){
        //still need to add logic 
        //If length of confirmedStudents is >= vacancy, add student to waitlist
        //If length of confirmedStudents is <vacancy, add student to confirmedStudents 
        //list and add this index to Student with addToConfirmedIndex
        //this is just testing code
        students.add(matriculationNumber);
    }

    public void isWaitlistStudent(String matriculationNumber){

    }

    public int getTotalSize(){
        return totalSize;
    }


    public void removeFromConfirmedStudent(){

    }





    
    
}
