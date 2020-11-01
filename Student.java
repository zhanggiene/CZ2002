import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

enum Gender {
    FEMALE{
        @Override
        public String toString() {
            return "Female";
        }
    },
    MALE{
        @Override
        public String toString() {
            return "Male";
        }
    }
}

public class Student implements Serializable
{
    private String name;
    private String matriculationNumber;
    private School school;
    private Gender gender;
    private String nationality;
    // arraylist containing tuples containing index and its corresponing class code
    private HashMap<String,String> confirmedCourseGroups; //courseGroup: courseCode
    
    public Student(String name,
                   String matriculationNumber,
                   School school,
                   Gender gender,
                   String nationality) {
        this.name=name;
        this.matriculationNumber=matriculationNumber;
        this.school=school;
        this.gender=gender;
        this.nationality=nationality;
        this.confirmedCourseGroups = new HashMap<>();
    }
    
    public String toString() {
        return name+" "+matriculationNumber;
    }
    /**
     * Adds to confirmedCourseGroups. 
     * @author Wang Li Rong
     */
    public void addToCourseGroups(String index, String courseCode)
    {
        this.confirmedCourseGroups.put(index, courseCode);
    }
    
    public String getMatriculationNumber()
    {
        return matriculationNumber;
    }

    public Boolean CheckClashes(String index)
    {
        return false;
    }

    public School getSchool()
    {
        return school;
    }

    /**
     * Format to print student
     * @Li Rong
     */
    public void printStudent(){
        System.out.println(name+"\t"+gender+"\t"+nationality);
    }




    
}
