import java.io.Console;
import java.util.Date;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) {

        PasswordManager mypass=new PasswordManager();
        StudentManager studentmanager=new StudentManager();
        LoginTimeManager timeManager=new LoginTimeManager();
        CourseManager courseManager=new CourseManager();
        Console console = System.console();
        Scanner input = new Scanner(System.in);
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        System.out.println("Welcome to My STudent Automated Registration System (MySTARS)");
        System.out.print("Enter your UserName:");
        String userName = input.nextLine();

        char[] passwordArray = console.readPassword("Enter your password:");
        System.out.println("User name is"+userName);
        
        //console.printf("Password entered was: %s%n", new String(passwordArray));
        //input.close();

        if (mypass.isAdmin(userName))
        {
            if (mypass.isCorrectAdmin(userName, new String(passwordArray)))
            {
                // go to admin page APP
                AdminApp adminPage =new AdminApp(studentmanager,courseManager);
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
                School schoolOfStudent=studentmanager.getSchool(userName);
                if (timeManager.isInside(schoolOfStudent,new Date()))
                {
                    // within the time
                    // initialize student APP
                    StudentApp studentApp=new StudentApp(userName,studentmanager,courseManager);
                    studentApp.start();


                }
                else
                {
                    // not within the time
                    System.out.println("sorry, not in the correct time");
                    input.close();
                    


                }

            }

            else
            {
                System.out.println("sorry, wrong password try again");
                input.close();

            }


        }
    }
}
