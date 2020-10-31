import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Interface that an admin see after login 
 * Functionalities include: 
 * 1. Adding and Editting Student Access Period 
 * 2. Adding students 
 * 3. Adding courses 
 * 4.Updating courses 
 * 5. Checking index vacancy 
 * 6. Printing Student List By Index Number 
 * 7. Printing Student List By Course
 * 
 * @author Wang Li Rong
 */
public class AdminApp {
    StudentManager studentManager;
    CourseManager courseManager;
    LoginTimeManager timeManager;
    EmailNotificationManager emailNotificationManager;
    
    public AdminApp(StudentManager studentManager,
                    CourseManager courseManager,
                    LoginTimeManager timeManager,
                    EmailNotificationManager emailNotificationManager){
        this.studentManager = studentManager;
        this.courseManager = courseManager;
        this.timeManager = timeManager;
        this.emailNotificationManager = emailNotificationManager;
    }

    public void start(){
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do{
            // Choices
            System.out.println("================Admin Page===============");
            System.out.println("| 1. Add Student Access Period          |");
            System.out.println("| 2. Edit Student Access Period         |");
            System.out.println("| 3. Add Student                        |");
            System.out.println("| 4. Add Course                         |");
            System.out.println("| 5. Update Course                      |");
            System.out.println("| 6. Check Index Vacancy                |");
            System.out.println("| 7. Print Student List By Index Number |");
            System.out.println("| 8. Print Student List By Course       |");
            System.out.println("| 9. Quit                               |");
            System.out.println("=========================================");

            System.out.print("Option: ");
            try{
                choice = sc.nextInt();
            }
            catch (Exception e){
                System.out.println("Input must be an integer.");
            }

            switch (choice){
                case 1: 
                    //Add Student Access Period
                    addStudentAccessPeriod();
                    break;
                case 2:
                    //Edit Student Access Period 
                    editStudentAccessPeriod();
                    break;
                case 3:
                    //Add Student
                    break;
                case 4:
                    //Add Course
                    break;
                case 5:
                    //Update Course
                    break;
                case 6:
                    //Check Index Vacancy 
                    break;
                case 7:
                    //Print Student List By Index Number
                    break;
                case 8:
                    //Print Student List By Course
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Input must be between 1-9");
            }
        } while (choice != 9);
        
        sc.close();
        System.out.println("Logging out of account...");

    }

    public void addStudentAccessPeriod(){
        Scanner sc = new Scanner(System.in);
        List schools = (List<School>)java.util.Arrays.asList(School.values());
        int choice=-1;
        boolean progress1=false;
        boolean progress2=false;
        do {
            this.timeManager.printAllAccessPeriod();
            System.out.println("--------------------------------");

            System.out.println("Choices for Schools: ");
            for (int i=1;i<=schools.size();i++){
                System.out.println(i+". "+ schools.get(i-1).toString());
            }
            System.out.println((schools.size()+1)+". Return back to menu");
            System.out.println("--------------------------------");

            System.out.print("Choice for School: ");
            try {
                choice = sc.nextInt();
            } catch (Exception e){
                System.out.println("Please input an integer");
            }
            if (choice <= schools.size() && choice >=1) {
                progress1=true;
                break;
            } else if (choice != schools.size()+1){
                System.out.println("Please input an integer between 1-"+ schools.size()+1);
            }
        } while (choice != schools.size()+1);

        String date;
        String time;
        String startDateTime = null;
        String endDateTime = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        while (progress1){
            try {
                //start date
                System.out.println("Press b to go back");
                System.out.println("Choice of start date: (YYYY-MM-DD)");
                date = sc.next();
                if (date.equals("b")){break;}

                //start time
                System.out.println("Choice of start time: (HH:MM)");
                time = sc.next();
                if (date.equals("b")){break;}

                //check correct format
                startDateTime = date + " " + time;
                df.parse(startDateTime);

                progress2=true;
                progress1=false;

            } catch (Exception e){
                System.out.println("Wrong Format of Input!");
            }
        }

        while (progress2) {
            try {
                //end date
                System.out.println("Choice of end date: (YYYY-MM-DD)");
                date = sc.next();
                if (date=="b"){break;}

                //end time
                System.out.println("Choice of end time: (HH:MM)");
                time = sc.next();
                if (date=="b"){break;}

                //check correct format
                endDateTime = date + " " + time;
                df.parse(startDateTime);

                progress2=false;

            } catch (Exception e){
                System.out.println("Wrong Format of Input!");
            }
        }

        if (startDateTime != null && endDateTime != null 
            && choice<=schools.size()+1 && choice>=1){
            //timemanager.add(School.SCSE,"2020-10-19 16:00","2020-10-19 17:00");
            timeManager.add((School)schools.get(choice-1), startDateTime, endDateTime);
            this.timeManager.printAllAccessPeriod();
            System.out.println("--------------------------------");
        }    
    }

    public void editStudentAccessPeriod(){
        Scanner sc = new Scanner(System.in);
        int choice=-1;
        boolean progress1=false;
        boolean progress2=false;
        ArrayList<School> schoolChoices = this.timeManager.getSchoolsWithLoginPeriod();
        do {
            this.timeManager.printAllAccessPeriod();
            System.out.println("--------------------------------");
            
            for (int i=1;i<=schoolChoices.size();i++){
                System.out.println(i+". "+schoolChoices.get(i-1));
            }
            System.out.println((schoolChoices.size()+1)+". Return back to menu");
            System.out.println("--------------------------------");
            
            try {
                choice = sc.nextInt();
            } catch (Exception e){
                System.out.println("Please input an integer");
            }

            if (choice <= schoolChoices.size() && choice >= 1) {
                progress1=true;
                break;
            } else if (choice != schoolChoices.size()+1){
                System.out.println("Please input an integer from 1-"+(schoolChoices.size()+1));
            }
        } while (choice != schoolChoices.size()+1);

        String date;
        String time;
        String startDateTime = null;
        String endDateTime = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        while (progress1) {
            try {
                //start date
                System.out.println("Press b to go back");
                System.out.println("Choice of start date: (YYYY-MM-DD)");
                date = sc.next();
                if (date.equals("b")){break;}

                //start time
                System.out.println("Choice of start time: (HH:MM)");
                time = sc.next();
                if (date.equals("b")){break;}

                //check correct format
                startDateTime = date + " " + time;
                df.parse(startDateTime);

                progress2=true;
                progress1=false;

            } catch (Exception e){
                System.out.println("Wrong Format of Input!");
            }
        }

        while (progress2) {
            try {
                //end date
                System.out.println("Choice of end date: (YYYY-MM-DD)");
                date = sc.next();
                if (date=="b"){break;}

                //end time
                System.out.println("Choice of end time: (HH:MM)");
                time = sc.next();
                if (date=="b"){break;}

                //check correct format
                endDateTime = date + " " + time;
                df.parse(startDateTime);

                progress2=false;

            } catch (Exception e){
                System.out.println("Wrong Format of Input!");
            }
        }

        if (startDateTime != null && endDateTime != null 
            && choice<=schoolChoices.size()+1 && choice>=1){
            //timemanager.add(School.SCSE,"2020-10-19 16:00","2020-10-19 17:00");
            timeManager.add((School)schoolChoices.get(choice-1), startDateTime, endDateTime);
            this.timeManager.printAllAccessPeriod();
            System.out.println("--------------------------------");
        }    
    }
    
}

