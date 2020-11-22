

import java.io.Console;
import java.util.Date;
import java.util.Scanner;

/**
     it serves as a starting point of the whole program.
     it shows all instructions for the users and receive input from the user
     * @author zhang zhuyan
     */

public class Star {

    

        public static void main(String[] args) {
    
            PasswordManager mypass=new PasswordManager();
            StudentManager studentManager=new StudentManager();
            LoginTimeManager timeManager=new LoginTimeManager();
            CourseManager courseManager=new CourseManager();
            EmailNotificationManager emailNotificationManager=new EmailNotificationManager();
            AdminApp adminPage = new AdminApp(studentManager,courseManager, timeManager,mypass);
            LoginApp loginPage=new LoginApp(timeManager,mypass, studentManager);

            String name=loginPage.logIn();
            if (name.equals("admin"))
            {
                adminPage.start();
            }
            else
            {
                StudentApp studentApp=new StudentApp(name, studentManager, courseManager, emailNotificationManager);
                studentApp.start();
            }
        

            

    }
}
