import java.io.*;
import java.util.*;



public class StudentManager {

    // reference https://stackoverflow.com/questions/30013292/how-do-i-write-multiple-objects-to-the-serializable-file-and-read-them-when-the

    Hashtable<String, Student> my_dict = new Hashtable<String, Student>();
    String FileName;

    StudentManager(String FileName){
        this.FileName=FileName;

    }
    public void add(String studentMetric,Student s)
    {
        my_dict.put(studentMetric,s);
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
    
            this.my_dict=(Hashtable<String, Student>) ois.readObject();
            ois.close();
    
        }
        catch (Exception e) {
            e.printStackTrace();
        }
}
            


    public void printAllRecord() {

        for (Student s : my_dict.values()) {
            System.out.println(s);
        }

        
    }
    



    public static void main(String[] args) {

        StudentManager studentmanager=new StudentManager("StudentData.bin");
        //studentmanager.loadData();
        //studentmanager.printAllRecord();

        /*Student s1 = new Student("random1", "U2020187L"); 
        Student s2 = new Student("random2", "U2020188L"); 
        studentmanager.add(s1.MetriculationNumber,s1);
        studentmanager.add(s2.MetriculationNumber,s2);
        studentmanager.printAllRecord();
        studentmanager.save();
        */
        studentmanager.loadData();
        Student s2 = new Student("random3", "U2020189L"); 
        studentmanager.add(s2.MetriculationNumber,s2);
        studentmanager.printAllRecord();

    
}
    
}