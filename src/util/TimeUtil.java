package util;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TimeUtil {

    public static String getSysTime(){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String time=df.format(new Date());
        String[] strings=time.split(" ");
        return strings[0];
    }

    public static int timeCompare(String origin,String target){
        String[] origins=origin.split("-");
        String[] targets=target.split("-");
        for(int i=0;i<3;i++){
            if(Integer.parseInt(origins[i])<Integer.parseInt(targets[i]))
                return 0;
        }
        return 1;
    }
}
