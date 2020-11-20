import java.io.*;
import java.util.*;

/**
 data base for students objects
* @author zhang zhuyan
*/
public class StudentManager {

    // reference https://stackoverflow.com/questions/30013292/how-do-i-write-multiple-objects-to-the-serializable-file-and-read-them-when-the

   private Hashtable<String, Student> studentAccounts;
    private String FileName="StudentData.bin";

    StudentManager(){
        load();
    }
    
    /** 
     * retreive student object from database
     * @param metriculationNumber
     * @return Student
     */
    public Student getStudent(String metriculationNumber)
    {
        return studentAccounts.get(metriculationNumber);
    }

    /**
     * Add a new student
     * @param student
     * @author zhu yan
     */
    public void addStudent(Student student)
    {
        studentAccounts.put(student.getMatriculationNumber(),student);
        save();
    }

    /**
     * Save all records
     * @author zhu yan
     */
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

    /**
     * Load all data
     * @author zhu yan
     */
    private void load()
    {
        try {
            File yourFile = new File(this.FileName);
            if(!yourFile.exists()){
                yourFile.createNewFile();
                this.studentAccounts=new Hashtable<String, Student>();
              }
              else
              {
                FileInputStream fis=new FileInputStream(yourFile);
                ObjectInputStream ois=new ObjectInputStream(fis);
                //WriteObject wo=null;
                //WriteObject[] woj=new WriteObject[5];
                this.studentAccounts=(Hashtable<String, Student>) ois.readObject();
                ois.close();
              }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
}
            

    /**
     * Print all students
     * @author Wang Li Rong
     */
    public void printAllRecord() {
        System.out.println("List of Students: ");
        System.out.println("\tName     \tGender\tNationality\tSchool");
        int i=1;
        for (Student s : studentAccounts.values()) {
            System.out.print(i+".\t");
            s.printStudent();
            i++;
        }
    }

    /**
     * Returns the school of a student
     * @param metricNumber
     * @return
     */
    School getSchool(String metricNumber)
    {
        return studentAccounts.get(metricNumber).getSchool();
    }

    /**
     * Find out if student exists
     * @param metricNumber
     * @return true: student exists
     *      <li> false: student does not exist
     * @author zhu yan
     */
    public boolean studentExist(String metricNumber)
    {
        return studentAccounts.containsKey(metricNumber);

    }

    /**
     * Adds course to a student
     * @author Wang Li Rong
     * @param matricNumber
     * @return True: course is added to student succesfully
     *     <li>False: course was not added to student successfully (if student does not exist in system)
     */
    public boolean enrol(String matricNumber, String courseGroupIndex, String courseCode){
        //check that student is in the system
        if (studentAccounts.containsKey(matricNumber)){
            studentAccounts.get(matricNumber).addToCourseGroups(courseGroupIndex, courseCode);
            save(); //since there is a change in the database
            return true;
        }
        return false;
    }

    /**
     * Drops course from a student
     * @param studentMatricNumber
     * @param courseGroupIndex
     * @return True: course is successfully dropped
     */
    public boolean dropCourseGroup(String studentMatricNumber, String courseGroupIndex){
        if (studentExist(studentMatricNumber)){
            boolean result = studentAccounts.get(studentMatricNumber).removeFromConfirmedCourseGroups(courseGroupIndex);
            save();
            return result;
        }
        return false;
    }    

    /**
     * Does swap for 2 students
     */
    public void swap(String[] swapEntry){
        String[] indexArray = swapEntry[0].split(" ");
        //first student (sender)
        getStudent(swapEntry[1]).swapIndex(indexArray[1], indexArray[0]);
        //second student (receiver)
        getStudent(swapEntry[2]).swapIndex(indexArray[0], indexArray[1]);
        save();
    }
}
