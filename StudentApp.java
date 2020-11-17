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
		System.out.println("| 7. View Confirmed Time Table              |");
        System.out.println("| 99. Quit                                   |");
        System.out.println("=============================================");
        System.out.print("Option: ");
		int option = 0;
        while(!scan.hasNextInt()) {
			System.out.println("Please input numbers between 1 - 6 or 99 only.\nOption: ");
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
				//Swop Index Number with Another Student
			case 7: viewTimetable();
				break;
        	case 99: System.out.println("Exiting MyStars now....");
				break;	
			default: System.out.println("Please input numbers between 1 - 6 or 99 only.\nOption: ");
				showMenu();;
				break;
        }
        
    }
    
    public void addMenu() {
    	System.out.println("=================Add Course Page================");
    	int option = 0;
		int i = 1;
		int totalAU = 0;
		ArrayList<String> confirmedCourse = new ArrayList<String> (loginStudent.getConfirmedCourseGroups().values());
		for(i=i-1; i<confirmedCourse.size(); i++){
			Course currentcourse = crsmgr.getCourseByCode(confirmedCourse.get(i));
			if(currentcourse != null){
				int confirmedCourseAU = currentcourse.getCourseAU();
				totalAU += confirmedCourseAU;
			}			
		}
		i = 1;
    	availableCourse = crsmgr.getCourseList(); //coursemanager method to retrieve all courses
    	String[] courseID = new String[availableCourse.size()];
    	for(Entry<String, Course> item: availableCourse.entrySet()) {
    		System.out.println("|"+i+". "+ item.getKey() +" " + item.getValue().getName());
    		courseID[i-1] = item.getKey();
    		i++;
    	}
    	System.out.println("|99. Return to previous menu. ");
    	System.out.println("|Please select the course you wish to add:     |");
    	System.out.println("================================================");
    	System.out.print("Option: ");
         	try{
				option = scan.nextInt();
			}catch(Exception e){
				System.out.println("Please input numbers between 1 - "+ availableCourse.size()+" or 99 only.\nOption: ");
				option = scan.nextInt();
			}
        if(option != 99 && (option > 0 && option <= availableCourse.size())) {
	        String selectedCourseID = availableCourse.get(courseID[option-1]).getcourseCode();
			if (loginStudent.getConfirmedCourseGroups().values().contains(selectedCourseID)){
				System.out.println("You have already registered for this course!");
				showMenu();
			} else {
				Map<String, CourseGroup> availableCG = crsmgr.getCourseGroup();
				System.out.println("=================Add Course Group Page================"); 
				i = 1;	
				if(availableCG.size() <= 0){
					String[] matchCG = new String[availableCG.size()];
				for(Map.Entry<String, CourseGroup> item: availableCG.entrySet()) {
					CourseGroup check = item.getValue();
					if(check.getCourseCode().equals(selectedCourseID)) {
						System.out.println("|"+i+". "+item.getKey() + " " + check.getIndexNumber() + " " + check.getLessons());
						matchCG[i-1] = item.getKey();    		
					}
					i++;
				}
				System.out.println("|Please select the course group you wish to add:     |");
				System.out.println("|99. Return to previous menu. ");
				System.out.println("================================================");
				System.out.print("Option: ");
				try{
					option = scan.nextInt();
				}catch(Exception e){
					System.out.println("Please input numbers between 1 - "+ availableCG.size()+" or 99 only.\nOption: ");
					option = scan.nextInt();
				}
				if(option != 99 && (option > 0 && option <= availableCG.size())) {
					int addedCourseAU = 0;
					try{
						addedCourseAU = crsmgr.getCourseByCode(selectedCourseID).getCourseAU();
					}catch(Exception e){
						e.printStackTrace();
					}
						totalAU += addedCourseAU;
					
					if(totalAU <=21){
						crsmgr.enrol(loginStudent, availableCG.get(matchCG[option-1]));
						crsmgr.save();
						stdmgr.save();
						System.out.println("You have added course group: "+matchCG[option-1]);
						addMenu();
					}
					else {
						System.out.println("You have exceeded the maximum AU. Course cannot be added");
						showMenu();
					}
				}
				}else{
					System.out.println("There are not available course groups for this course.");
					showMenu();
				}
			}
        }else {
        	showMenu();
        }    	
    }
    
    public void dropMenu() {
		int option = 0;
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
        System.out.println("|99. Return to previous menu. ");
        System.out.print("Option: ");
		try{
		option = scan.nextInt();
		}catch(Exception e){
        	System.out.println("Please input numbers between 1 - "+ studentregcourse.size()+" or 99 only.\nOption: ");
		}
        
        if(option != 99 && (option > 0 && option <= studentregcourse.size())) {
        	String addedStudentFromWaitlist = dropCourseGroup(matchCG[option-1]);
        	System.out.print("You have dropped course group: "+matchCG[option-1]+".");
			//if there was a student added from waitlist
			if (addedStudentFromWaitlist != null){
				//send an email
				emailNotificationManager.sendEmail(addedStudentFromWaitlist, "New course added from your waitlist", matchCG[option-1]+" has been added. ");
			}
			dropMenu();
        }else {
        	showMenu();
        }
    }
    
    //Updated by WY
    private String dropCourseGroup(String courseGroup) {
		loginStudent.dropCourseGroups(courseGroup);
    	return crsmgr.dropCourseGroup(courseGroup, loginStudent.getMatriculationNumber());   	
    }
    
    public void printMenu() {
		loginStudent.printRegisterCourse();
		showMenu();
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
		scan.nextLine();
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
		 scan.nextLine();
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
        scan.nextLine();
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
								   lesson.typeOfLesson + "\t"+ lesson.getLocation());
			}
			System.out.println("---------------------------------------------------------");
			System.out.print("Press any key to continue to the next course...");
			scan.nextLine();
		}
		System.out.println("End of Time Table");
		System.out.println("Press Enter to return to main menu");
		scan.next();
			showMenu();
	}
    
}
