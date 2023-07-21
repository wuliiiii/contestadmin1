package org.algorithmcontestdatacollect.crawlerendpoint2.Utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static Long timeStringToTimeStamp(String time) throws ParseException {
        String pattern = "(\\d+)-(\\d+)-(\\d+) (\\d+):(\\d+):(\\d+)([\\+|\\-]\\d+)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(time);
        if(m.find()){
            String year =  m.group(1);
            String month = m.group(2);
            String day = m.group(3);
            String hour = m.group(4);
            String minute = m.group(5);
            String second = m.group(6);
            String zone = m.group(7);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"+zone));
            var date = sdf.parse(year+"-" + month + "-" + day + " " + hour+":"+minute+":"+second);
            return date.getTime() /1000;
        }else{
            return 0L;
        }
    }
    public static String parserNickname(String url){
        var ls = url.split("/");
        return ls.length == 0? "":ls[ls.length - 1];
    }
    public static long parserDuration(String dur) {
        var sp = dur.split(":");
        int minute = Integer.parseInt(sp[0]);
        int sec = Integer.parseInt(sp[1]);
        return (minute* 60L + sec)*60L;
    }
    public static Long parserSid(String url){
        var ls = url.split("/");
        return Long.parseLong(ls[ls.length - 1]);
    }
}
