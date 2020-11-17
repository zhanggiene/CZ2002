import java.io.Serializable;
import java.util.ArrayList;







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

	public String getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(String newNumber){
		this.indexNumber = newNumber;
	}

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

	public int getVacancy(){
		return vacancy;
	}

	public void setVacancy(int newVacancy){
		this.totalSize= newVacancy;
	}

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
	 * Returns true if the enrolment was successful
	 * Returns false if student was added into waitlist
	 * @param matricNumber
	 */
	public Boolean enrol(String matricNumber){
		//if there is still vacancy add the student
		if(vacancy>=1){
			students.add(matricNumber);
			vacancy--;
			return true;
		}
		else { //if no more vacancy, then add the student to the waiting list
			studentsWaiting.add(matricNumber);
			return false;
		}
	}

	//updated by WY
	//updated by Wang Li Rong
	public boolean isWaitlistStudent(String matricNumber){
		return studentsWaiting.contains(matricNumber);
	}
	//updated by WY
	public void removeWaitlistStudent(String matricNumber) {
		for(int i=0; i<=studentsWaiting.size(); i++){
			if(matricNumber == studentsWaiting.get(i)){
				studentsWaiting.remove(i);
				break;
			}
		}
	}
	
	public void swapStudent(String matric1, String matric2) {
		for(int i = 0; i < students.size(); i++) {
			if(students.get(i) == matric1) {
				students.remove(i);
				enrol(matric2);
				break;
			}
		}
	}

	public int getTotalSize(){
		return totalSize;
	}

	//removed confirmed student and add first student from waiting list
	//updated by WY 
	//updated by Wang Li Rong
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
		// for(int i = 0; i < students.size(); i++) {
		// 	if(students.get(i) == matricNumber) {
		// 		students.remove(i);
		// 		String waitlistStudent = studentsWaiting.get(0);
		// 		enrol(studentsWaiting.get(0));
		// 		studentsWaiting.remove(0);
		// 		return waitlistStudent;
		// 	}
		// }
		// return null;
	}

	public void addLesson(PeriodClass lesson){
		lessons.add(lesson);
	}
	
	//Updated by WY
	public ArrayList<PeriodClass> getLessons(){
		return lessons;
	}

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
