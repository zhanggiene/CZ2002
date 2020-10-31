import java.util.*;
import java.io.*;

// since there is no setting of admin password,  it is hard coded in to the program, txt file only stores student password and ID

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
    
    public void add(String studentId,String passWord)
    // assume the studentID does not exist in the database to avoid duplicate
    {
        assert studentExist(studentId);
        my_dict.put(studentId, HashPassword.hashPassword(passWord));
        this.pw.println(studentId+" "+HashPassword.hashPassword(passWord));
        System.out.println("added successfuly");
        System.out.println(studentId+" "+HashPassword.hashPassword(passWord));
        this.pw.close();

    }

    public Boolean studentExist(String studentID)
    {
        return my_dict.containsKey(studentID);
    }

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
    public Boolean isCorrectAdmin(String admin,String adminPass)
    {
        return admin.equals("admin") && adminPass.equals("adminPass");
    }

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

    public static void main(String[] args)
    {
        PasswordManager mypass=new PasswordManager();
        //mypass.add("U1920187L","1920187scse");
        mypass.add("U192018L","192017scse");
        System.out.println(mypass.isCorrectStudent("U1920187L","1920187scse"));

    }




    
}
