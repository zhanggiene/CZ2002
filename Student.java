/** 
Represents a student enrolled in the school.
A student can be enrolled in many courses.
@author Tan Kheng Leong
@version 1.0
@since 2014-08-31
*/
public class Student {
/**
* The first and last name of this student.
*/
private String name;
/**
* The age of this student.
*/
private int age;
/**
* Creates a new Student with the given name.
* The name should include both first and
* last name.
* @param name This Student's name.
* @param age This Student's age.
*/
public Student(String name, int age) {
this.name = name;
this.age = age;
}
/**
* Gets the first and last name of this Student.
* @return this Student's name.
*/
public String getName() {
return name;
}
/**
* Changes the name of this Student.
* This may involve a lengthy legal process.
* @param newName This Student's new name.
* Should include both first
* and last name.
*/
public void setName(String newName) {
name = newName;
}
}