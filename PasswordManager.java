import java.util.*;
import java.io.*;

// since there is no setting of admin password,  it is hard coded in to the program, txt file only stores student password and ID

/**
 * it is a password database. add and validate the correctness of the password.
 * 
 * @author zhang zhuyan
 */

public class PasswordManager  {

    public Hashtable<String, String> my_dict = new Hashtable<String, String>();
    private String fileName = "password.txt";
    private File file;
    private FileWriter fw;
    private PrintWriter pw;

    public PasswordManager() {
        try {

            this.file = new File(this.fileName);
            this.file.createNewFile();
            loadFile();
            this.fw = new FileWriter(this.file, true);
            this.pw = new PrintWriter(this.fw);

        } catch (IOException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

    }

    /**
     * Load records of passwords of each student
     * @author zhu yan
     */
    private void loadFile() {
        try {
            Scanner rd = new Scanner(this.file);
            while (rd.hasNextLine()) {
                String data = rd.nextLine();
                String[] splittedData = data.split(" ", 2);
                my_dict.put(splittedData[0], splittedData[1]);

            }
            rd.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

    }

    /**
     * assume the studentID does not exist in the database to avoid duplicates
     * 
     * @param studentId unique student id
     * @param passWord  rawpassword
     * @author zhu yan
     */
    public void add(String studentId, String passWord)

    {
        // this.pw=new PrintWriter(this.fw);
        assert studentExist(studentId);
        my_dict.put(studentId, HashPassword.hashPassword(passWord));
        // this.pw.println(studentId+" "+HashPassword.hashPassword(passWord));
        //System.out.println("Student added successfuly");
        //System.out.println(studentId + " " + HashPassword.hashPassword(passWord));
        save();
    }

    /**
     * Saves all password records
     * @author zhu yan
     */
    private void save() {
        this.file = new File(this.fileName);
        try {
            this.fw = new FileWriter(this.file, false);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.pw=new PrintWriter(this.fw);
        for (Map.Entry<String, String> entry : my_dict.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            this.pw.println(key+" "+value);
            
            // ...
        }
        this.pw.close();
    }

    
    /** check if the student is already in the database
     * @param studentID
     * @return Boolean
     * @author zhu yan
     */
    public Boolean studentExist(String studentID)
    {
        return my_dict.containsKey(studentID);
    }

    
    /** 
     * check the correctness of the password typed by student and compare it against passwords database
     * @param studentId
     * @param passWord
     * @return Boolean
     * @author zhu yan
     */
    public Boolean isCorrectStudent(String studentId, String passWord)
    {
        if (my_dict.containsKey(studentId))
        {
            if (my_dict.get(studentId).equals(HashPassword.hashPassword(passWord)))
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        else
        {
            System.out.println("The userID does not exist, ask admin to add");
            return false;
        }
    }
    
    /**  
     * verify the correctness of the admin password
     * @param admin
     * @param adminPass
     * @return Boolean
     * @author zhu yan
     */
    public Boolean isCorrectAdmin(String admin,String adminPass)
    {
        return admin.equals("admin") && adminPass.equals("adminPass");
    }

    
    /** check if it is admin or student
     * @param name
     * @return Boolean    return true if it is admin account
     * @author zhu yan
     */
    public Boolean isAdmin(String name)
    {
        if (name.equals("admin"))
        {
            return true;

        }
        else{
            return false;
        }
    }    
}
