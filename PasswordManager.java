import java.util.*;
import java.io.*;

// since there is no setting of admin password,  it is hard coded in to the program, txt file only stores student password and ID


/**
     * it is a password database.
     * add and validate the correctness of the password.
     * @author zhang zhuyan
     */


public class PasswordManager {
    
    private Hashtable<String, String> my_dict = new Hashtable<String, String>();
    private String fileName="password.txt";
    private File file;
    private FileWriter fw;
    private PrintWriter pw;

    public PasswordManager()
    {
        try{

            this.file=new File(this.fileName);
            loadFile();
            this.fw=new FileWriter(this.file, true);
            this.pw=new PrintWriter(this.fw);


        }
        catch(IOException e)
        {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

        

    }

    private void loadFile()
    {
        try{
            Scanner rd=new Scanner(this.file);
            while(rd.hasNextLine())
            {
                String data=rd.nextLine();
                String[] splittedData=data.split(" ",2);
                my_dict.put(splittedData[0],splittedData[1]);

            }
        rd.close();
        
    }
        catch(FileNotFoundException e)
        {
            System.out.println("An error occured.");
            e.printStackTrace();
        }

    }
    
    
    /** assume the studentID does not exist in the database to avoid duplicates
     * @param studentId   unique student id
     * @param passWord   rawpassword
     */
    public void add(String studentId,String passWord)
    
    {
        assert studentExist(studentId);
        my_dict.put(studentId, HashPassword.hashPassword(passWord));
        this.pw.println(studentId+" "+HashPassword.hashPassword(passWord));
        System.out.println("Student added successfuly");
        System.out.println(studentId+" "+HashPassword.hashPassword(passWord));
        this.pw.close();

    }

    
    /** check if the student is already in the database
     * @param studentID
     * @return Boolean
     */
    public Boolean studentExist(String studentID)
    {
        return my_dict.containsKey(studentID);
    }

    
    /** check the correctness of the password typed by student and compare it against passwords database
     * @param studentId
     * @param passWord
     * @return Boolean
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
    
    /**  verify the correctness of the admin password
     * @param admin
     * @param adminPass
     * @return Boolean
     */
    public Boolean isCorrectAdmin(String admin,String adminPass)
    {
        return admin.equals("admin") && adminPass.equals("adminPass");
    }

    
    /** check if it is admin or student
     * @param name
     * @return Boolean    return true if it is admin account
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

    
    /** 
     * @param args
     */
    public static void main(String[] args)
    {
        PasswordManager mypass=new PasswordManager();
        //mypass.add("U1920187L","1920187scse");
        mypass.add("U192018L","192017scse");
        System.out.println(mypass.isCorrectStudent("U1920187L","1920187scse"));

    }




    
}
