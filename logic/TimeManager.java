package logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeManager {

    public static String getTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD\tHH:mm:ss");
        return sdf.format(cal.getTime());
    }

}