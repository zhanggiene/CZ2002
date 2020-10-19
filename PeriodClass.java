import java.time.LocalDateTime;
import java.time.Period;
import java.sql.Time;
import java.time.DayOfWeek;

import java.time.format.*;
public class PeriodClass {
    DayOfWeek dayOfWeek;
    int start;
    int end;
    String location;

    public PeriodClass(int weekday,int start,int end,String location)
    {
        assert start<end;
        assert start%10==0;
        this.dayOfWeek=DayOfWeek.of(weekday);  
        this.start=start;
        this.end=end;
        this.location=location;
    }

    

    public Boolean hasClash(PeriodClass b)
    {
        if (this.getweekDay().getValue()!=b.getweekDay().getValue())
        {
            return false;
        }
        else
        {
            if (this.end<=b.start)
            {
                return false;
            }
            else{
                if (this.start>=b.end)
                {
                    return false;
                }

            }


        }
        return true;

    }

    public DayOfWeek getweekDay()
    {
        return this.dayOfWeek;
    }

    public String toString() {
        return this.dayOfWeek.name()+" "+Integer.toString(this.start)+"-"+Integer.toString(this.end);
      }

    public static void main(String args[])
    {
        //LocalDateTime d1=LocalDateTime.now();
        //DateTimeFormatter dateinFull=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //System.out.println(d1);
        //System.out.println(d1.format(dateinFull));
        PeriodClass test1=new PeriodClass(1, 1200,1300,"lt22");
        PeriodClass test2=new PeriodClass(1, 1300,1500,"lt22");
        System.out.println(test1);



    }


    
}
