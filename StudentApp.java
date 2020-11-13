import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Interface that a student see after login 
 * Functionalities include: 
 * 1. Add Course Group 
 * 2. Drop Course Group 
 * 3. Check/Print Courses Registered 
 * 4. Check Vacancies Available
 * 5. Check Index Number of Course 
 * 6. Swop Index Number with Another Student 
 * 9. Quit
 * 
 * @author Wei Yao
 */
public class StudentApp {
	private Scanner scan = new Scanner(System.in);
	private Student loginStudent;
	private StudentManager stdmgr;
	private CourseManager crsmgr;
	private EmailNotificationManager emailNotificationManager;
	private Map<String, Course> availableCourse = new HashMap<String, Course>();

    public StudentApp(String userName,
                    StudentManager studentManager,
                    CourseManager courseManager,
                    EmailNotificationManager emailNotificationManager){
		stdmgr=studentManager;
		crsmgr=courseManager;
		this.emailNotificationManager=emailNotificationManager;
		loginStudent = stdmgr.getStudent(userName);
    }
    public void start(){
    	showMenu();
    	
    }
    /**
     * This method starts up the student app and displays all choices to admin
     * @author Wei Yao
     */
    public void showMenu() {
    	System.out.println("=================Student Page================");
        System.out.println("| 1. Add Course Group                       |");
        System.out.println("| 2. Drop Course Group                      |");
        System.out.println("| 3. Check/Print Courses Registered         |");
        System.out.println("| 4. Check Vacancies Available              |");
        System.out.println("| 5. Check Index Number of Course           |");
		System.out.println("| 6. Swop Index Number with Another Student |");
		System.out.println("| 7. View Confirmed Time Table              |");
        System.out.println("| 9. Quit                                   |");
        System.out.println("=============================================");
        System.out.print("Option: ");
        int option = 0;
        while(!scan.hasNextInt()) {
        	System.out.println("Please input numbers between 1 - 6 or 9 only.\nOption: ");
        }
        option = scan.nextInt();
        switch(option) {
        	case 1: addMenu();
				break;
				//Add Course Group
        	case 2: dropMenu();
				break;	
				//Drop Course Group
        	case 3: printMenu();
				break;
				//Check/Print Courses Registered
        	case 4: checkVacancies();
				break;
				//Check Vacancies Available
        	case 5: checkIndex();
				break;
				//Check Index Number of Course
        	case 6: swopIndex();
				break;
				//Swop Index Number with Another Student
			case 7: viewTimetable();
				break;
        	case 9: System.out.println("Exiting MyStars now....");
        		break;	
        }
        
    }
    /**
     * Menu for adding course.
     * 
     * @author Wei Yao
     */
    public void addMenu() {
    	System.out.println("=================Add Course Page================");
    	
    	int i = 1;
    	availableCourse = crsmgr.getCourseList(); //coursemanager method to retrieve all courses
    	String[] courseID = new String[availableCourse.size()];
    	for(Entry<String, Course> item: availableCourse.entrySet()) {
    		System.out.print("|"+i+". "+ item.getKey() +" " + item.getValue());
    		courseID[i-1] = item.getKey();
    		i++;
    	}
    	System.out.print("|99. Return to previous menu. ");
    	System.out.println("|Please select the course you wish to add:     |");
    	System.out.println("================================================");
    	System.out.print("Option: ");
        while(!scan.hasNextInt()) {
        	System.out.println("Please input numbers between 1 - "+ availableCourse.size()+" or 99 only.\nOption: ");
        }
        int option = scan.nextInt();
        if(option != 99) {
	        String selectedCourseID = availableCourse.get(courseID[option]).getcourseCode();
	        Map<String, CourseGroup> availableCG = crsmgr.getCourseGroup();
	        System.out.println("=================Add Course Group Page================"); 
	        i = 1;
	        String[] matchCG = new String[availableCG.size()];
	        for(Map.Entry<String, CourseGroup> item: availableCG.entrySet()) {
	        	CourseGroup check = item.getValue();
	        	if(check.getCourseCode() == selectedCourseID) {
	        		System.out.println("|"+i+". "+item.getKey() + " " + check.getIndexNumber() + " " + check.getLessons());
	        		matchCG[i-1] = item.getKey();    		
	        	}
	        	i++;
	        }
	        System.out.println("|Please select the course group you wish to add:     |");
	    	System.out.println("================================================");
	    	System.out.print("Option: ");
	        while(!scan.hasNextInt()) {
	        	System.out.println("Please input numbers between 1 - "+ availableCG.size()+" or 99 only.\nOption: ");
	        }
	        option = scan.nextInt();
	        if(option != 99) {
		        crsmgr.enrol(loginStudent, availableCG.get(matchCG[option]));
		        System.out.println("You have added course group: "+matchCG[option]);
		        addMenu();
	        }
        }else {
        	showMenu();
        }    	
    }
    /**
     * Menu for dropping course.
     * 
     * @author Wei Yao
     */
    public void dropMenu() {
    	System.out.println("|Please select the course group you wish to drop:     |");
    	System.out.println("================================================");
    	System.out.println("|Course Group \t Course Code");
    	Map<String, String> studentregcourse = loginStudent.getConfirmedCourseGroups();
    	String[] matchCG = new String[studentregcourse.size()];
    	int i = 1;
        for(Map.Entry<String, String> item: studentregcourse.entrySet()) {
        		System.out.println("|"+i+". "+item.getKey() + "\t" + item.getValue());
        		matchCG[i-1] = item.getKey();    		
        	i++;
        }    
        System.out.print("|99. Return to previous menu. ");
        System.out.print("Option: ");
        while(!scan.hasNextInt()) {
        	System.out.println("Please input numbers between 1 - "+ studentregcourse.size()+" or 99 only.\nOption: ");
        }
        int option = scan.nextInt();
        if(option != 99) {
        	dropCourseGroup(matchCG[option]);
        	System.out.print("You have dropped course group: "+matchCG[option]+".");
        	dropMenu();
        }else {
        	showMenu();
        }
    }
    
