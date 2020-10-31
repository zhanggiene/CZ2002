import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.io.*;
import java.util.*;


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

    EnumMap<School, ArrayList<Date>> TimeMap=new EnumMap<School,ArrayList<Date>>(School.class);
    SimpleDateFormat ft;
    String FileName="timeData.bin";

    public LoginTimeManager()
    {
        ft = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
    }

    public void save()
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

    public void add(School scse, String start, String end) {

        try {
            Date time1 = ft.parse(start);
            Date time2 = ft.parse(end);
            ArrayList<Date> interval = new ArrayList<Date>();
            interval.add(time1);
            interval.add(time2);
            TimeMap.put(scse, interval);
        } catch (ParseException e) {
            System.out.println("the format is wrong");
            e.printStackTrace();
        }

        this.save();

        // save
        

    }


    public void loadLoginPeriods()
    {
        try {
            FileInputStream fis=new FileInputStream("./"+this.FileName);
            ObjectInputStream ois=new ObjectInputStream(fis);
            //WriteObject wo=null;
            //WriteObject[] woj=new WriteObject[5];
    
            this.TimeMap=(EnumMap<School, ArrayList<Date>>) ois.readObject();
            ois.close();
    
        }
        catch (Exception e) {
            e.printStackTrace();
        }
}

    public boolean isInside(School studentSchool, Date currentTime) {
        ArrayList<Date> interval = TimeMap.get(studentSchool);

        return currentTime.after(interval.get(0)) && currentTime.before(interval.get(1));

    }

    public void printAllAccessPeriod()
    {

        System.out.println("Access time for all the school are:");
        for (School name: TimeMap.keySet()){
            System.out.println(name+": from      "+TimeMap.get(name).get(0)+"  to       "+TimeMap.get(name).get(1));  
        } 

    }

    public static void main(String[] args) {

        LoginTimeManager timemanager=new LoginTimeManager();
        //timemanager.add(School.SCSE,"2020-10-19 16:00","2020-10-19 17:00");
        //timemanager.save();
        timemanager.loadLoginPeriods();
        timemanager.printAllAccessPeriod();

        
    }

}

//     edit(sch)