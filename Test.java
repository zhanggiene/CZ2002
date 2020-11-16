/**
 * This class populates the database for testing
 * @author Wang Li Rong
 */

public class Test {
    LoginTimeManager timeManager = new LoginTimeManager();
    StudentManager studentManager = new StudentManager();
    PasswordManager passwordManager = new PasswordManager();
    EmailNotificationManager emailNotificationManager = new EmailNotificationManager();
    CourseManager courseManager = new CourseManager();

    public static void main(String args[]){
        Test test = new Test();
        
        test.checkpoint1();
    }

    /**
     * Populates database for functions 1-4 for AdminApp
     */
    private void checkpoint1(){
        //1. Add student access period
        timeManager.add(School.SCSE, "2020-11-01 10:00", "2021-11-01 10:00");
        timeManager.add(School.MSE, "2020-11-01 10:00", "2021-11-01 10:00");
        // For confimation
        timeManager.printAllAccessPeriod();

        //2. Edit student access period
        System.out.println("After editing...");
        timeManager.add(School.SCSE, "2020-11-01 10:00", "2021-12-01 10:00");
        // For confirmation
        timeManager.printAllAccessPeriod();

        //TODO: After running this, only the first student gets recorded in emailNotificationManager and passWordManager
        //3. Add student
        //Adds first student to studentmanager, passwordManager and EmailNotificationManager
        Student student1 = new Student("studentname1", "U1234567B", 
                                        School.SCSE, Gender.MALE, "chinese");
        studentManager.addStudent(student1);
        passwordManager.add(student1.getMatriculationNumber(), "student");
        emailNotificationManager.add(student1.getMatriculationNumber(), "NTUCZ2002A1@gmail.com");
        //Adds second student to studentmanager, passwordManager and EmailNotificationManager
        Student student2 = new Student("studentname2", "U7654321B", 
                                        School.SCSE, Gender.MALE, "chinese");
        studentManager.addStudent(student2);
        passwordManager.add(student2.getMatriculationNumber(), "student");
        emailNotificationManager.add(student2.getMatriculationNumber(), "NTUCZ2002A1@gmail.com");

        // For confirmation
        studentManager.printAllRecord();

        //4. Add Course
        //a) Add course1 to courseManager
        Course course1 = new Course("CZ2002", "OODP", School.SCSE, 4);
        courseManager.addCourse(course1);
        

        //b) Add courseGroup1 to courseManager and course1
        CourseGroup courseGroup1 = new CourseGroup("DSAI1", 25, "CZ2002");
        course1.addCourseGroup("DSAI1");
        courseManager.addCourseGroup(courseGroup1);

        // For confirmation
        courseManager.printAllRecord();

        //c) Add lesson
        // Add lesson1
        PeriodClass lesson1 = new PeriodClass(TypeOfLesson.LECTURE, 1, 13, 14, "lt23");
        courseGroup1.addLesson(lesson1);
        // Add lesson2
        PeriodClass lesson2 = new PeriodClass(TypeOfLesson.TUTORIAL, 2, 13, 14, "lt26C");
        courseGroup1.addLesson(lesson2);

        courseManager.save();

        // For confirmation
        courseGroup1.printInfo();
    }
}
