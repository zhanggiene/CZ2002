import java.io.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.time.DayOfWeek;

/**
     * validate the correct timing of registering for students. 
     * serve as a database of correct timing for each school
	 * @author Weiyao
     */
public class CourseManager {
	private Map<String, Course> courses; 
	private Map<String, CourseGroup> courseGroups;
	private Map<String, String[]> swapIndex;
	//course

	private String CourseFile="CourseData.bin" ;
	private String CourseGroupFile="CourseGroup.bin";
	private String SwapIndexFile="SwapIndex.bin";

	public CourseManager()
	{
		loadData();
		if (this.courses == null){
			this.courses = new HashMap<String, Course>();
		}
		if (this.courseGroups == null){
			this.courseGroups = new HashMap<String, CourseGroup>();
		}
		if (this.swapIndex == null){
			this.swapIndex = new HashMap<String, String[]>();
		} else {
			checkSwap();
		}

	}
	public void addCourse(Course course)
	{
		courses.put(course.getcourseCode(),course);
		save();
	}

	public void updateCourse(Course course, String oldCourseCode, String newCourseCode){
		courses.remove(oldCourseCode);
		courses.put(newCourseCode,course);
		save();
	}

	public void addCourseGroup(CourseGroup courseGroup)
	{
		courseGroups.put(courseGroup.getIndexNumber(),courseGroup);
		save();
	}

	public void updateCourseGroup(CourseGroup courseGroup, String oldIndex, String newIndex){
		courseGroups.remove(oldIndex);
		courseGroups.put(newIndex,courseGroup);
		save();
	}

	/** retreive course object
     * @param CourseCode
     * @return Course
	 * @author Wei Yao
     */
	public Course getCourseByCode(String CourseCode)
	{
		return courses.get(CourseCode);
	}

	/** get course vacancies
	 * @author Wei Yao
     */
	public ArrayList<String[]> getVacancies(){
		ArrayList<String[]> crsvacancies = new ArrayList<String[]>();
		for(Map.Entry<String, CourseGroup> item: courseGroups.entrySet()) {
			String[] temp = {item.getKey(), Integer.toString(item.getValue().getVacancy())};
			crsvacancies.add(temp);
		}
		return crsvacancies;
	}

	/** enrol student into a course	
	 * @author Wei Yao
	 * Updated by Wang Li Rong
	 * Returns boolean telling the user if the coursemanager enrolled successfully
     */
	public boolean enrol(String matricNumber , String index)
	{
		Boolean result = courseGroups.get(index).enrol(matricNumber);
		save(); //since there is a change in the database
		return result;
	}

	/** retreive course group (index) object
     * @param courseCode
     * @return CourseGroup
	 * @author Wei Yao
     */
	public CourseGroup getCourseGroup(String index)
	{
		return courseGroups.get(index);
	}

	//Created by WY
	public Map<String, CourseGroup> getCourseGroup()
	{
		return courseGroups;
	}


/* 	public Map<String, CourseGroup> getCourseGroupWY(String courseCode)
	{
		Map<String, CourseGroup> CGByCourseCode = new HashMap<String, CourseGroup>();
		for(Map.Entry<String, CourseGroup> item: courseGroups.entrySet()) {
			if(item.getValue().getCourseCode() == courseCode) {
				CGByCourseCode.put(item.getKey(), item.getValue());
			}
		}
		return CGByCourseCode;
	}
	//Created by WY
	public void dropCourseGroup(String index, String matric) {
		courseGroups.get(index).removeFromConfirmedStudent(matric);
	}
	} */
	/**
	 * Drop student from course group and returns next waitlist student
	 * @author Wei Yao
	 * Updated by Wang Li Rong
	 */
	public String dropCourseGroup(String index, String matric) {
		String result = courseGroups.get(index).removeFromConfirmedStudent(matric);
		save();
		return result;
	}

	/**
	 * Get list of CoursesCodes.
	 * @author Wang Li Rong
	 */

	public String[] getCourseCodeList() { return
			Arrays.stream(courses.keySet().toArray()).toArray(String[]::new); }


	public Map<String, Course> getCourseList(){
		return courses;
	}

	/**
	 * Get list of CourseGroupsIndexes of a Course. 
	 * @author Wang li Rong
	 */
	public ArrayList<String> getCourseGroupsOfCourse(String courseCode){
		ArrayList<String> courseGroupIndexes = courses.get(courseCode).getCourseGroups(); 
		ArrayList<String> courseGroupList = new ArrayList<>();
		for (String indexes:courseGroupIndexes){
			courseGroupList.add(courseGroups.get(indexes).getIndexNumber());
		}
		return courseGroupList;
	}

