import java.io.*;
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
    }
    public void addCourseGroup(CourseGroup courseGroup)
    {
        courseGroups.put(courseGroup.getIndexNumber(),courseGroup);
    }

    public Course getCourseByCode(String CourseCode)
    {
        return courses.get(CourseCode);
    }
    public void enroll(Student student,CourseGroup index)
    {
        //to do 
        courseGroups.get(index).enrol(student.getMetriculationNumber());
        student.addToConfirmedIndex(index.getIndexNumber());

    }

    public CourseGroup getCourseGroup(String index)
    {
        return courseGroups.get(index);
    }

    public void printAllStudents(String courseCode)
    {
        // to do
        // as the Course object to bring all student 
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
        CourseManager manager=new CourseManager();
        Course a=new Course("CZ2002",600,"oodp",School.SCSE);
        System.out.println(a.getcourseCode());
        manager.add(a);
        Course b=manager.getCourseByCode("CZ2002");
        b.changeSize(30);
        System.out.println(manager.getCourseByCode("CZ2002"));
        
    }



    


    // 
    
}
