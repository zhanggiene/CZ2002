import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

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
    
    public void showMenu() {
    	System.out.println("=================Student Page================");
        System.out.println("| 1. Add Course Group                       |");
        System.out.println("| 2. Drop Course Group                      |");
        System.out.println("| 3. Check/Print Courses Registered         |");
        System.out.println("| 4. Check Vacancies Available              |");
        System.out.println("| 5. Check Index Number of Course           |");
        System.out.println("| 6. Swop Index Number with Another Student |");
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
        	case 2: dropMenu();
        		break;	
        	case 3: printMenu();
        		break;
        	case 4: checkVacancies();
        		break;
        	case 5: checkIndex();
        		break;
        	case 6: swopIndex();
        		break;
        	case 9: System.out.println("Exiting MyStars now....");
        		break;	
        }
        
    }
    
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
        while(scan.hasNextInt()) {
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
	        while(scan.hasNextInt()) {
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
        while(scan.hasNextInt()) {
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
    
    //Updated by WY
    private void dropCourseGroup(String courseGroup) {
    	crsmgr.dropCourseGroup(courseGroup, loginStudent.getMatriculationNumber());   	
    }
    
    public void printMenu() {
    	loginStudent.printRegisterCourse();
    }
    
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
    
}
