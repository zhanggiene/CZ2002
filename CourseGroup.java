import java.io.Serializable;
import java.util.ArrayList;

/**
 * Entity class for storing information about the course group
 */
public class CourseGroup implements Serializable{
	private String indexNumber;
	private int totalSize;
	private String courseCode; 
	private int vacancy;
	private ArrayList<PeriodClass> lessons;
	private ArrayList<String> students;
	private ArrayList<String> studentsWaiting; //Q for student queing for this tutorial 
	private static final long serialVersionUID = 2L;


	public CourseGroup(String index, int totalSize, String courseCode){
		this.indexNumber = index;
		this.totalSize = totalSize;
		this.courseCode = courseCode;
		this.vacancy = totalSize;
		this.lessons = new ArrayList<>();
		this.students = new ArrayList<>();
		this.studentsWaiting = new ArrayList<>();
	}

	/**
	 * Getter for index number
	 */
	public String getIndexNumber() {
		return indexNumber;
	}

	/**
	 * Setter for index number
	 * @param newNumber
	 */
	public void setIndexNumber(String newNumber){
		this.indexNumber = newNumber;
	}

	/**
	 * Getter for course code
	 * @return course code
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * @return List of Students Matriculation Numbers
	 * @author Wang Li Rong
	 */
	public ArrayList<String> getStudents() {
		return students;
	}

	/**
	 * Getter for vacancy
	 * @return vacancy
	 */
	public int getVacancy(){
		return vacancy;
	}

	/**
	 * Setter for total size
	 * @param newTotalSize
	 */
	public void setTotalSize(int newTotalSize){
		this.totalSize= newTotalSize;
	}

	/**
	 * Checks if student is in this course group
	 * @param matricNumber
	 * @return false: student is not in this course group
	 * 		   <li> true: sudent is in this course group
	 */
	//updated by WY
	public boolean isConfirmedStudent(String matricNumber){
		boolean confirmed = false;
		for(int i = 0; i<=students.size(); i++){
			if(matricNumber == students.get(i))
				confirmed = true;
		}
		return confirmed;
	}

	/**
	 * Returns status of the enrolment and enrols the student if successful
	 * If the course group is full, student is added into waitlist
	 * If the course group is not full, student is directly added
	 * @param matricNumber
	 * @author Wang Li Rong
	 * @return true: enrolment was successful 
	 * 		   <li> false: enrolment is pending (the course is full)
	 */
	public Boolean enrol(String matricNumber){
		//if there is still vacancy add the student
		if(vacancy>=1){
			students.add(matricNumber);
			vacancy--;
			return true;
		}
		//if no more vacancy, then add the student to the waiting list
		else { 
			studentsWaiting.add(matricNumber);
			return false;
		}
	}

	/**
	 * To check if student is currently in the waitlist of this course group.
	 * This is used in enrolment to prevent the student from being added to the waitlist 
	 * multiple times.
	 * @param matricNumber
	 * @return true: student is in waitlist for this coursegroup
	 * 	       <li> false: student is not in the waitlist for this course group
	 * @author Wang Li Rong
	 */
	public boolean isWaitlistStudent(String matricNumber){
		return studentsWaiting.contains(matricNumber);
	}

	/**
	 * Removes student from the waitlist of this course group
	 * @param matricNumber
	 * @author Wei Yao
	 */
	public void removeWaitlistStudent(String matricNumber) {
		for(int i=0; i<=studentsWaiting.size(); i++){
			if(matricNumber == studentsWaiting.get(i)){
				studentsWaiting.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Swaps the two students.
	 * We assume that the student to be removed has been checked to be in this course.
	 * @param matricToRemove : matriculation number of the student to remove from this course
	 * @param matricToAdd : matriculation number of the student to add to this course
	 */
	public void swapStudent(String matricToRemove, String matricToAdd) {
		for(int i = 0; i < students.size(); i++) {
			if(students.get(i) == matricToRemove) {
				students.remove(i);
				enrol(matricToAdd);
				break;
			}
		}
	}

	/**
	 * Getter for total size
	 * @return totalsize
	 */
	public int getTotalSize(){
		return totalSize;
	}

	/**
	 * Removes a confirmed student
	 * <li> if there are students in the waitlist, enrol the first student from waiting list
	 *      and return the matriculation number of this student
	 * <li> This is so that we can add the course to the student using studentmanager later
	 * <li> And send an email to the waitlist student to inform them.
	 * @param matricNumber
	 * @return matriculation number of the first student from waitlist
	 * 		   <li> null: when there is no one in the waitlist
	 * @author Wei Yao and Wang Li Rong
	 */
	public String removeFromConfirmedStudent(String matricNumber){
		//if there are no students in waitlist
		if (studentsWaiting.size() <=0){
			//if the student is in the courseGroup
			if (students.contains(matricNumber)){
				students.remove(matricNumber);
				vacancy++; //1 more vacancy freed up
			}
		} else { //if there are waitlist students
			//if the student is in the courseGroup
			if (students.contains(matricNumber)){
				students.remove(matricNumber); //remove current student
				String firstInWaitlist = studentsWaiting.get(0);
				students.add(firstInWaitlist);
				studentsWaiting.remove(0);
				//no change in vacancy
				return firstInWaitlist;
			}
		}
		return null;
	}

	/**
	 * Adds a lesson to the course
	 * @author Andrew
	 */
	public void addLesson(PeriodClass lesson){
		lessons.add(lesson);
	}
	
	/**
	 * Getter for lessons
	 * @author Wei Yao
	 * @return lessons
	 */
	public ArrayList<PeriodClass> getLessons(){
		return lessons;
	}

	/**
	 * Prints lesson information for this course
	 * @author Wang Li Rong
	 */
	public void printInfo(){
		System.out.println("Index Number : " + indexNumber);
		System.out.println("Vacancy = " + vacancy);
		System.out.println("Type\tDay\tStart Time\tEnd Time\tLocation");
		for(int i = 0;i<lessons.size();i++){
			PeriodClass less = lessons.get(i);
			less.printRecord();
		}
	}
}
