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

    public boolean isConfirmedStudent(String matriculationNumber){
        for(int i = 0; i<=students.size(); i++){
            if(matriculationNumber == students[i])
                return true;
              
            else{
                return false;
            }
        }
    }

    public void enrol(String matriculationNumber){
        //still need to add logic 
        //If length of confirmedStudents is >= vacancy, add student to waitlist
        //If length of confirmedStudents is <vacancy, add student to confirmedStudents 
        //list and add this index to Student with addToConfirmedIndex
        //this is just testing code
        
        if(students.size() >= vacancy)
            studentsWaiting.add(matriculationNumber);
        else
            students.add(matriculationNumber);
    }

    public boolean isWaitlistStudent(String matriculationNumber){
        for(int i=0; i<=studentsWaiting.size(); i++){
            if(matriculationNumber == studentsWaiting[i]){
                return true;
            }
            else
                return false;
        }
    }

    public int getTotalSize(){
        return totalSize;
    }


    public void removeFromConfirmedStudent(String students){
        students.remove(students);
    }
    
    public void addLesson(PeriodClass lesson){
        lessons.add(lesson);
    }

    public void isClashing(CourseGroup otherindex){
        
    }





    
    
}
