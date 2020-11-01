import java.util.Scanner;

public class StudentApp {
    public StudentApp(String userName,
                    StudentManager studentManager,
                    CourseManager courseManager,
                    EmailNotificationManager emailNotificationManager){

    }
    public void start(){
        //testing code
        System.out.println("In student app");
        Scanner scan = new Scanner(System.in);
        System.out.print("Press 1 to exit: ");
        scan.nextInt();
    }
}
