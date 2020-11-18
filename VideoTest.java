import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class populates the data bases for the video
 * @author Wang Li Rong
 */

public class VideoTest {
    LoginTimeManager timeManager = new LoginTimeManager();
    StudentManager studentManager = new StudentManager();
    PasswordManager passwordManager = new PasswordManager();
    EmailNotificationManager emailNotificationManager = new EmailNotificationManager();
    CourseManager courseManager = new CourseManager();

    public static void main(String args[]) {
        VideoTest videoTest = new VideoTest();
        videoTest.addAccessPeriods();
        videoTest.addAllStudents();
        videoTest.addAllCourses();
        videoTest.addAllCourseGroups();
        videoTest.addAllLessons();
        videoTest.enrolAllStudents(); 
    }

    /**
     * Adds in All Students
     * 
     * @author Wang Li Rong
     */
    private void addAllStudents() {
        addOneStudent("Student1", "U1920001A", School.SCSE, Gender.MALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student2", "U1920002B", School.SCSE, Gender.MALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student3", "U1920003C", School.SCSE, Gender.MALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student4", "U1920004D", School.SCSE, Gender.MALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student5", "U1920005E", School.SCSE, Gender.MALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student6", "U1920006F", School.SCSE, Gender.MALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student7", "U1920007G", School.SCSE, Gender.FEMALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student8", "U1920008H", School.SCSE, Gender.FEMALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student9", "U1920009I", School.SPMS, Gender.FEMALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student10", "U1920010J", School.SPMS, Gender.FEMALE, "CHINESE", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student11", "U1920011K", School.SPMS, Gender.FEMALE, "SINGAPOREAN", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student12", "U1920012L", School.SPMS, Gender.FEMALE, "SINGAPOREAN", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student13", "U1920013M", School.SPMS, Gender.FEMALE, "SINGAPOREAN", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student14", "U1920014N", School.SPMS, Gender.FEMALE, "SINGAPOREAN", "NTUCZ2002A1@gmail.com");
        addOneStudent("Student15", "U1920015P", School.NBS, Gender.FEMALE, "SINGAPOREAN", "teamsynergy789@gmail.com");
        // For confirmation
        studentManager.printAllRecord();
    }

    /**
     * Adds in 1 student
     * 
     * @author Wang Li Rong
     */
    private void addOneStudent(String name, String matricNumber, School school, Gender gender, String nationality,
            String email) {
        Student student = new Student(name, matricNumber, school, gender, nationality);
        studentManager.addStudent(student);
        passwordManager.add(student.getMatriculationNumber(), "student");
        emailNotificationManager.add(student.getMatriculationNumber(), email);
    }

    /**
     * Add access periods
     * 
     * @author Wang Li Rong
     */
    private void addAccessPeriods() {
        timeManager.add(School.SCSE, "2020-11-16 09:00", "2020-11-17 09:00");
        timeManager.add(School.SPMS, "2020-11-10 09:00", "2020-11-30 09:00");
        timeManager.add(School.NBS, "2020-11-10 09:00", "2020-11-30 09:00");
        // For confimation
        timeManager.printAllAccessPeriod();
    }

    /**
     * Add all Courses
     * 
     * @author Wang Li Rong
     */
    private void addAllCourses() {
        // CZ2002
        addOneCourse("CZ2002", "OODP", School.SCSE);
        // CZ2001
        addOneCourse("CZ2001", "Algorithms", School.SCSE);
        // LS9001
        addOneCourse("LS9001", "Spanish", School.NBS);
        // MH2500
        addOneCourse("MH2500", "Statistics", School.SPMS);
        // BU8401
        addOneCourse("BU8401", "Business Management", School.NBS);

        // Save changes
        courseManager.save();
        // For confirmation
        courseManager.printAllRecord();
    }

    /**
     * Add all CoursesGroups
     * 
     * @author Wang Li Rong
     */
    private void addAllCourseGroups() {
        // CZ2002
        // 10401
        addOneIndexToCourse("10401", "CZ2002");
        // 10402
        addOneIndexToCourse("10402", "CZ2002");

        // CZ2001
        // 13801
        addOneIndexToCourse("13801", "CZ2001");

        // LS9001
        // 13008
        addOneIndexToCourse("13008", "LS9001");

        // MH2500
        // 10197
        addOneIndexToCourse("10197", "MH2500");
        // 10198
        addOneIndexToCourse("10198", "MH2500");

        // BU8401
        // 19352
        addOneIndexToCourse("19352", "BU8401");

        // Save changes
        courseManager.save();
        // For confirmation
        courseManager.printAllRecord();
    }

    /**
     * Add all Lessons
     * 
     * @author Wang Li Rong
     */
    private void addAllLessons() {
        // CZ2002
        // Lessons of 10401
        addOneLessonToIndex(TypeOfLesson.LABORATORY, 1, 830, 1030, "SPL", "10401");
        addOneLessonToIndex(TypeOfLesson.TUTORIAL, 1, 1130, 1230, "TR+33", "10401");
        addOneLessonToIndex(TypeOfLesson.LECTURE, 5, 1130, 1230, "ONLINE", "10401");
        // Lessons of 10402
        addOneLessonToIndex(TypeOfLesson.TUTORIAL, 1, 1030, 1130, "TR+33", "10402");
        addOneLessonToIndex(TypeOfLesson.LECTURE, 5, 1130, 1230, "ONLINE", "10402");

        // CZ2001
        // Lessons of 13801
        addOneLessonToIndex(TypeOfLesson.LECTURE, 1, 1230, 1330, "ONLINE", "13801");
        addOneLessonToIndex(TypeOfLesson.TUTORIAL, 3, 1130, 1230, "TR+37", "13801");

        // LS9001
        // Lessons of 13008
        addOneLessonToIndex(TypeOfLesson.TUTORIAL, 2, 1530, 1700, "CSKL16", "13008");
        addOneLessonToIndex(TypeOfLesson.TUTORIAL, 4, 1530, 1700, "CSKL16", "13008");

        // MH2500
        // Lessons of 10197
        addOneLessonToIndex(TypeOfLesson.TUTORIAL, 4, 1330, 1430, "TR+24", "10197");
        addOneLessonToIndex(TypeOfLesson.LECTURE, 5, 830, 1030, "ONLINE", "10197");
        // Lessons of 10198
        addOneLessonToIndex(TypeOfLesson.TUTORIAL, 4, 1030, 1130, "TR+24", "10198");
        addOneLessonToIndex(TypeOfLesson.LECTURE, 5, 830, 1030, "ONLINE", "10198");

        // BU8401
        addOneLessonToIndex(TypeOfLesson.TUTORIAL, 4, 1030, 1330, "TR+20", "19352");

        // Save changes
        courseManager.save();
        // For confirmation
        showAllLessons();
    }


    /**
     * Adds 1 Course
     * 
     * @param courseCode
     * @param name
     * @param school
     * @author Wang Li Rong
     */
    private void addOneCourse(String courseCode, String name, School school) {
        Course course = new Course(courseCode, name, school, 4);
        courseManager.addCourse(course);
    }

    /**
     * Adds 1 Index to Course
     * 
     * @param index
     * @param courseCode
     * @author Wang Li Rong
     */
    private void addOneIndexToCourse(String index, String courseCode) {
        // must always add the course before the index
        CourseGroup courseGroup = new CourseGroup(index, 10, courseCode);
        courseManager.getCourseByCode(courseCode).addCourseGroup(index);
        courseManager.addCourseGroup(courseGroup);
    }

    /**
     * Adds 1 Lesson to Index
     * 
     * @param typeOfLesson
     * @param weekday
     * @param start
     * @param end
     * @param location
     * @param index
     * @author Wang Li Rong
     */
    private void addOneLessonToIndex(TypeOfLesson typeOfLesson, int weekday, int start, int end, String location,
            String index) {
        PeriodClass lesson = new PeriodClass(typeOfLesson, weekday, start, end, location);
        courseManager.getCourseGroup(index).addLesson(lesson);
    }

    private void showAllLessons() {
        for (String courseCode : courseManager.getCourseCodeList()) {
            ArrayList<String> courseGroups = courseManager.getCourseGroupsOfCourse(courseCode);
            for (Object courseGroupIndex : courseGroups) {
                CourseGroup courseGroup = this.courseManager.getCourseGroup((String)courseGroupIndex);
                String courseName = this.courseManager.getCourseByCode(courseCode).getName();
                System.out.println(courseCode+"\t"+courseName+"\t"+courseGroupIndex);
                ArrayList<PeriodClass> lessons = courseGroup.getLessons();
                System.out.println("Day \t Time \t Lesson Type \t Location");
                for (PeriodClass lesson: lessons){
                    System.out.println(lesson.getDayOfWeek()+"\t"+
                                       Integer.toString(lesson.getStartTime())+"-"+Integer.toString(lesson.getEndTime())+"\t"+
                                       lesson.typeOfLesson + "\t"+ lesson.getLocation());
                }
                System.out.println("---------------------------------------------------------");
            }
        }
        
    }

    private void enrolAllStudents(){
        //CZ2002 10401
        List<String> matricNumberList = Arrays.asList("U1920001A","U1920002B", "U1920003C", "U1920004D"
        , "U1920005E", "U1920006F", "U1920007G", "U1920008H", "U1920009I", "U1920010J"
        , "U1920011K", "U1920012L", "U1920013M", "U1920014N", "U1920015P");
        for (String matricNumber : matricNumberList.subList(0, 10)){
            enrolOneStudent(matricNumber, "10401", "CZ2002");
        }
        //CZ2002 10402        
        for (String matricNumber : matricNumberList.subList(10, 15)){
            enrolOneStudent(matricNumber, "10402", "CZ2002");
        }
        //CZ2001 13801
        for (String matricNumber : matricNumberList.subList(6, 14)){
            enrolOneStudent(matricNumber, "13801", "CZ2001");
        }
        //LS9001 13008
        for (String matricNumber : matricNumberList.subList(2, 10)){
            enrolOneStudent(matricNumber, "13008", "LS9001");
        }
        //MH2500 10197
        for (String matricNumber : matricNumberList.subList(0, 10)){
            enrolOneStudent(matricNumber, "10197", "MH2500");
        }
        //MH2500 10198
        for (String matricNumber : matricNumberList.subList(12, 14)){
            enrolOneStudent(matricNumber, "10198", "MH2500");
        }
        //BU8401 19352
        for (String matricNumber : matricNumberList.subList(4, 10)){
            enrolOneStudent(matricNumber, "19352", "BU8401");
        }

        checkAllStudents();

        studentManager.save();
        courseManager.save();
        
    }

    private void enrolOneStudent(String matricNumber, String courseGroupIndex, String courseCode){
        //add the courseGroup to the student
        Student student = studentManager.getStudent(matricNumber);
        student.addToCourseGroups(courseGroupIndex, courseCode);
        //add the student to the courseGroup
        CourseGroup courseGroup = courseManager.getCourseGroup(courseGroupIndex);
        courseGroup.enrol(matricNumber);
    }

    private void checkAllStudents(){
        for (String courseCode : courseManager.getCourseCodeList()) {
            ArrayList<String> courseGroups = courseManager.getCourseGroupsOfCourse(courseCode);
            for (Object courseGroupIndex : courseGroups) {
                CourseGroup courseGroup = this.courseManager.getCourseGroup((String)courseGroupIndex);
                String courseName = this.courseManager.getCourseByCode(courseCode).getName();
                System.out.println(courseCode+"\t"+courseName+"\t"+courseGroupIndex);
                ArrayList<String> students = courseGroup.getStudents();
                System.out.println("Matriculation Number");
                System.out.println(students);
                System.out.println("---------------------------------------------------------");
            }
        }
    }




    
}
