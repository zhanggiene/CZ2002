import java.time.LocalDateTime;
import java.time.Period;
import java.sql.Time;
import java.time.DayOfWeek;

import java.time.format.*;


/**
     * all the lessons are PeriodClass object, in charge of checking classes between lessons. 
     * @author zhang zhuyan
     */


enum TypeOfLesson {
    LABORATORY,TUTORIAL,LECTURE
}
public class PeriodClass {
    DayOfWeek dayOfWeek;
    int startTime;
    int endTime;
    String location;
    TypeOfLesson typeOfLesson;

    public PeriodClass(int weekday,int start,int end,String location)
    {
        assert start<end;
        assert start%10==0;
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
    private DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    
    /** return string representation of class period for printing. 
     * @return String
     */
    public String toString() {
        return this.dayOfWeek.name()+" "+Integer.toString(this.startTime)+"-"+Integer.toString(this.endTime);
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
