import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface that an admin see after login 
 * Functionalities include: 
 * 1. Adding and Editting Student Access Period 
 * 2. Adding students 
 * 3. Adding courses 
 * 4. Updating courses 
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
    PasswordManager passwordManager;
    
    public AdminApp(StudentManager studentManager,
                    CourseManager courseManager,
                    LoginTimeManager timeManager,
                    EmailNotificationManager emailNotificationManager,
                    PasswordManager passwordManager){
        this.studentManager = studentManager;
        this.courseManager = courseManager;
        this.timeManager = timeManager;
        this.emailNotificationManager = emailNotificationManager;
        this.passwordManager = passwordManager;
    }

    /**
     * This method starts up the admin app and displays all choices to admin
     * @author Wang Li Rong
     */
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
                System.out.println("Please Enter a Valid Integer.");
                sc.nextLine(); //clear buffer
                continue;
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
                    addStudent();
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
                    printStudentListByIndexNumber();
                    break;
                case 8:
                    //Print Student List By Course
                    printStudentListByCourse();
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
    /**
     * UI for adding student access period.
     * @author Wang Li Rong
     */
    private void addStudentAccessPeriod(){
        Scanner scan = new Scanner(System.in);
        List schools = (List<School>)java.util.Arrays.asList(School.values());
        int schoolChoice=-1;
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
                schoolChoice = scan.nextInt();
            } catch (Exception e){
                System.out.println("Please Enter a Valid Integer.");
                scan.nextLine(); //clear buffer
            }
            if (schoolChoice <= schools.size() && schoolChoice >=1) {
                progress1=true;
                break;
            } else if (schoolChoice != schools.size()+1){
                System.out.println("Please input an integer between 1-"+ (schools.size()+1));
            }
        } while (schoolChoice != schools.size()+1);

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
                date = scan.next();
                if (date.equals("b")){break;}

                //start time
                System.out.println("Choice of start time: (HH:MM)");
                time = scan.next();
                if (time.equals("b")){break;}

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
                date = scan.next();
                if (date.equals("b")){break;}

                //end time
                System.out.println("Choice of end time: (HH:MM)");
                time = scan.next();
                if (time.equals("b")){break;}

                //check correct format
                endDateTime = date + " " + time;
                df.parse(endDateTime);

                progress2=false;

            } catch (Exception e){
                System.out.println("Wrong Format of Input!");
            }
        }

        if (startDateTime != null && endDateTime != null 
            && schoolChoice<=schools.size()+1 && schoolChoice>=1){
            //timemanager.add(School.SCSE,"2020-10-19 16:00","2020-10-19 17:00");
            timeManager.add((School)schools.get(schoolChoice-1), startDateTime, endDateTime);
            this.timeManager.printAllAccessPeriod();
            System.out.println("--------------------------------");
        }    
    }

    /**
     * UI for editting student access period.
     * Similar to adding except only previously added schools can be chosen. 
     * @author Wang Li Rong
     */
    private void editStudentAccessPeriod(){
        Scanner scan = new Scanner(System.in);
        int schoolChoice=-1;
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
            
            System.out.print("Choice for School: ");
            try {
                schoolChoice = scan.nextInt();
            } catch (Exception e){
                System.out.println("Please Enter a Valid Integer.");
                scan.nextLine(); //clear buffer
            }

            if (schoolChoice <= schoolChoices.size() && schoolChoice >= 1) {
                progress1=true;
                break;
            } else if (schoolChoice != schoolChoices.size()+1){
                System.out.println("Please input an integer from 1-"+(schoolChoices.size()+1));
            }
        } while (schoolChoice != schoolChoices.size()+1);

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
                date = scan.next();
                if (date.equals("b")){break;}

                //start time
                System.out.println("Choice of start time: (HH:MM)");
                time = scan.next();
                if (time.equals("b")){break;}

                //check correct format
                startDateTime = date + " " + time;
                df.parse(startDateTime);

                progress2=true;
                progress1=false;

            } catch (Exception e){
                System.out.println("Wrong Format of Input!");
                scan.nextLine(); //clear buffer
            }
        }

        while (progress2) {
            try {
                //end date
                System.out.println("Choice of end date: (YYYY-MM-DD)");
                date = scan.next();
                if (date.equals("b")){break;}

                //end time
                System.out.println("Choice of end time: (HH:MM)");
                time = scan.next();
                if (time.equals("b")){break;}

                //check correct format
                endDateTime = date + " " + time;
                df.parse(endDateTime);

                progress2=false;

            } catch (Exception e){
                System.out.println("Wrong Format of Input!");
            }
        }

        if (startDateTime != null && endDateTime != null 
            && schoolChoice<=schoolChoices.size()+1 && schoolChoice>=1){
            //timemanager.add(School.SCSE,"2020-10-19 16:00","2020-10-19 17:00");
            timeManager.add((School)schoolChoices.get(schoolChoice-1), startDateTime, endDateTime);
            this.timeManager.printAllAccessPeriod();
            System.out.println("--------------------------------");
        }    
    }
    

    /**
     * UI for adding a new student.
     * @author Wang Li Rong
     */
    private void addStudent(){
        Scanner scan = new Scanner(System.in);
        //add to student manager

        System.out.println("Section 1/7");
        System.out.println("Enter -1 to return to menu");
        System.out.print("Student Matriculation Number: ");
        String matricNumber = scan.nextLine();
        //Goes back to main menu when student already exists
        if (matricNumber.equals("-1")){
            return;
        } else if (studentManager.studentExist(matricNumber)){
            System.out.println("Student already exists!");
            return;
        }
        System.out.println("========================================");
        System.out.println("Section 2/7");
        //Checks if student name is empty
        String name = "";
        while (true){
            System.out.println("Enter -1 to return to menu");
            System.out.print("Student Name: ");
            name = scan.nextLine();
            if (name.equals("-1")){
                return;
            }
            else if (!name.isEmpty()){
                break;
            } else {
                System.out.println("Please enter a valid name.");
            } 
        }
        System.out.println("========================================");
        System.out.println("Section 3/7");

        int genderInt;
        Gender gender;
        //checks if valid choice is chosen
        while (true){
            System.out.println("Enter -1 to return to menu");
            System.out.println("Student Gender: ");
            System.out.println("1. Male");
            System.out.println("2. Female");
            System.out.print("Gender (1/2): ");
            try {
                genderInt = scan.nextInt();
                if (genderInt==-1){
                    return;
                } else if (genderInt !=1 && genderInt !=2){
                    System.out.println("Please enter 1 or 2.");
                } else {
                    gender = genderInt==1? Gender.MALE : Gender.FEMALE;
                    break;
                }
            } catch (Exception e){
                System.out.println("Please Enter a Valid Integer.");
                scan.nextLine(); //clear buffer
            }
        }
        System.out.println("========================================");
        System.out.println("Section 4/7");
        
        scan.nextLine(); //clear buffer

        List schools = (List<School>)java.util.Arrays.asList(School.values());
        int schoolInt;
        School school;
        System.out.println("Enter -1 to return to menu");
        while (true) {
            System.out.println("Student School: ");
            for (int i=1;i<=schools.size();i++){
                System.out.println(i+". "+ schools.get(i-1).toString());
            }

            System.out.print("Choice for School: ");
            try {
                schoolInt = scan.nextInt();
                scan.nextLine(); //clear buffer
                if (schoolInt==-1){
                    return;
                } else if (schoolInt <= schools.size() && schoolInt >=1) {
                    school = (School)schools.get(schoolInt);
                    break;
                } else {
                    System.out.println("Please input an integer between 1-"+ schools.size());
                }
            } catch (Exception e){
                System.out.println("Please Enter a Valid Integer.");
                scan.nextLine(); //clear buffer
            }
        }
        
        System.out.println("========================================");
        System.out.println("Section 5/7");

        //Checks if nationality is empty
        String nationality="";
        while (true){
            System.out.println("Enter -1 to return to menu");
            System.out.print("Student Nationality: ");
            nationality = scan.nextLine();
            if (nationality.equals("-1")){
                return;
            } else if (!nationality.isEmpty()){
                break;
            } else {
                System.out.println("Please enter a valid nationality.");
            }
        }
        
        System.out.println("========================================");
        System.out.println("Section 6/7");
        
        //add to password manager
        String password="";
        while (true){
            System.out.println("Enter -1 to return to menu");
            System.out.print("Student Password: ");
            password = scan.nextLine();
            if (password.equals("-1")){
                return;
            } else if (!password.isEmpty()){
                break;
            } else {
                System.out.println("Please enter a valid password.");
            }
        }
        
        System.out.println("========================================");
        System.out.println("Section 7/7");

        //add to notification manger
        //reference https://blog.mailtrap.io/java-email-validation/
        String email="";
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        while (true){
            System.out.println("Enter -1 to return to menu");
            System.out.print("Student Email: ");
            email = scan.nextLine();
            Matcher matcher = pattern.matcher(email);
            if (email.equals("-1")){
                return;
            } else if (email.matches(regex)){
                break;
            } else {
                System.out.println("Please enter a valid email.");
            }
        }

        //add student to studentManager
        if (!name.isEmpty() && !matricNumber.isEmpty() && 
            school!=null && gender!=null && !nationality.isEmpty()){
                studentManager.addStudent(new Student(name, 
                matricNumber, 
                school, 
                gender, 
                nationality));
        } else{
            throw new RuntimeException("Particulars not filled up");
        }

        //add student to password manager
        if (!password.isEmpty()){
            passwordManager.add(matricNumber, password);
        }

        //add student to emailNotificationManager
        if (!email.isEmpty() && email.matches(regex)){
            emailNotificationManager.add(matricNumber, email);
        }

        System.out.println("========================================");

        studentManager.printAllRecord();
    }

    

    /**
     * UI for printing list of students by Index Number.
     * @author Wang Li Rong
     */
    private void printStudentListByIndexNumber(){
        String[] courseCodes = courseManager.getCourseList2();
        boolean progress1 = false; //progress to next stage or not
        boolean progress2 = false; //progress to next stage or not
        int courseInt=-1;
        int courseGroupInt=-1;
        Scanner scan = new Scanner(System.in);
        while (true){
            System.out.println("Courses: ");
            for (int i=0;i<courseCodes.length;i++){
                System.out.println((i+1)+".\t"+courseCodes[i]);
            }
            System.out.println((courseCodes.length+1)+".\tReturn back to menu");
            System.out.println("---------------------------------------");
            System.out.print("Choice of Course Code (1-"+(courseCodes.length+1)+"): ");
            try {
                courseInt=scan.nextInt();
                scan.nextLine();//clear buffer
                if (courseInt == courseCodes.length+1){
                    break;
                } else if (courseInt >courseCodes.length+1 && courseInt<1 ){
                    System.out.println("Please enter an integer between 1-"+(courseCodes.length+1));
                } else {
                    progress1 = true;
                    break;
                }
            } catch (Exception e){
                System.out.println("Please Enter a Valid Integer.");
                scan.nextLine(); //clear buffer
            }
        }
        ArrayList<String> courseGroups = null;
        while (progress1){
            String courseCode = courseCodes[courseInt-1];
            courseGroups = courseManager.getCourseGroupsOfCourse(courseCode);
            for (int i=0;i<courseGroups.size();i++){
                System.out.println((i+1)+".\t"+courseGroups.get(i));
            }
            System.out.println((courseGroups.size()+1)+".\tReturn back to menu");
            System.out.println("---------------------------------------");
            System.out.print("Choice of Course Group (1-"+(courseGroups.size()+1)+"): ");
            
            try {
                courseGroupInt=scan.nextInt();
                scan.nextLine();//clear buffer
                if (courseGroupInt == courseGroups.size()+1){
                    break;
                } else if (courseGroupInt >courseGroups.size()+1 && courseGroupInt<1 ){
                    System.out.println("Please enter an integer between 1-"+(courseGroups.size()+1));
                } else{
                    progress2=true;
                    break;
                }
            } catch (Exception e){
                System.out.println("Please Enter a Valid Integer.");
                scan.nextLine(); //clear buffer
            }
        }
        if (progress1 && progress2){
            String courseGroupIndex = courseGroups.get(courseGroupInt-1);
            CourseGroup courseGroup = courseManager.getCourseGroup(courseGroupIndex);
            ArrayList<String> studentMatricNumbers = courseGroup.getStudents();
            int i=1;
            System.out.println("Students in "+courseGroupIndex+" : ");
            for (String matricNumber: studentMatricNumbers){
                System.out.print(i+". ");
                studentManager.getStudent(matricNumber).printStudent();
                i++;
            }
            System.out.println("---------------------------------------");
        }
    }

    /**
     * UI for printing list of students by Course.
     * @author Wang Li Rong
     */
    private void printStudentListByCourse(){
        String[] courseCodes = courseManager.getCourseList2();
        boolean progress1 = false; //progress to next stage or not
        int courseInt=-1;
        Scanner scan = new Scanner(System.in);
        while (true){
            System.out.println("Courses: ");
            for (int i=0;i<courseCodes.length;i++){
                System.out.println((i+1)+".\t"+courseCodes[i]);
            }
            System.out.println((courseCodes.length+1)+".\tReturn back to menu");
            System.out.println("---------------------------------------");
            System.out.print("Choice of Course Code (1-"+(courseCodes.length+1)+"): ");
            try {
                courseInt=scan.nextInt();
                scan.nextLine();//clear buffer
                if (courseInt == courseCodes.length+1){
                    break;
                } else if (courseInt >courseCodes.length+1 && courseInt<1 ){
                    System.out.println("Please enter an integer between 1-"+(courseCodes.length+1));
                } else {
                    progress1 = true;
                    break;
                }
            } catch (Exception e){
                System.out.println("Please Enter a Valid Integer.");
                scan.nextLine(); //clear buffer
            }
        }
        
        if (progress1){
            String courseCode = courseCodes[courseInt-1];
            System.out.println("Students in "+courseCode+" : ");
            ArrayList<String> courseGroups =courseManager.getCourseGroupsOfCourse(courseCode);
            for (String courseGroupIndex: courseGroups){
                CourseGroup courseGroup = courseManager.getCourseGroup(courseGroupIndex);
                ArrayList<String> studentMatricNumbers = courseGroup.getStudents();
                int i=1;
                for (String matricNumber: studentMatricNumbers){
                    System.out.print(i+". ");
                    studentManager.getStudent(matricNumber).printStudent();
                    i++;
                }
            }
            
            System.out.println("---------------------------------------");
        }
    }
    

}

