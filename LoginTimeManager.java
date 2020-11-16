import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.io.*;


/**
     * validate the correct timing of registering for students. 
     * serve as a database of correct timing for each school
     * @author zhang zhuyan
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
    };
}


public class LoginTimeManager {

    private EnumMap<School, ArrayList<Date>> TimeMap;
    private SimpleDateFormat ft;
    private String FileName="timeData.bin";

    public LoginTimeManager()
    {
        ft = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
        loadLoginPeriods();
    }

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

        // save
    }

    
    /** 
     * @return ArrayList
     */
    public ArrayList getSchoolsWithLoginPeriod(){
        return new ArrayList<>(this.TimeMap.keySet());
    }

    /**
     * load data from local data, must use this method after initialization 
     * it is private as it will load automatically upon initialization
     */
    private void loadLoginPeriods()
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
     * @param studentSchool  school type of student of type School
     * @param currentTime   the time of login of type Date
     * @return boolean      true if it is within the allowed time frame
     */
    public boolean isInside(School studentSchool, Date currentTime) {
        ArrayList<Date> interval = TimeMap.get(studentSchool);

        return currentTime.after(interval.get(0)) && currentTime.before(interval.get(1));

    }
    /**
     * print start and end access time for each school. 
     * 
     */
    public void printAllAccessPeriod()
    {

        System.out.println("Access time for all the school are:");
        for (School name: TimeMap.keySet()){
            System.out.println(name+": from      "+TimeMap.get(name).get(0)+"  to       "+TimeMap.get(name).get(1));  
        } 

    }

    
    /** 
     * @param args
     */
    public static void main(String[] args) {

        LoginTimeManager timemanager=new LoginTimeManager();
        //timemanager.add(School.SCSE,"2020-10-19 16:00","2020-10-19 17:00");
        timemanager.printAllAccessPeriod();
        
    }

}

//     edit(sch)