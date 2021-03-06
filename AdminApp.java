import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface that an admin see after login 
 * <li>Functionalities include: 
 * <li> 1. Adding and Editting Student Access Period 
 * <li>2. Adding students 
 * <li>3. Adding courses 
 * <li>4. Updating courses 
 * <li>5. Checking index vacancy 
 * <li>6. Printing Student List By Index Number 
 * <li>7. Printing Student List By Course
 * 
 * @author Wang Li Rong and Andrew
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
            System.out.println("| 4. Add Course/Indexes                 |");
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
                    // Edit Student Access Period
                    editStudentAccessPeriod();
                    break;
                case 3:
                    // Add Student
                    addStudent();
                    break;
                case 4:
                    // Add Course
                    addCourse();
                    break;
                case 5:
                    // Update Course
                    updateCourse();
                    break;
                case 6:
                    // Check Index Vacancy
                    checkVacancy();
                    break;
                case 7:
                    // Print Student List By Index Number
                    printStudentListByIndexNumber();
                    break;
                case 8:
                    // Print Student List By Course
                    printStudentListByCourse();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Input must be between 1-9");
            }
        } while (choice != 9);

        sc.close();
        System.out.println("Exiting MyStars now....");

    }

    /**
     * UI for adding student access period.
     * @author Wang Li Rong
     */
    private void addStudentAccessPeriod() {
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
                    school = (School)schools.get(schoolInt-1);
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
     * UI for adding a new course.
     * @author Andrew Wiraatmaja
     */

    private void addCourse(){
        System.out.println("ADDING NEW COURSE/INDEX");
        System.out.println("==========================");
        Scanner scan = new Scanner(System.in);
        
        int myChoice;
        
        while(true){
            System.out.println("1. Add new courses");
            System.out.println("2. Add new index for existing courses");
            System.out.print("Your choice (1/2) :");
            try {
                int choice = scan.nextInt();
                if (choice !=1 && choice !=2){
                    System.out.println("Please enter 1 or 2.");
                } else{
                    myChoice = choice;
                    break; 
                }
            } catch (Exception e){
                System.out.println("Please enter a valid integer.");
            }
        }

        if (myChoice == 1){
            System.out.println("Section 1/5");
            System.out.print("Enter Course Code : ");
            scan.nextLine();
            String courseCode = scan.nextLine();
            if (courseManager.courseExist(courseCode)){
                System.out.println("Course already exists!");
                return;
            }

            System.out.println("========================================");
            System.out.println("Section 2/5");
            List schools = (List<School>)java.util.Arrays.asList(School.values());
            int schoolInt;
            School school;
            while (true) {
                System.out.println("Course School: ");
                for (int i=1;i<=schools.size();i++){
                    System.out.println(i+". "+ schools.get(i-1).toString());
                }

                System.out.print("Enter Choice for School: ");
                try {
                    schoolInt = scan.nextInt();
                    if (schoolInt <= schools.size() && schoolInt >=1) {
                        school = (School)schools.get(schoolInt-1);
                        break;
                    } else {
                        System.out.println("Please input an integer between 1-"+ schools.size()+1);
                    }
                } catch (Exception e){
                    System.out.println("Please input an integer");
                    scan.nextLine();
                }
            }
            scan.nextLine();

            System.out.println("========================================");
            System.out.println("Section 3/5");
            String courseName = "";
            while (true){
                System.out.print("Enter Course Name: ");
                courseName = scan.nextLine();
                if (!courseName.isEmpty()){
                    break;
                } else {
                    System.out.println("Please enter a valid course name.");
                }
            }
            
            System.out.println("========================================");
            System.out.println("Section 4/5");
            int numAU = 3;
            while (true) {
                System.out.print("Enter Number of AU: ");
                try {
                    numAU = scan.nextInt();
                    break;
                } catch (Exception e){
                    System.out.println("Please input an integer");
                    scan.nextLine();
                }
            }
            scan.nextLine();

            if (school!=null && !courseCode.isEmpty() && !courseName.isEmpty()){
                Course course = new Course(courseCode,courseName, school,numAU);    
                courseManager.addCourse(course);
            } else{
                throw new RuntimeException("Particulars not filled up");
            }

            System.out.println("Section 5/5");
            addIndex(courseCode);

            courseManager.printAllRecord();
        }
        else if (myChoice == 2){
            System.out.println("Enter course code:");
            scan.nextLine();
            String courseCode = scan.nextLine();
            // check whether course code is already exists
            if (!courseManager.courseExist(courseCode)){
                System.out.println("Course not exists!");
                return;
            } else{
                addIndex(courseCode);
            }
            courseManager.printAllRecord();
        }

    }

    /**
     * UI for adding a new index.
     * @author Andrew Wiraatmaja
     */

    public void addIndex(String courseCode){
        Scanner scan = new Scanner(System.in);
        System.out.println("ADDING NEW INDEX");
        System.out.println("=========================");

        String indexNum = "";
        while (true){
            System.out.print("Enter the index number: ");
            indexNum = scan.nextLine();
            if (!indexNum.isEmpty()){
                break;
            } else {
                System.out.println("Please enter a valid index number.");
            }
        }

        System.out.print("Enter the vacancies: ");
        int vacance = 1;
        try{
            vacance = scan.nextInt();
        } catch(Exception e){
            System.out.println("Please enter a valid integer.");
        }

        CourseGroup courseNum;
        if (!indexNum.isEmpty() && vacance > 0){
            courseNum = new CourseGroup(indexNum,vacance, courseCode);
            courseManager.addCourseGroup(courseNum);
            Course c = courseManager.getCourseByCode(courseCode);   
            c.addCourseGroup(indexNum);
        } else{
            throw new RuntimeException("Particulars not filled up");
        }

        boolean adding = true;
        while(adding){
            System.out.println("Select which lesson available");
            System.out.println("1. Lecture");
            System.out.println("2. Tutorial");
            System.out.println("3. Lab");
            System.out.println("Your choice (Enter -1 to stop adding lesson) : ");
            int lessInt = scan.nextInt();
            if(lessInt == -1){
                adding = false;
            }else{
                TypeOfLesson type = TypeOfLesson.LECTURE; 
                if(lessInt == 1){
                    type = TypeOfLesson.LECTURE;
                } else if(lessInt == 2){
                    type = TypeOfLesson.TUTORIAL;
                }else if(lessInt == 3){
                    type = TypeOfLesson.LABORATORY;
                }
                scan.nextLine();

                int day = 1;
                System.out.print("Select what day (Monday 1,... Friday 5): ");
                try{
                    day = scan.nextInt();
                } catch(Exception e){
                    System.out.println("Please enter a valid integer.");
                }
                scan.nextLine();

                int start = 0;
                System.out.print("Select start time (HHMM 24 HOUR FORMAT): ");
                try{
                    start = scan.nextInt();
                } catch(Exception e){
                    System.out.println("Please enter a valid integer.");
                }
                scan.nextLine();
                
                int end = 0;
                System.out.print("Select end time (HHMM 24 HOUR FORMAT): ");
                try{
                    end = scan.nextInt();
                } catch(Exception e){
                    System.out.println("Please enter a valid integer.");
                }
                scan.nextLine();
                
                String loc = "";
                while (true){
                    System.out.print("Select location : ");
                    loc = scan.nextLine();
                    if (!loc.isEmpty()){
                        break;
                    } else {
                        System.out.println("Please enter a valid course name.");
                    }
                }
                
                PeriodClass period;
                if (day > 0 && day <= 5 && start >= 0 && start < 2400 && end >= 0 && end < 2400 && !loc.isEmpty()){
                    period = new PeriodClass(type,day,start,end,loc);
                    courseNum.addLesson(period);
                    courseManager.save();
                } else{
                    throw new RuntimeException("Particulars not filled up");
                }
                
            }
        }


    }

    /**
     * UI for updating a course.
     * @author Andrew Wiraatmaja
     */

    public void updateCourse(){
        Scanner scan = new Scanner(System.in);
        String Schoice;
        do{
            courseManager.printAllRecord();
            System.out.println("Enter course code you want to update (Enter exit to quit): ");
            Schoice = scan.nextLine();
            Course c;
            if(!Schoice.equals("exit")){
                if (courseManager.getCourseByCode(Schoice)==null){
                    System.out.println("Please enter valid course code!");
                    break;
                } else{
                    c=courseManager.getCourseByCode(Schoice);
                }
            }else{
                break;
            }
            
            
            System.out.println("Select which particular you want to update");
            System.out.println("1. Course Code");
            System.out.println("2. Course Name");
            System.out.println("3. School");
            System.out.println("4. Index Specific");
            System.out.print("Your choice :");
            int choice2 = scan.nextInt();

            if(choice2 == 1){
                String newCode = "";
                while (true){
                    scan.nextLine();
                    System.out.print("Enter the new course code: ");
                    newCode = scan.nextLine();
                    if (!newCode.isEmpty()){
                        break;
                    } else {
                        System.out.println("Please enter a valid course code.");
                    }
                }
                System.out.println(c.getcourseCode());
                updateCourseCodesOfAllStudents(c.getcourseCode(), newCode);
            }
            else if(choice2 == 2){
                String newName = "";
                while (true){
                    scan.nextLine();
                    System.out.print("Enter the new course name: ");
                    newName = scan.nextLine();
                    if (!newName.isEmpty()){
                        break;
                    } else {
                        System.out.println("Please enter a valid course name.");
                    }
                }
                c.setCourseName(newName);
                courseManager.save();
            }
            else if(choice2 == 3){
                List schools = (List<School>)java.util.Arrays.asList(School.values());
                int schoolInt;
                School school;
                while (true) {
                    System.out.println("Course School: ");
                    for (int i=1;i<=schools.size();i++){
                        System.out.println(i+". "+ schools.get(i-1).toString());
                    }
                    System.out.print("Enter Choice for new School: ");
                    try {
                        schoolInt = scan.nextInt();
                        if (schoolInt <= schools.size() && schoolInt >=1) {
                            school = (School)schools.get(schoolInt-1);
                            break;
                        } else {
                            System.out.println("Please input an integer between 1-"+ schools.size()+1);
                        }
                    } catch (Exception e){
                        System.out.println("Please input an integer");
                        scan.nextLine();
                    }
                }
                c.setSchool(school);
                courseManager.save();
                scan.nextLine();
            }
            else if(choice2 == 4){
                System.out.println("Select which index you want to change");
                ArrayList<String> cg = c.getCourseGroups();
                for(int j=1;j<=cg.size();j++){
                    System.out.println(j + ". " +  cg.get(j-1));
                }
                System.out.print("\n");
                System.out.println("Your choice (1-" + cg.size() +  "): ");
                int cgInt = scan.nextInt();
                String cgString = cg.get(cgInt-1);
                CourseGroup cgIndex = courseManager.getCourseGroup(cgString);
                cgIndex.printInfo();
                int indexInt;
                do{
                    System.out.println("What do you want to change");
                    System.out.println("1. Index Number");
                    System.out.println("2. Size");
                    System.out.print("Your choice: ");
                    indexInt = scan.nextInt();
                    if(indexInt == 1){
                        String newNumber = "";
                        while (true){
                            scan.nextLine();
                            System.out.print("Enter the new index number: ");
                            newNumber = scan.nextLine();
                            if (!newNumber.isEmpty()){
                                break;
                            } else {
                                System.out.println("Please enter a valid index number.");
                            }
                        }
                        
                        updateIndexesOfAllStudents(cgIndex.getIndexNumber(), newNumber);
                        cgIndex.setIndexNumber(newNumber);
                        cg.set(cgInt-1,newNumber);
                        courseManager.save();
                    }
                    else if(indexInt == 2){
                        ArrayList<String> cgStud = cgIndex.getStudents();
                        int newVacancy = 10;
                        try {
                            System.out.println("Enter new size: ");
                            newVacancy = scan.nextInt();
                            if(newVacancy < cgStud.size()){
                                System.out.println("Number of vacancy must exceed number of student");
                            }
                            else{
                                cgIndex.setTotalSize(newVacancy);
                                courseManager.save();
                            }
                        } catch (Exception e){
                            System.out.println("Please enter a valid integer.");
                        }    
                    } else if(indexInt == -1){
                        break;
                    }
                }while(indexInt != -1);
                scan.nextLine();
            } 
    }while(!Schoice.equals("exit"));

        }

    /**
     * UI for checking vacancies.
     * @author Andrew Wiraatmaja
     */

    public void checkVacancy(){
        String [] courseCodes = courseManager.getCourseCodeList();
        Scanner scan = new Scanner(System.in);
        courseManager.printAllRecord();
        System.out.println("Enter which course you want to check: ");
        int choice = scan.nextInt();
        String courseCode = courseCodes[choice -1];
        Course c = courseManager.getCourseByCode(courseCode);
        ArrayList<String> cg = c.getCourseGroups();
        System.out.println("Select which index you want to check the vacancy: ");
        for(int j=0;j<cg.size();j++){
            System.out.println((j+1)+ ". " + cg.get(j));
        }
        System.out.println("Your choice (1-" + cg.size() +  "): ");
        int choice2;
        try{
            choice2 = scan.nextInt();
            if(choice2>=1 && choice2 <= cg.size()){
                String courseGroup = cg.get(choice2-1);
                CourseGroup courseNum = courseManager.getCourseGroup(courseGroup);
                System.out.println("Vacancies available : "+ courseNum.getVacancy() +"/" + courseNum.getTotalSize());
            } else{
                System.out.println("Please enter within range");
            }
        } catch(Exception e){
            System.out.println("Please enter valid integer");
        }
    }

    

    /**
     * UI for printing list of students by Index Number.
     * @author Wang Li Rong
     */
    private void printStudentListByIndexNumber(){
        String[] courseCodes = courseManager.getCourseCodeList();
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
            System.out.println("\tName     \tGender\tNationality\tSchool");
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
        String[] courseCodes = courseManager.getCourseCodeList();
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
            System.out.println("\tName     \tGender\tNationality\tSchool");
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

    /**
     * This function updates the index number for all students in the index 
     * as well as updates the index of the course group in course manager
     * @param oldCourseGroupIndex
     * @param newCourseGroupIndex
     * @author Wang Li Rong
     */
    private void updateIndexesOfAllStudents(String oldCourseGroupIndex, String newCourseGroupIndex){
        CourseGroup courseGroup = courseManager.getCourseGroup(oldCourseGroupIndex);
        for (String matricNumber: courseGroup.getStudents()){
            studentManager.getStudent(matricNumber).setCourseGroup(oldCourseGroupIndex,newCourseGroupIndex);
        }
        studentManager.save();
        courseGroup.setIndexNumber(newCourseGroupIndex);
        courseManager.updateCourseGroup(courseGroup, oldCourseGroupIndex , newCourseGroupIndex);
    }

    /**
     * This method updates the course codes of all students in the course
     * as well as updates the course of the course in course manager
     * @param oldCourseCode
     * @param newCourseCode
     * @author Wang Li Rong
     */
    private void updateCourseCodesOfAllStudents(String oldCourseCode, String newCourseCode){
        Course course = courseManager.getCourseByCode(oldCourseCode);
        for (String courseGroup : courseManager.getCourseGroupsOfCourse(oldCourseCode)){
            ArrayList<String> students = courseManager.getCourseGroup(courseGroup).getStudents();
            for (String matricNumber: students){
                Student student = studentManager.getStudent(matricNumber);
                student.setCourseCode(courseGroup, newCourseCode);
            }
        }
        studentManager.save();
        course.setCourseCode(newCourseCode);
        courseManager.updateCourse(course, oldCourseCode, newCourseCode);
        courseManager.save();
    }

    

}

