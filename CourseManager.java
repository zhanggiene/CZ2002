import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CourseManager {
    private Map<String, Course> courses=new HashMap<String, Course>(); 
    private Map<String, CourseGroup>courseGroups=new HashMap<String, CourseGroup>();

    //course

    private  String CourseFile="CourseData.bin" ;
    private String CourseGroupFile="CourseGroup.bin";

    public CourseManager()
    {
        loadData();

    }
    public void addCourse(Course a)
    {
        courses.put(a.getcourseCode(),a);
        save();
    }
    public void addCourseGroup(CourseGroup courseGroup)
    {
        courseGroups.put(courseGroup.getIndexNumber(),courseGroup);
        save();
    }

    public Course getCourseByCode(String CourseCode)
    {
        return courses.get(CourseCode);
    }
    public void enroll(Student student,CourseGroup index)
    {
        //to do 
        courseGroups.get(index).enrol(student.getMatriculationNumber());
        student.addToCourseGroups(index.getIndexNumber(), index.getCourseCode());
        save();
    }

    public CourseGroup getCourseGroup(String index)
    {
        return courseGroups.get(index);
    }

    //coursemanager cannot print students because it has no access to students
    // public void printAllStudents(String courseCode)
    // {
    //     // to do
    //     // as the Course object to bring all student 
    // }
    /**
     * Get list of CoursesCodes.
     * @author Wang Li Rong
     */
    public String[] getCourseList() {        
        return Arrays.stream(courses.keySet().toArray()).toArray(String[]::new);
    }

    /**
     * Get list of CourseGroupsIndexes of a Course. 
     * @author Wang li Rong
     */
    public ArrayList<String> getCourseGroupsOfCourse(String courseCode){
        ArrayList<String> courseGroupIndexes = courses.get(courseCode).getCourseGroups(); 
        ArrayList<String> courseGroupList = new ArrayList<>();
        for (String indexes:courseGroupIndexes){
            courseGroupList.add(courseGroups.get(indexes).getIndexNumber());
        }
        return courseGroupList;
    }

    public void save()
    {
        try {
            FileOutputStream fopCourse=new FileOutputStream("./"+this.CourseFile);
            ObjectOutputStream oosCourse=new ObjectOutputStream(fopCourse);
            oosCourse.writeObject(this.courses);
            oosCourse.close();

            FileOutputStream fopCourseGroup=new FileOutputStream("./"+this.CourseGroupFile);
            ObjectOutputStream oosCourseGroup=new ObjectOutputStream(fopCourseGroup);
            oosCourseGroup.writeObject(this.courseGroups);
            oosCourseGroup.close();
    
        }
         catch (Exception e) {
            e.printStackTrace();

    }
    }
    private void loadData()
    {
        try {
            FileInputStream fisCourse=new FileInputStream("./"+this.CourseFile);
            ObjectInputStream oisCourse=new ObjectInputStream(fisCourse);
            this.courses=( Map<String, Course>) oisCourse.readObject();
            oisCourse.close();
            //WriteObject wo=null;
            //WriteObject[] woj=new WriteObject[5];
            FileInputStream fisCourseGroup=new FileInputStream("./"+this.CourseGroupFile);
            ObjectInputStream oisCourseGroup=new ObjectInputStream(fisCourseGroup);
            this.courseGroups=( Map<String, CourseGroup>) oisCourseGroup.readObject();
            oisCourseGroup.close();            
    
        }
        catch (Exception e) {
            e.printStackTrace();
        }
}
            

    public static void main(String[] args) {
        //For testing
        CourseManager manager=new CourseManager();

        //add courses
        Course course=new Course("CZ2002","OODP",School.SCSE);
        manager.addCourse(course);

        //add coursegroups
        CourseGroup courseGroup = new CourseGroup("DSAI1", 50, "CZ2002");
        if (!course.courseGroupExist(courseGroup.getIndexNumber())){
            course.addCourseGroup(courseGroup.getIndexNumber());
            manager.addCourseGroup(courseGroup);
        }
        CourseGroup courseGroup2 = new CourseGroup("DSAI2", 50, "CZ2002");
        if (!course.courseGroupExist(courseGroup2.getIndexNumber())){
            course.addCourseGroup(courseGroup2.getIndexNumber());
            manager.addCourseGroup(courseGroup2);
        }

        courseGroup.enrol("U1234567B");
        courseGroup2.enrol("U2234567B");

        manager.save();

        System.out.println(manager.courseGroups);
        System.out.println(manager.courses);

        // CourseManager manager=new CourseManager();
        // Course a=new Course("CZ2002","oodp",School.SCSE);
        // System.out.println(a.getcourseCode());
        // manager.add(a);
        // Course b=manager.getCourseByCode("CZ2002");
        // b.changeSize(30);
        // System.out.println(manager.getCourseByCode("CZ2002"));
        
    }
    
}
