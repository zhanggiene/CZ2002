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
		System.out.println("| 7. Accept Swop Request                    |");
		System.out.println("| 8. View Confirmed Time Table              |");
        System.out.println("| 99. Quit                                   |");
        System.out.println("=============================================");
        System.out.print("Option: ");
		int option = 0;
        while(!scan.hasNextInt()) {
			System.out.println("Please input numbers between 1 - 8 or 99 only.\nOption: ");
			scan.nextLine();
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
			case 7: acceptSwap();
				break;
			case 8: viewTimetable();
				break;
        	case 99: System.out.println("Exiting MyStars now....");
				break;	
			default: System.out.println("Please input numbers between 1 - 8 or 99 only.\nOption: ");
				showMenu();;
				break;
        }
        
    }
    
    public void addMenu() {
    	System.out.println("=================Add Course Page================");
    	
		int i = 1;
		//calculate student's current AU
		int totalAU = 0;
		ArrayList<String> confirmedCourse = new ArrayList<String> (loginStudent.getConfirmedCourseGroups().values());
		for(i=i-1; i<confirmedCourse.size(); i++){
			int confirmedCourseAU = crsmgr.getCourseByCode(confirmedCourse.get(i)).getCourseAU();
			totalAU += confirmedCourseAU;
		}
		//show all possible courses
		i = 1;
    	availableCourse = crsmgr.getCourseList(); //coursemanager method to retrieve all courses
    	String[] courseID = new String[availableCourse.size()];
    	for(Entry<String, Course> item: availableCourse.entrySet()) {
    		System.out.println("|"+i+". "+ item.getKey() +" " + item.getValue().getName());
    		courseID[i-1] = item.getKey();
    		i++;
    	}
    	System.out.print("|99. Return to previous menu. ");
    	System.out.println("|Please select the course you wish to add:     |");
    	System.out.println("================================================");
    	System.out.print("Option: ");
        while(!scan.hasNextInt()) {
        	System.out.println("Please input numbers between 1 - "+ availableCourse.size()+" or 99 only.\nOption: ");
			scan.nextLine();
		}
        int option = scan.nextInt();
        if(option != 99) {
			//shows all possible coursegroups 
	        String selectedCourseID = availableCourse.get(courseID[option-1]).getcourseCode();
			if (loginStudent.getConfirmedCourseGroups().values().contains(selectedCourseID)){
				System.out.println("You have already registered for this course!");
				showMenu();
			} else {
				ArrayList<String> availableCG = crsmgr.getCourseGroupsOfCourse(selectedCourseID);
				System.out.println("=================Add Course Group Page================"); 
				i = 1;
				String[] matchCG = new String[availableCG.size()];
				for(String courseGroupIndex: availableCG) {
					CourseGroup courseGroup = crsmgr.getCourseGroup(courseGroupIndex);
					System.out.println("|"+i+". "+courseGroup.getIndexNumber()  + " " + courseGroup.getLessons());
					matchCG[i-1] = courseGroup.getIndexNumber();    		
					i++;
				}
				System.out.println("|Please select the course group you wish to add:     |");
				System.out.println("================================================");
				System.out.print("Option: ");
				while(!scan.hasNextInt()) {
					System.out.println("Please input numbers between 1 - "+ availableCG.size()+" or 99 only.\nOption: ");
					scan.nextLine();
				}
				option = scan.nextInt();
				if(option != 99) {
					int addedCourseAU = crsmgr.getCourseByCode(selectedCourseID).getCourseAU();
					totalAU += addedCourseAU;
					
					//check if student is already in the waitlist to prevent spamming
					if (crsmgr.getCourseGroup(matchCG[option-1]).isWaitlistStudent(loginStudent.getMatriculationNumber())){
						System.out.println("You are already in the waitlist of this course group!");
						showMenu();
					} else if (isClashing(matchCG[option-1])){
						System.out.println("Cannot add this index because of timetable clashes.");
						showMenu();
					}
					// check if student has has AU under 21
					else if (totalAU <=21){
						Boolean result = crsmgr.enrol(loginStudent.getMatriculationNumber(), matchCG[option-1]);
						//if the enrolment is succesful for coursemanager, add to student also
						if (result){
							stdmgr.enrol(loginStudent.getMatriculationNumber(), matchCG[option-1], selectedCourseID);
							System.out.println("You have added course group: "+matchCG[option-1]);
						} // if enrolment was unsuccessfully, this means that student is in waitlit
						else {
							System.out.println(matchCG[option-1]+" is full, you will be added to the waitlist. ");
						}
						addMenu();
					}
					else {
						System.out.println("You have exceeded the maximum AU. Course cannot be added");
						showMenu();
					}
				}
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
        while(!scan.hasNextInt()) {
        	System.out.println("Please input numbers between 1 - "+ studentregcourse.size()+" or 99 only.\nOption: ");
			scan.nextLine();
		}
        int option = scan.nextInt();
        if(option != 99) {
        	String addedStudentFromWaitlist = dropCourseGroup(matchCG[option-1]);
        	System.out.print("You have dropped course group: "+matchCG[option-1]+".");
			//if there was a student added from waitlist
			if (addedStudentFromWaitlist != null){
				stdmgr.enrol(addedStudentFromWaitlist, matchCG[option-1], crsmgr.getCourseGroup(matchCG[option-1]).getCourseCode());
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
		String result = crsmgr.dropCourseGroup(courseGroup, loginStudent.getMatriculationNumber());
		boolean result2 = stdmgr.dropCourseGroup(loginStudent.getMatriculationNumber(), courseGroup);
		if (!result2){
			System.out.println("Student does not have course group");
		}
		return result;   	
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
    		System.out.println("|"+item[0]+" has "+ item[1] +"/"+item[2] + " vacancies available.");
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
     	scan.next();
     	showMenu();
    }
    
    public void swopIndex() {

		System.out.println("================Swap Index Menu=========================");
		//check that current student has the course group to swap
		System.out.println("|Please enter the course group you wish to swap from: ");
		String courseFromID = scan.next();
		while (crsmgr.getCourseGroup(courseFromID)==null){
			System.out.println("Please enter a valid course group.");
			courseFromID = scan.next();
		}

    	System.out.println("|Please enter the course group you wish to swap to: ");
		String courseToID = scan.next();
		while (crsmgr.getCourseGroup(courseToID)==null){
			System.out.println("Please enter a valid course group.");
			courseToID = scan.next();
		}
		String courseCode = crsmgr.getCourseGroup(courseToID).getCourseCode();
		ArrayList<String> confirmedCourse = new ArrayList<String> (loginStudent.getConfirmedCourseGroups().keySet());
		//checking for clashes for current student and if the current student has the course to swop
		boolean currentStudentClash = false;
		boolean currentStudentHasIndex=false;
		//go through all couseGroups of student
		for (String courseGroup : confirmedCourse){
			//ignore clashes in the current course
			if (crsmgr.getCourseGroup(courseGroup).getCourseCode().equals(courseCode) &&
				courseGroup.equals(courseFromID)){
				currentStudentHasIndex=true;
				continue;
			}
			//check for clashes
			currentStudentClash = crsmgr.isClashing(courseGroup, courseToID);
		}
		if (!currentStudentHasIndex){
			System.out.println("You do not have the index to swop with the other student.");
		} else if (currentStudentClash){
			System.out.println("Swopping courses will cause a clash in your timetable");
		} else{
			System.out.println("|Please enter matric number of the student you wish to swap with: ");
			String matric2 = scan.next();
			while (stdmgr.getStudent(matric2)==null){
				System.out.println("Please enter a valid matriculation number");
				matric2 = scan.next();
			}
			ArrayList<String> confirmedCourseOtherStudent = new ArrayList<String> (stdmgr.getStudent(matric2).getConfirmedCourseGroups().keySet());
			//checking for clashes for other student and if the other student has the course to swop
			boolean otherStudentClash = false;
			boolean otherStudentHasIndex=false;
			//go through all couseGroups of student
			for (String courseGroup : confirmedCourseOtherStudent){
				//ignore clashes in the current course
				if (crsmgr.getCourseGroup(courseGroup).getCourseCode().equals(courseCode) &&
					courseGroup.equals(courseToID)){
					otherStudentHasIndex=true;
					continue;
				}
				//check for clashes
				otherStudentClash = crsmgr.isClashing(courseGroup, courseToID);
			}
			
			if (!otherStudentHasIndex){
				System.out.println("The other student does not have the index to swap with you");
			} else if (otherStudentClash){
				System.out.println("Swopping courses will cause a clash in other student's timetable");
			} else {
				crsmgr.addSwap(courseFromID,courseToID, loginStudent.getMatriculationNumber(), matric2);
				System.out.println("|Your registered course group will be updated if successful.\nPress any key to return to previous menu.");
				scan.next();
			}
		} 
	
        showMenu();
	}

	private void acceptSwap(){
		ArrayList<String[]> swapArrayList = crsmgr.getSwapsForStudent(loginStudent.getMatriculationNumber());
		System.out.println("\tFrom To\tSender\tReceiver");
		int i=1;
		for (String[] swap : swapArrayList){
			System.out.println(i+". "+swap[0]+"\t"+swap[1]+"\t"+swap[2]);
			i++;
		}
		System.out.println("Enter choice number to accept (-1 to go back to menu)");
		int option;
		while (!scan.hasNextInt()){
			System.out.println("Please enter valid int.");
			scan.nextLine();
		}
		option = scan.nextInt();
		if (option != -1){
			if (option >=1 && option <=swapArrayList.size()){
				String[] selection = swapArrayList.get(option-1);
				
				//from is the index of the sender
				//to is index of receiver
				//get [fromIndex, toIndex]
				String[] indexArray = selection[0].split(" ");
				//do the swap
				//change record in the coursegroup
				//for the first coursegroup, remove the first student from fromIndex
				crsmgr.getCourseGroup(indexArray[0]).swapStudent(selection[1], selection[2]);
				//for the second coursegroup, remove the second student from ToIndex
				crsmgr.getCourseGroup(indexArray[1]).swapStudent(selection[2], selection[1]);

				//now change the record in the student as well
				//first student (sender)
				stdmgr.getStudent(selection[1]).swapIndex(indexArray[1], indexArray[0]);
				//second student (receiver)
				stdmgr.getStudent(selection[2]).swapIndex(indexArray[0], indexArray[1]);
				//save
				stdmgr.save();
				crsmgr.save();
				//remove the swap from database
				crsmgr.removeSwap(selection[0]);
			} else {
				System.out.println("Invalid option");
			}
		}

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
			scan.nextLine();
		}
		System.out.println("End of Time Table");
		System.out.println("Press any key to return to main menu");
		scan.nextLine();
		showMenu();
	}

	private boolean isClashing(String newCourseGroupIndex){
		ArrayList<String> confirmedCourse = new ArrayList<String> (loginStudent.getConfirmedCourseGroups().keySet());
		//go through all couseGroups of student
		for (String courseGroup : confirmedCourse){
			//check for clashes
			if (crsmgr.isClashing(courseGroup, newCourseGroupIndex)){
				return true;
			}
		}
		return false;
	}
    
}
