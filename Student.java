import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Enum for gender of student
 */
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

/**
 * Entity class for student stores the details of the student eg. name, matriculation number...
 */
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
    
    /**
     * Returns string representation of student
     * @return string representation of student
     */
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

    public boolean removeFromConfirmedCourseGroups(String courseGroupIndex)
    {   
        if (confirmedCourseGroups.containsKey(courseGroupIndex)){
            this.confirmedCourseGroups.remove(courseGroupIndex);
            return true;
        }
        return false;
    }
    
    /**
     * Returns hashmap of confirmed course groups student is in
     * @author Wei Yao
     * @return hashmap of confirmed course groups student is in (index: course code)
     */
    public HashMap<String,String> getConfirmedCourseGroups (){
    	return confirmedCourseGroups;
    }
    
    /**
     * Changes a index of the student
     * @author Wei Yao
     */
    public void swapIndex(String newGroup, String oldGroup) {//group1 = new, group2 = old
    	String coursecode = confirmedCourseGroups.get(oldGroup);
    	confirmedCourseGroups.remove(oldGroup);
    	confirmedCourseGroups.put(newGroup, coursecode);
    }
    
    /**
     * Getter for matricultion number
     * @return matricultion number
     * @author Wei Yao
     */
    public String getMatriculationNumber()
    {
        return matricNumber;
    }
    
    /**
     * Getter for school
     * @author Wei Yao
     */
    public School getSchool()
    {
        return school;
    }

    /**
     * Change a coursegroup index in confirmedCourseGroups
     * @author Wang Li Rong
     */
    public void setCourseGroup(String oldCourseGroupIndex, String newCourseGroupIndex){
        String courseCode = confirmedCourseGroups.get(oldCourseGroupIndex);
        confirmedCourseGroups.remove(oldCourseGroupIndex);
        confirmedCourseGroups.put(newCourseGroupIndex, courseCode);
    }

    /**
     * Change a course code in confirmedCourseGroups
     * Assume that student has the course
     * @author Wang Li Rong
     */
    public void setCourseCode(String oldCourseCode, String newCourseCode){
        String courseGroupIndexToRemove="";
        Set<String> courseGroups = confirmedCourseGroups.keySet();
        for (String courseGroupIndex : courseGroups){
            String courseCode = confirmedCourseGroups.get(courseGroupIndex);
            if (courseCode.equals(oldCourseCode)){
                courseGroupIndexToRemove = courseGroupIndex;
            }
        }
        //To prevent java.util.ConcurrentModificationException
        confirmedCourseGroups.remove(courseGroupIndexToRemove);
        confirmedCourseGroups.put(courseGroupIndexToRemove, newCourseCode);
    }

    /**
     * Checks if student is already in the course
     * @param courseGroupIndex
     * @return
     */
    public boolean isInCourse(String courseGroupIndex){
        return confirmedCourseGroups.containsValue(courseGroupIndex);
    }


    /**
     * Format to print student
     * @author Wang Li Rong
     */
    public void printStudent(){
        System.out.println(name+"\t"+gender+"\t"+nationality+"\t"+this.school);
    }
    /**
     * Prints out all registered course groups of a student
     * @author Wei Yao
     */
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
