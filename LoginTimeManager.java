import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.io.*;


    
/**
 * Schools in the system
 */
enum School {
    SCSE {
        @Override
        public String toString() {
            return "SCSE";
        }
    },
    EEE {
        @Override
        public String toString() {
            return "EEE";
        }
    },
    MSE {
        @Override
        public String toString() {
            return "MSE";
        }
    },
    MAE {
        @Override
        public String toString() {
            return "MAE";
        }
    },
    SPMS {
        @Override
        public String toString() {
            return "SPMS";
        }
    },
    NBS {
        @Override
        public String toString() {
            return "NBS";
        }
    };
}

/**
 * validate the correct timing of registering for students. 
 * serve as a database of correct timing for each school
 * @author zhang zhuyan
 */
public class LoginTimeManager extends Manager{

    private EnumMap<School, ArrayList<Date>> TimeMap;
    private SimpleDateFormat ft;
    private String FileName="timeData.bin";

    public LoginTimeManager()
    {
        ft = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
        load();
    }

    /**
     * Saves all records
     * @author zhu yan
     */
    private void save()
    {
        try {
            FileOutputStream fop=new FileOutputStream("./"+this.FileName);
            ObjectOutputStream oos=new ObjectOutputStream(fop);
            oos.writeObject(this.TimeMap);
            oos.close();
        }
         catch (Exception e) {
            e.printStackTrace();

    }
    }

    
    /** 
     * add access time for each school
     * @param schoolName  schoolName of Type School
     * @param start       start of type String "2020-10-19 16:00"
     * @param end         type String eg  "2020-10-19 16:00"
     * @author zhu yan
     */
    public void add(School schoolName, String start, String end) {

        try {
            Date time1 = ft.parse(start);
            Date time2 = ft.parse(end);
            ArrayList<Date> interval = new ArrayList<Date>();
            interval.add(time1);
            interval.add(time2);
            TimeMap.put(schoolName, interval);
        } catch (ParseException e) {
            System.out.println("the format is wrong");
            e.printStackTrace();
        }

        this.save();
    }

    /**
     * Checks if the format is correct for time
     * @param start       start of type String "2020-10-19 16:00"
     * @param end         type String eg  "2020-10-19 16:00"
     * @return            return true if the start is before the end time. 
     * @author zhu yan
     */
    public boolean isValidTime(String start, String end)
    {
        try {
            Date time1 = ft.parse(start);
            Date time2 = ft.parse(end);
            if (time1.after(time2))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        catch (ParseException e) {
            System.out.println("Wrong Format of Input!");
            return false;
        }

    }

    
    /** 
     * Returns list of school that already have a login period set
     * @return ArrayList of schools that already have a login period
     * @author Wang Li Rong
     * @author zhu yan
     */
    public ArrayList<School> getSchoolsWithLoginPeriod(){
        return new ArrayList<>(this.TimeMap.keySet());
    }

    /**
     * load data from local data, must use this method after initialization 
     * it is private as it will load automatically upon initialization
     * @author zhu yan
     */
    private void load()
    {
        try {
            File yourFile = new File(this.FileName);
            if(!yourFile.exists()){
                yourFile.createNewFile();
                this.TimeMap=new EnumMap<School,ArrayList<Date>>(School.class);
              }

              else
              {
                FileInputStream fis=new FileInputStream("./"+this.FileName);
                ObjectInputStream ois=new ObjectInputStream(fis);
                //WriteObject wo=null;
                //WriteObject[] woj=new WriteObject[5];
                this.TimeMap=(EnumMap<School, ArrayList<Date>>) ois.readObject();
                ois.close();
              }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
}

    
    /** 
     * Checks if it is within the allowed time frame
     * @param studentSchool  school type of student of type School
     * @param currentTime   the time of login of type Date
     * @return boolean      true if it is within the allowed time frame
     * @author zhu yan
     */
    public boolean isInside(School studentSchool, Date currentTime) {
        ArrayList<Date> interval = TimeMap.get(studentSchool);
        if (interval==null)
        {
            return false;
        }
        return currentTime.after(interval.get(0)) && currentTime.before(interval.get(1));

    }


        /** 
     * Checks if the current student log in too early or too late
     * @param studentSchool  school type of student of type School
     * @param currentTime   the time of login of type Date
     * @return boolean      true if it is before the allocated time
     *                      false if it is after the allocated time.
     * @author zhu yan
     */


    public boolean isEarly(School studentSchool,Date currentTime)
    {
        ArrayList<Date> interval = TimeMap.get(studentSchool);
        if (interval==null)
        {
            return false;
        }
        return currentTime.before(interval.get(0));
    
    }
    /**
     * print start and end access time for each school. 
     * @author zhu yan
     */
    public void printAllAccessPeriod()
    {

        System.out.println("Access time for all the school are:");
        if(this.TimeMap!=null)
        {
        for (School name: TimeMap.keySet()){
            System.out.println(name+": from      "+TimeMap.get(name).get(0)+"  to       "+TimeMap.get(name).get(1));  
        } 
    }

    }
    public void PrintSchoolAcessTime(School a)
    {
        if(this.TimeMap!=null && TimeMap.get(a)!=null )
        {
            System.out.println(a+": from      "+TimeMap.get(a).get(0)+"  to       "+TimeMap.get(a).get(1));  
    }
    }
}
