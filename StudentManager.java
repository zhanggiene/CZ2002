import java.io.*;
import java.util.*;



public class StudentManager {

    // reference https://stackoverflow.com/questions/30013292/how-do-i-write-multiple-objects-to-the-serializable-file-and-read-them-when-the

   private Hashtable<String, Student> studentAccounts = new Hashtable<String, Student>();
    private String FileName="StudentData.bin";

    StudentManager(){
        loadData();

    }
    Student getStudent(String metriculationNumber)
    {
        return studentAccounts.get(metriculationNumber);
    }

    public void addStudent(Student s)
    {
        studentAccounts.put(s.getMetriculationNumber(),s);
        save();
    }
    public void save()
    {
        try {
            FileOutputStream fop=new FileOutputStream("./"+this.FileName);
            ObjectOutputStream oos=new ObjectOutputStream(fop);
            oos.writeObject(this.studentAccounts);
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
    
            this.studentAccounts=(Hashtable<String, Student>) ois.readObject();
            ois.close();
    
        }
        catch (Exception e) {
            e.printStackTrace();
        }
}
            


    public void printAllRecord() {

        for (Student s : studentAccounts.values()) {
            System.out.println(s);
        }

        
    }
    School getSchool(String metriculationNumber)
    {
        return studentAccounts.get(metriculationNumber).getSchool();
    }

    public boolean studentExist(String metriculationNumber)
    {
        return studentAccounts.contains(metriculationNumber);

    }
    
    
}
