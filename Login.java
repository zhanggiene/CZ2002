import java.io.Console;
import java.util.Date;
import java.util.Scanner;

/**
     it serves as a starting point of the whole program.
     it shows all instructions for the users and receive input from the user
     * @author zhang zhuyan
     */
public class Login {

    

    public static void main(String[] args) {

        PasswordManager mypass=new PasswordManager();
        StudentManager studentManager=new StudentManager();
        LoginTimeManager timeManager=new LoginTimeManager();
        CourseManager courseManager=new CourseManager();
        
        EmailNotificationManager emailNotificationManager = new EmailNotificationManager();

        Console console = System.console();
        Scanner input = new Scanner(System.in);
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        System.out.println("Welcome to My Student Automated Registration System (MySTARS)");
        System.out.print("Enter your UserName:");
        String userName = input.nextLine();

        char[] passwordArray = console.readPassword("Enter your password:");
        System.out.println("User name is "+userName);
        
        //console.printf("Password entered was: %s%n", new String(passwordArray));
        //input.close();

        if (mypass.isAdmin(userName))
        {
            if (mypass.isCorrectAdmin(userName, new String(passwordArray)))
            {
                // go to admin page APP
                AdminApp adminPage = new AdminApp(studentManager,courseManager, timeManager, emailNotificationManager, mypass);
                adminPage.start();
            }
            else
            {
                System.out.println("sorry,admin, wrong password");
            }
        }

        else if(mypass.studentExist(userName))
        {
            if (mypass.isCorrectStudent(userName, new String(passwordArray)))
            {
                School schoolOfStudent=studentManager.getSchool(userName);
                System.out.println("school of Student is "+schoolOfStudent);
                timeManager.printAllAccessPeriod();
                if (timeManager.isInside(schoolOfStudent,new Date()))
                {
                    // within the time
                    // initialize student APPs
                    StudentApp studentApp=new StudentApp(userName,studentManager,courseManager, emailNotificationManager);
                    studentApp.start();
                }
                else
                {
                    // not within the time
                    System.out.println("sorry, not in the correct time.");
                    input.close();
                }

            }

            else
            {
                System.out.println("sorry, wrong password try again");
                input.close();

            }
        }

        else
                {
                    // not within the time
                    System.out.println("sorry,try again");
                    input.close();
                }
    }
}
