import java.io.Serializable;
import java.time.DayOfWeek;


    

/**
 * Type of lesson
 */
enum TypeOfLesson {
    LABORATORY,TUTORIAL,LECTURE
}

/**
 * all the lessons are PeriodClass object, in charge of checking clashes between lessons. 
 * @author zhang zhuyan
 */
public class PeriodClass implements Serializable{
    private static final long serialVersionUID = 1L;
    private DayOfWeek dayOfWeek;
    private int startTime;
    private int endTime;
    private String location;
    private TypeOfLesson typeOfLesson;

    public PeriodClass(TypeOfLesson type, int weekday,int start,int end,String location)
    {
        assert start<end;
        assert start%10==0;
        this.typeOfLesson = type;
        this.dayOfWeek=DayOfWeek.of(weekday);  
        startTime=start;
        endTime=end;
        this.location=location;
    }

    /**  
     * Check with other PeriodClass to see if there is a overlap. 
     * @param b     the other PeriodClass
     * @return Boolean   return true if the two period overlap on timeTable
     * @author zhu yan
     */
    public Boolean hasClash(PeriodClass b)
    {
        if (this.getDayOfWeek().getValue()!=b.getDayOfWeek().getValue())
        {
            return false;
        }
        else
        {
            if (this.endTime<=b.startTime)
            {
                return false;
            }
            else{
                if (this.startTime>=b.endTime)
                {
                    return false;
                }
            }
        }
        return true;
    }

    
    /** 
     * Returns day of the week the lesson is on
     * @return DayOfWeek
     * @author zhu yan
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    
    /** 
     * Return string representation of class period for printing. 
     * @return String
     */
    public String toString() {
        return this.dayOfWeek.name()+" "+Integer.toString(this.startTime)+"-"+Integer.toString(this.endTime);
      }
    
    /**
     * Getter for Type of Lesson
     * @author Wang Li Rong
     * @return Type of Lesson
     */
    public TypeOfLesson getTypeOfLesson() {
        return typeOfLesson;
    }

    /**
     * Getter for Start Time
     * @author Wang Li Rong
     * @return Start time in int format
     */
    public int getStartTime() {
        return startTime;
    }


    /**
     * Getter for End Time
     * @author Wang Li Rong
     * @return End time in int format
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Getter for location
     * @author Wang Li Rong
     * @return Location (String)
     */
    public String getLocation() {
        return location;
    }

    /**
     * Prints details of this lesson
     */
    public void printRecord(){
        System.out.println(typeOfLesson + "\t" + dayOfWeek + "\t" + startTime + "\t" + endTime + "\t" + location);
    }    
}
