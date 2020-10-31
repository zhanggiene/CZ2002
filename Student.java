import java.io.Serializable;

enum Gender {
    FEMALE,
    MALE
}

public class Student implements Serializable
{
    private String name;
    private String matriculationNumber;
    private School school;
    private Gender gender;
    private String nationality;
    // arraylist containing tuples containing index and its corresponing class code
    
    
    public Student(String name,
                   String matriculationNumber,
                   School school,
                   Gender gender,
                   String nationality) {
        this.name=name;
        this.matriculationNumber=matriculationNumber;
        this.school=school;
        this.gender=gender;
        this.nationality=nationality;
    }
    
    public String toString() {
        return name+" "+matriculationNumber;
    }

    public void add(String index)
    {

    }
    public String getMetriculationNumber()
    {
        return matriculationNumber;
    }

    public Boolean CheckClashes(String index)
    {
        return false;
    }

    public School getSchool()
    {
        return school;
    }




    
}