	public void save()
	{
		try {
			FileOutputStream fopCourse=new FileOutputStream("./"+this.CourseFile);
			ObjectOutputStream oosCourse=new ObjectOutputStream(fopCourse);
			oosCourse.writeObject(this.courses);
			oosCourse.close();

			FileOutputStream fopCourseGroup=new FileOutputStream("./"+this.CourseGroupFile);
			ObjectOutputStream oosCourseGroup=new ObjectOutputStream(fopCourseGroup);
			oosCourseGroup.writeObject(this.courseGroups);
			oosCourseGroup.close();
			
			FileOutputStream fopSwapIndexFile=new FileOutputStream("./"+this.SwapIndexFile);
			ObjectOutputStream oosSwapIndexFile=new ObjectOutputStream(fopSwapIndexFile);
			oosSwapIndexFile.writeObject(this.swapIndex);
			oosSwapIndexFile.close();
		}
		catch (Exception e) {
			e.printStackTrace();

		}
	}
	private void loadData()
	{
		try {
			FileInputStream fisCourse=new FileInputStream("./"+this.CourseFile);
			ObjectInputStream oisCourse=new ObjectInputStream(fisCourse);
			this.courses=( Map<String, Course>) oisCourse.readObject();
			oisCourse.close();
			//WriteObject wo=null;
			//WriteObject[] woj=new WriteObject[5];
			FileInputStream fisCourseGroup=new FileInputStream("./"+this.CourseGroupFile);
			ObjectInputStream oisCourseGroup=new ObjectInputStream(fisCourseGroup);
			this.courseGroups=( Map<String, CourseGroup>) oisCourseGroup.readObject();
			oisCourseGroup.close();            
			//updated by WY
			FileInputStream fisSwapIndexFile = new FileInputStream("./"+this.SwapIndexFile);
			ObjectInputStream oisSwapIndexFilep = new ObjectInputStream(fisSwapIndexFile);
			this.swapIndex = (Map<String, String[]>) oisSwapIndexFilep.readObject();
			oisSwapIndexFilep.close();  
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
/* 
	private void loadSwap()
	{
		try {          

			FileInputStream fisSwapIndexFile = new FileInputStream("./"+this.SwapIndexFile);
			ObjectInputStream oisSwapIndexFilep = new ObjectInputStream(fisSwapIndexFile);
			this.swapIndex = (Map<String, String[]>)oisSwapIndexFilep.readObject();
			oisSwapIndexFilep.close();  
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	} */
	/**
	 * Save the swap index result
	 * @author Wei Yao
	 */
	public void saveSwap()
	{
		FileOutputStream fopSwapIndexFile;
		try {
			fopSwapIndexFile = new FileOutputStream("./"+this.SwapIndexFile);
			ObjectOutputStream oosSwapIndexFile=new ObjectOutputStream(fopSwapIndexFile);
			oosSwapIndexFile.writeObject(this.swapIndex);
			oosSwapIndexFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	//Created by WY
	public boolean checkSwap() {
		boolean swapped = false;
		for(Map.Entry<String, String[]> item : swapIndex.entrySet()) {
			for(Map.Entry<String, String[]> item2 : swapIndex.entrySet()) {
				if(item.getValue()[0] == item2.getValue()[1] && item.getValue()[1] == item2.getValue()[0]) {
					courseGroups.get(item.getKey()).swapStudent(item.getValue()[1], item.getValue()[0]);
					courseGroups.get(item2.getKey()).swapStudent(item.getValue()[0], item.getValue()[1]);
					StudentManager stdmgr = new StudentManager();
					stdmgr.checkSwap(item.getValue()[0], item.getKey(), item.getValue()[1], item2.getKey());
					swapped = true;
					break;
				}
			}
		}

		return swapped;
	}
	
	public void addSwap(String courseID, String matric1, String matric2) {
		String[] temp = {matric1, matric2};
		swapIndex.put(courseID, temp);
	}

	public void printAllRecord() {
        System.out.println("List of Courses: ");
        System.out.println("\tCourse Code\tSchool\tCourse Name\tAU\tIndex");
		int i=1;
        for (Course c : courses.values()) {
            System.out.print(i+".\t");
			c.printCourse();
			ArrayList<String> cg = c.getCourseGroups();
			for(int j=0;j<cg.size();j++){
				System.out.print(cg.get(j) + "\t");
			}
			System.out.print("\n");
            i++;
        }
    }
	/**
	 * Check if course exist
	 * @param courseCode
	 * @return boolean
	 * @author Wei Yao
	 */
	public boolean courseExist(String courseCode){
        return courses.containsKey(courseCode);
	}
	
	
	//Updated by Wang Li Rong to use two courseGroup indexes instead of coursegroup itself
	public boolean isClashing(String currentCourseGroupIndex, String newCourseGroupIndex){	
		CourseGroup currentCourseGroup = courseGroups.get(currentCourseGroupIndex);
		CourseGroup newCourseGroup = courseGroups.get(newCourseGroupIndex);
		for(PeriodClass item: currentCourseGroup.getLessons()){//load first lesson.
			for(PeriodClass item2: newCourseGroup.getLessons()){//compare starttime of first lesson to other lessons in new index
				if (item.hasClash(item2)){
					return true;
				}
			}
		}
		return false;
	}

	/*
	public static void main(String[] args) {
		//For testing
		CourseManager manager=new CourseManager();

		//add courses
		Course course=new Course("CZ2002","OODP",School.SCSE, 3);
		manager.addCourse(course);

		//add coursegroups
		CourseGroup courseGroup = new CourseGroup("DSAI1", 50, "CZ2002");
		if (!course.courseGroupExist(courseGroup.getIndexNumber())){
			course.addCourseGroup(courseGroup.getIndexNumber());
			manager.addCourseGroup(courseGroup);
		}
		CourseGroup courseGroup2 = new CourseGroup("DSAI2", 50, "CZ2002");
		if (!course.courseGroupExist(courseGroup2.getIndexNumber())){
			course.addCourseGroup(courseGroup2.getIndexNumber());
			manager.addCourseGroup(courseGroup2);
		}

		courseGroup.enrol("U1234567B");
		courseGroup2.enrol("U2234567B");

		manager.save();

		System.out.println(manager.courseGroups);
		System.out.println(manager.courses); 

		// CourseManager manager=new CourseManager();
		// Course a=new Course("CZ2002","oodp",School.SCSE);
		// System.out.println(a.getcourseCode());
		// manager.add(a);
		// Course b=manager.getCourseByCode("CZ2002");
		// b.changeSize(30);
		// System.out.println(manager.getCourseByCode("CZ2002"));

	}
	*/
}

