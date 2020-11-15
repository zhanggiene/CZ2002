import java.io.*;
import java.util.*;

/**
     data base for students objects
     * @author zhang zhuyan
     */

public class StudentManager {

    // reference https://stackoverflow.com/questions/30013292/how-do-i-write-multiple-objects-to-the-serializable-file-and-read-them-when-the

   private Hashtable<String, Student> studentAccounts = new Hashtable<String, Student>();
    private String FileName="StudentData.bin";

    StudentManager(){
        loadData();
    }
    
    /** retreive student object from database
     * @param metriculationNumber
     * @return Student
     */
    public Student getStudent(String metriculationNumber)
    {
        return studentAccounts.get(metriculationNumber);
    }

    public void addStudent(Student student)
    {
        studentAccounts.put(student.getMatriculationNumber(),student);
        save();
    }

    private void save()
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
        System.out.println("List of Students: ");
        System.out.println("\tName     \tGender\tNationality\t");
        int i=1;
        for (Student s : studentAccounts.values()) {
            System.out.print(i+".\t");
            s.printStudent();
            i++;
        }

        
    }
    School getSchool(String metricNumber)
    {
        return studentAccounts.get(metricNumber).getSchool();
    }

    public boolean studentExist(String metricNumber)
    {
        return studentAccounts.contains(metricNumber);

    }
    
    
    /** 
     * @param matric1
     * @param coursegroup1
     * @param matric2
     * @param coursegroup2
     * @return boolean
     */
    //updated by WY
	public boolean checkSwap(String matric1, String coursegroup1, String matric2, String coursegroup2) {
		boolean swapped = false;
		for(Map.Entry<String, Student> item : studentAccounts.entrySet()) {
			if(item.getValue().getMatriculationNumber() == matric1) {
				item.getValue().swapIndex(coursegroup2, coursegroup1);
				swapped = true;
			}else
			if(item.getValue().getMatriculationNumber() == matric2) {
				item.getValue().swapIndex(coursegroup1, coursegroup2);
				swapped = true;
			}
		}
		return swapped;
	}

    
    /** 
     * @param args[]
     */
    public static void main(String args[]){
        //testing code
        StudentManager studentManager = new StudentManager();
        studentManager.addStudent(new Student("John Doe", "U1234567B", School.SCSE, Gender.MALE, "Chinese"));
        studentManager.addStudent(new Student("Jane Doe", "U2234567B", School.EEE, Gender.FEMALE, "Singaporean"));
        studentManager.printAllRecord();
    }
    
    
}
