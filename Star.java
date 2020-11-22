

import java.io.Console;
import java.util.Date;
import java.util.Scanner;

/**
     it serves as a starting point of the whole program.
     * @author zhang zhuyan
     * @return the string of user account after checking it is valid(be it admin or student)
     */

public class Star {

    

        public static void main(String[] args) {
    
            PasswordManager mypass=new PasswordManager();
            StudentManager studentManager=new StudentManager();
            LoginTimeManager timeManager=new LoginTimeManager();
            CourseManager courseManager=new CourseManager();
            EmailNotificationManager emailNotificationManager=new EmailNotificationManager();
            AdminApp adminPage = new AdminApp(studentManager,courseManager, timeManager,mypass,emailNotificationManager);
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
