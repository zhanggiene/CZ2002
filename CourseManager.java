import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CourseManager {
    Map<String, Course> courses=new HashMap<String, Course>(); 
    Map<String, CourseGroup>courseGroups=new HashMap<String, CourseGroup>();

    //course

    String CourseFile="CourseData.bin" ;
    String CourseGroupFile="CourseGroup.bin";

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
            FileOutputStream fop=new FileOutputStream("./"+this.FileName);
            ObjectOutputStream oos=new ObjectOutputStream(fop);
            oos.writeObject(this.my_dict);
            oos.close();
    
        }
         catch (Exception e) {
            e.printStackTrace();

    }
    }
    private void loadData()
    {
        try {
            FileInputStream fis=new FileInputStream("./"+this.FileName);
            ObjectInputStream ois=new ObjectInputStream(fis);
            //WriteObject wo=null;
            //WriteObject[] woj=new WriteObject[5];
    
            this.my_dict=( Map<String, Course>) ois.readObject();
            ois.close();
    
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
