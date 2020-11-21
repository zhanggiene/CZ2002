import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

	/**
     * Manages the adding and dropping of courses
	 * @author Weiyao
     */
public class CourseManager{
	private Map<String, Course> courses; 
	private Map<String, CourseGroup> courseGroups;
	private Map<String, String[]> swapIndex;
	//course

	private String CourseFile="CourseData.bin" ;
	private String CourseGroupFile="CourseGroup.bin";
	private String SwapIndexFile="SwapIndex.bin";

	public CourseManager()
	{
		load();
		if (this.courses == null){
			this.courses = new HashMap<String, Course>();
		}
		if (this.courseGroups == null){
			this.courseGroups = new HashMap<String, CourseGroup>();
		}
		if (this.swapIndex == null){
			this.swapIndex = new HashMap<String, String[]>();
		} 
	}

	/**
	 * Adds a new course
	 * @param course
	 * @author Andrew
	 */
	public void addCourse(Course course)
	{
		courses.put(course.getCourseCode(),course);
		save();
	}

	/**
	 * Adds a new course group (to an existing course)
	 * @param courseGroup
	 * @param course
	 * @author Andrew and Wang Li Rong
	 */
	public void addCourseGroup(CourseGroup courseGroup, String courseCode){
		courseGroups.put(courseGroup.getIndexNumber(),courseGroup);
		Course course = getCourseByCode(courseCode);   
		course.addCourseGroup(courseGroup.getIndexNumber());
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

	/** 
	 * get course vacancies
	 * @author Wei Yao
     */
	public ArrayList<String[]> getVacancies(){
		ArrayList<String[]> crsvacancies = new ArrayList<String[]>();
		for(Map.Entry<String, CourseGroup> item: courseGroups.entrySet()) {
			String[] temp = {item.getKey(), Integer.toString(item.getValue().getVacancy()), Integer.toString(item.getValue().getTotalSize())};
			crsvacancies.add(temp);
		}
		return crsvacancies;
	}

	/** 
	 * Enrols student into a course group
	 * <li> Returns boolean telling the user if the coursemanager enrolled successfully
	 * @author Wei Yao and Wang Li Rong 
	 * @return false: student wasn't enrolled successfully into the course (student is in waitlist)
	 *         <li> true: student was successfully enrolled
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

	/**
	 * Getter for course group
	 * @return Hashmap of index: course group object
	 * @author Wei Yao
	 */
	public Map<String, CourseGroup> getCourseGroup()
	{
		return courseGroups;
	}

	/**
	 * Returns swaps relevant to the student in 
	 * 	["fromIndex toIndex", [senderMatricNumber, receiverMatricNumber]] form
	 * This is used for displaying what swaps the student can accept
	 * @return swaps relevant to the student
	 */
	//Limitation is that someone may override the previous swap for the toIndex and fromIndex
	public ArrayList<String[]> getSwapsForStudent(String matricNumber){
		ArrayList<String[]> returnList = new ArrayList<>();
		for (Map.Entry<String, String[]> item : swapIndex.entrySet()){
			String matric1 = item.getValue()[0]; //sender
			String matric2 = item.getValue()[1]; //receiver
			if (matric2.equals(matricNumber)){//if the currentStudent is receiver
				String[] entry= {item.getKey(), matric1, matric2};
				returnList.add(entry);
			}
		}
		return returnList;
	}

	/**
	 * Drop student from course group
	 * <li> if there are students in the waitlist, enrol the first student from waiting list
	 *      and return the matriculation number of this student
	 * <li> This is so that we can add the course to the student using studentmanager later
	 * <li> And send an email to the waitlist student to inform them.
	 * @param index : index to drop
	 * @param matric : matric number of student dropping out
	 * @author Wei Yao and Wang Li Rong
	 * @return null: if there is no waitlist student added
	 *         <li> waitlist student matrciculation number, if there is a waitlist student added
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
	public String[] getCourseCodeList() { 
		return Arrays.stream(courses.keySet().toArray()).toArray(String[]::new); 
	}


	/**
	 * Get hashmap of courses.
	 * @return HashMap of courseCode: Course
	 * @author Wei Yao
	 */
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

	/**
	 * Saves all hashmaps to their respective bin files
	 * @author zhu yan
	 */
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

	/**
	 * Load data from the bin files
	 * @author zhu yan
	 */
	private void load()
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
	
	/**
	 * Adds a new swap
	 * Assumes that all courseIDs are valid and all matriculation numbers are valid
	 * @param courseFromID : coure group index to swap from (from sender's perspective)
	 * @param courseToID : course group index to swap to (from sender's perspective)
	 * @param matric1 : matriculation number of sender
	 * @param matric2 : matriculation number of receiver
	 * @author Wei Yao and Wang Li Rong
	 */
	public void addSwap(String courseFromID,String courseToID, String matric1, String matric2) {
		String[] value = {matric1, matric2};
		String key = courseFromID+" "+courseToID;
		swapIndex.put(key, value);
		save();
	}

	/**
	 * Removes a swap
	 * @param key
	 * @author Wang Li Rong
	 */
	public void removeSwap(String[] selection){
		String key = selection[0];
		//remove the current request
		swapIndex.remove(key);
		//checks if there is a duplicate request from the other person and remove it
		String[] arrayKey = key.split(" "); //index 0(from) 1(to) from sender's perspective
		String otherKey = arrayKey[1] +" "+ arrayKey[0];
		//if there is another request with the swapped other key
		if (swapIndex.containsKey(otherKey)){
			//if this is the correct pair
			if (selection[1].equals(swapIndex.get(otherKey)[1]) && selection[2].equals(swapIndex.get(otherKey)[0])){
				//remove it
				swapIndex.remove(otherKey);
			}
		}
		save();
	}

	/**
	 * Prints all particulars of all courses
	 * @author Andrew 
	 */
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
	 * @return false: course does not exist
	 *        <li> true: course exists
	 * @author Wei Yao
	 */
	public boolean courseExist(String courseCode){
        return courses.containsKey(courseCode);
	}
	
	/**
	 * Checks if 2 course groups have clashing lessons 
	 * @param currentCourseGroupIndex
	 * @param newCourseGroupIndex
	 * @return true: these 2 course groups have at least 1 lesson which clashes
	 *        <li> false: these 2 course groups do not have any lesson clashes
	 * @author Wei Yao and Wang Li Rong
	 */
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

	/**
	 * Does swap for 2 students using swap entry
	 * @param swapEntry
	 * @author Wang Li Rong
	 */
	public void swap(String[] swapEntry){
        //from is the index of the sender
        //to is index of receiver
        //get [fromIndex, toIndex]
        String[] indexArray = swapEntry[0].split(" ");
        //do the swap
        //change record in the coursegroup
        //for the first coursegroup, remove the first student from fromIndex
        getCourseGroup(indexArray[0]).swapStudent(swapEntry[1], swapEntry[2]);
        //for the second coursegroup, remove the second student from ToIndex
		getCourseGroup(indexArray[1]).swapStudent(swapEntry[2], swapEntry[1]);
		save();
	}
	
	/**
	 * Adds Lesson to a course group
	 * @author Wang Li Rong
	 * @param courseGroupIndex
	 * @param lesson
	 */
	public void addLesson(String courseGroupIndex, PeriodClass lesson){
		CourseGroup courseGroup =courseGroups.get(courseGroupIndex);
		courseGroup.addLesson(lesson);
        save();
	}

	/**
	 * Set course name of an existing course
	 * @param courseCode
	 * @param newCourseName
	 * @author Wang Li Rong
	 */
	public void setCourseName(String courseCode, String newCourseName){
		Course course = getCourseByCode(courseCode);
		course.setCourseName(newCourseName);
    	save();
	}

	/**
	 * Set school of existing course
	 * @param courseCode
	 * @param school
	 * @author Wang Li Rong
	 */
	public void setSchool(String courseCode, School school) {
		Course course = getCourseByCode(courseCode);
		course.setSchool(school);
        save();
	}

	/**
	 * Set totalSize of an existing course group
	 * @param courseGroupIndex
	 * @param newTotalSize
	 * @author Wang Li Rong
	 */
	public void setTotalSize(String courseGroupIndex, int newTotalSize){
		CourseGroup courseGroup = getCourseGroup(courseGroupIndex); 
		courseGroup.setTotalSize(newTotalSize);
		save();
	}

	/**
	 * Updates index of an existing course
	 * @author Wang Li Rong and Andrew
	 * @param oldCourseGroupIndex
	 * @param newCourseGroupIndex
	 */
	public void setIndexNumber(String oldCourseGroupIndex, String newCourseGroupIndex){
		//set the index inside the coursegroup
		CourseGroup courseGroup = getCourseGroup(oldCourseGroupIndex);
		courseGroup.setIndexNumber(newCourseGroupIndex);
		//change the index inside the hashmap
		courseGroups.remove(oldCourseGroupIndex);
		courseGroups.put(newCourseGroupIndex,courseGroup);
		//set the index inside the course
		String courseCode = courseGroup.getCourseCode(); 
        getCourseByCode(courseCode).setCourseGroup(oldCourseGroupIndex, newCourseGroupIndex);
		save();
	}

	/**
	 * Get list of matriculation numbers of students in the course
	 * @param courseCode
	 * @return list of matriculation numbers of students in the course
	 */
	public ArrayList<String> getStudentsOfCourse(String courseCode){
		ArrayList<String> students = new ArrayList<>();
		for (String courseGroup : getCourseGroupsOfCourse(courseCode)){
			students.addAll(getCourseGroup(courseGroup).getStudents());
		}
		return students;
	}
	
	/**
	 * Change the course code of an existing course
	 * @author Wang Li Rong
	 * @param oldCourseCode
	 * @param newCourseCode
	 */
	public void setCourseCode(String oldCourseCode, String newCourseCode){
		//set the course code inside the course
		Course course = getCourseByCode(oldCourseCode);
		course.setCourseCode(newCourseCode);
		//change the courseCode inside the hashmap
		courses.remove(oldCourseCode);
		courses.put(newCourseCode, course);
		//set all the course codes of all coursegroups inside the course
		for (String courseGroupIndex : course.getCourseGroups()){
			CourseGroup courseGroup = getCourseGroup(courseGroupIndex);
			courseGroup.setCourseCode(newCourseCode);
		}
		save();
	}

}