    /**
     * Drop from course group.
     * 
     * @author Wei Yao
     */
    private void dropCourseGroup(String courseGroup) {
		crsmgr.dropCourseGroup(courseGroup, loginStudent.getMatriculationNumber());  
		showMenu(); 	
    }
    /**
     * Print all registered courses.
     * 
     * @author Wei Yao
     */
    public void printMenu() {
		loginStudent.printRegisterCourse();
		showMenu();
    }
    /**
     * Menu for checking vacancy.
     * 
     * @author Wei Yao
     */
    public void checkVacancies() {
    	System.out.println("|These are the vacancies for all available course groups |");
    	System.out.println("==========================================================");
    	ArrayList<String[]> temp = crsmgr.getVacancies();
    	for(String[] item: temp) {
    		System.out.println("|"+item[0]+" has "+ item[1] + " vacancies available.");
    	}
    	System.out.println("==========================================================");
    	System.out.println("Press any key to return to previous menu.");
    	scan.next();
    	showMenu();
    }
    /**
     * Print out available course groups for a course.
     * 
     * @author Wei Yao
     */
    public void checkIndex() {
    	System.out.println("|These are the indexes for all available course groups     |");
    	System.out.println("==========================================================");
    	 Map<String, CourseGroup> courseGroups = crsmgr.getCourseGroup();
    	 for(Map.Entry<String, CourseGroup> item: courseGroups.entrySet()) {
    	    	System.out.println("|"+item.getValue().getIndexNumber()+"   "+ item.getValue().getLessons());	    	
    	 }
    	 System.out.println("==========================================================");
     	System.out.println("Press any key to return to previous menu.");
     	scan.next();
     	showMenu();
    }
    /**
     * Menu for swopping index with another student.
     * 
     * @author Wei Yao
     */
    public void swopIndex() {

    	System.out.println("================Swap Index Menu=========================");
    	System.out.println("|Please enter the course group you wish to swap to: ");
        String courseID = scan.next();
    	System.out.println("|Please enter matric number of the student you wish to swap with: ");
        String matric2 = scan.next();
        crsmgr.addSwap(courseID, loginStudent.getMatriculationNumber(), matric2);
    	System.out.println("|Your registered course group will be updated if successful.\nPress any key to return to previous menu.");
        scan.next();
        showMenu();
	}
	

	/**
	 * UI to view student's timetable 
	 * @author Wang Li Rong
	 */
	public void viewTimetable(){
		Object[] confirmedCourseGroupIndexes = this.loginStudent.getConfirmedCourseGroups().keySet().toArray();
		System.out.println("======================Time Table=========================");
		for (Object courseGroupIndex : confirmedCourseGroupIndexes) {
			CourseGroup courseGroup = this.crsmgr.getCourseGroup((String)courseGroupIndex);
			String courseCode = courseGroup.getCourseCode();
			String courseName = this.crsmgr.getCourseByCode(courseCode).getName();
			System.out.println(courseCode+"\t"+courseName);
			ArrayList<PeriodClass> lessons = courseGroup.getLessons();
			System.out.println("Day \t Time \t Lesson Type \t Location");
			for (PeriodClass lesson: lessons){
				System.out.println(lesson.getDayOfWeek()+"\t"+
								   Integer.toString(lesson.getStartTime())+"-"+Integer.toString(lesson.getEndTime())+"\t"+
								   lesson.getLocation());
			}
			System.out.println("---------------------------------------------------------");
			System.out.print("Press any key to continue to the next course...");
			scan.nextLine();
		}
		System.out.println("End of Time Table");
		System.out.println("Press any key to return to main menu");
		scan.nextLine();
		scan.nextLine();
		showMenu();
	}
}
