import java.io.Serializable;






public class Student implements Serializable
{
    String name;
    String MetriculationNumber;
    School school;
    // arraylist containing tuples containing index and its corresponing class code
    
    

    public Student(String name,String MetriculationNumber)
    {
        this.name=name;
        this.MetriculationNumber=MetriculationNumber;
    }
    public String toString() {
        return name+" "+MetriculationNumber;
    }

    public void add(String index)
    {

    }
    public String getMetriculationNumber()
    {
        return MetriculationNumber;
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
