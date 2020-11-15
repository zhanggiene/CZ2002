import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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
    private String matricNumber;
    private School school;
    private Gender gender;
    private String nationality;
    // arraylist containing tuples containing index and its corresponding class code
    private HashMap<String,String> confirmedCourseGroups; //course-group(index), course-code
    private static final long serialVersionUID = 3L;
    
    
    
    public Student(String name,
                   String matricNumber,
                   School school,
                   Gender gender,
                   String nationality) {
        this.name=name;
        this.matricNumber=matricNumber;
        this.school=school;
        this.gender=gender;
        this.nationality=nationality;
        this.confirmedCourseGroups = new HashMap<>();
    }
    
    public String toString() {
        return name+" "+matricNumber;
    }
    
    /**
     * Adds to confirmedCourseGroups. 
     * @author Wang Li Rong
     * Updated by WY
     */
    public void addToCourseGroups(String courseGroup, String courseCode)
    {
        this.confirmedCourseGroups.put(courseGroup, courseCode);
    }
    
    //Updated by WY
    public HashMap<String,String> getConfirmedCourseGroups (){
    	return confirmedCourseGroups;
    }
    
    public void swapIndex(String group1, String group2) {//group1 = new, group2 = old
    	String coursecode = confirmedCourseGroups.get(group2);
    	confirmedCourseGroups.remove(group2);
    	confirmedCourseGroups.put(group1, coursecode);
    }
    
    public String getMatriculationNumber()
    {
        return matricNumber;
    }

    /*
    public Boolean CheckClashes(String index)
    {

        return false;
    }
    */
    
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
    //Updated by WY
    public void printRegisterCourse() {
    	System.out.println("|Your registered course groups     |");
    	System.out.println("================================================");
    	System.out.println("|  Course Group\tCourse Code");
    	int i = 1;
    	for(Map.Entry<String, String> item: confirmedCourseGroups.entrySet()){
    		System.out.println("|"+i+". "+ item.getKey() + "\t" + item.getValue());
    		i++;
    	}
    	System.out.println("================================================");
    }
    
}
