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

<<<<<<< Updated upstream
	public void setVacancy(int newVacancy){
		this.vacancy = newVacancy;
	}

=======
>>>>>>> Stashed changes
	//updated by WY
	public boolean isConfirmedStudent(String matricNumber){
		boolean confirmed = false;
		for(int i = 0; i<=students.size(); i++){
			if(matricNumber == students.get(i))
				confirmed = true;
		}
		return confirmed;
	}

	public void enrol(String matricNumber){
		//still need to add logic 
		//If length of confirmedStudents is >= vacancy, add student to waitlist
		//If length of confirmedStudents is <vacancy, add student to confirmedStudents 
		//list and add this index to Student with addToConfirmedIndex
		//this is just testing code

		if(students.size() >= vacancy)
			studentsWaiting.add(matricNumber);
		else
			students.add(matricNumber);
	}

	//updated by WY
	public boolean isWaitlistStudent(String matricNumber){
		boolean waiting =false;
		for(int i=0; i<=studentsWaiting.size(); i++){
			if(matricNumber == studentsWaiting.get(i)){
				waiting = true;
			}
		}
		return waiting;
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
	public void removeFromConfirmedStudent(String matricNumber){
		for(int i = 0; i < students.size(); i++) {
			if(students.get(i) == matricNumber) {
				students.remove(i);
				enrol(studentsWaiting.get(0));
				studentsWaiting.remove(0);
				break;
			}
		}
	}

	public void addLesson(PeriodClass lesson){
		lessons.add(lesson);
	}
	
	//Updated by WY
	public ArrayList<PeriodClass> getLessons(){
		return lessons;
	}
<<<<<<< Updated upstream

	public void isClashing(CourseGroup otherindex){

	}







=======
>>>>>>> Stashed changes
}
