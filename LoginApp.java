import java.io.Console;
import java.util.Date;
import java.util.Scanner;

/**
     it serves as a starting point of the whole program.
     it shows all instructions for the users and receive input from the user
     * @author zhang zhuyan
     */
public class LoginApp 
{

        private LoginTimeManager timeManager;
        private PasswordManager passwordManager;
        private StudentManager studentManager;
        
        public LoginApp(
                        LoginTimeManager timeManager,
                        PasswordManager passwordManager,
                        StudentManager studentManager){
            this.timeManager = timeManager;
            this.passwordManager = passwordManager;
            this.studentManager=studentManager;
                        }
        public String logIn()
        {


        Console console = System.console();
        Scanner input = new Scanner(System.in);
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        System.out.println("Welcome to My Student Automated Registration System (MySTARS)");
        do
    {

            System.out.print("Enter your UserName:");
        String userName = input.nextLine();

        char[] passwordArray = console.readPassword("Enter your password:");
        System.out.println("User name is "+userName);
        
        //console.printf("Password entered was: %s%n", new String(passwordArray));
        //input.close();

        if (passwordManager.isAdmin(userName))
        {
            if (passwordManager.isCorrectAdmin(userName, new String(passwordArray)))
            {
                // go to admin page APP
                return "admin";
            }
            else
            {
                System.out.println("sorry,admin, wrong password");
            }
        }

        else if(passwordManager.studentExist(userName))
        {
            if (passwordManager.isCorrectStudent(userName, new String(passwordArray)))
            {
                School schoolOfStudent=studentManager.getSchool(userName);
                System.out.println("school of Student is "+schoolOfStudent);
                if (timeManager.isInside(schoolOfStudent,new Date()))
                {
                    // within the time
                    // initialize student APPs
                    return userName;
                }
                else if (timeManager.isEarly(schoolOfStudent, new Date())) {

                    System.out.println("sorry, you are too early.\n");
                    System.out.println("your registration time is\n");
                    timeManager.PrintSchoolAcessTime(schoolOfStudent);
                }

                else
                {

                    System.out.println("sorry, you are too late.\n");
                    System.out.println("your registration time is\n");
                    timeManager.PrintSchoolAcessTime(schoolOfStudent);

                }

            }

            else
            {
                System.out.println("sorry, wrong password try again");

            }
        }

        else
                {
                    // not within the time
                    System.out.println("sorry,try again");
                }
    }
        while(true);
}
        
        
}
