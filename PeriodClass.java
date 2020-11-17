import java.io.Serializable;
import java.time.DayOfWeek;


/**
     * all the lessons are PeriodClass object, in charge of checking classes between lessons. 
     * @author zhang zhuyan
     */


enum TypeOfLesson {
    LABORATORY,TUTORIAL,LECTURE
}
public class PeriodClass implements Serializable{
    DayOfWeek dayOfWeek;
    int startTime;
    int endTime;
    String location;
    TypeOfLesson typeOfLesson;
    static final long serialVersionUID = 8509715140262640597L;

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

    

    
    /**  check with other PeriodClass to see if there is a overlap. 
     * @param b     the other PeriodClass
     * @return Boolean   return true if the two period overlap on timeTable
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
     * @return DayOfWeek
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    
    /** return string representation of class period for printing. 
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

    public void printRecord(){
        System.out.println(typeOfLesson + "\t" + dayOfWeek + "\t" + startTime + "\t" + endTime + "\t" + location);
    }
    
    /** 
     * @param args[]
     */
    public static void main(String args[])
    {
        //LocalDateTime d1=LocalDateTime.now();
        //DateTimeFormatter dateinFull=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //System.out.println(d1);
        //System.out.println(d1.format(dateinFull));
        //"07:00" 
        //PeriodClass test1=new PeriodClass(1, 1200,1300,"lt22",);
        //PeriodClass test2=new PeriodClass(1, 1300,1500,"lt22");
        //System.out.println(test1);

    }


    
}
