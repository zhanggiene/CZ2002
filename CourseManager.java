import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CourseManager {
    Map<String, Course> my_dict=new HashMap<String, Course>();
    String FileName;

    public CourseManager(String fileName)
    {
        this.FileName= fileName;
    }
    public void add(Course a)
    {
        this.my_dict.put(a.getcourseCode(),a);
    }

    public Course getCourseByCode(String CourseCode)
    {
        return this.my_dict.get(CourseCode);
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
    public void loadData()
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
        CourseManager manager=new CourseManager("CourseData.bin");
        Course a=new Course("CZ2002",600,"oodp",School.SCSE);
        System.out.println(a.getcourseCode());
        manager.add(a);
        Course b=manager.getCourseByCode("CZ2002");
        b.changeSize(30);
        System.out.println(manager.getCourseByCode("CZ2002"));
        
    }



    


    // 
    
}
