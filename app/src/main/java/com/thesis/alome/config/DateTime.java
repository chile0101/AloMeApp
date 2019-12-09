package com.thesis.alome.config;

import com.thesis.alome.model.DatePojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DateTime {

    public static final SimpleDateFormat dateFm = new SimpleDateFormat("EEEE-dd/MM/yyyy");
    public static final SimpleDateFormat timeFm = new SimpleDateFormat("HH:mm");
    public static final Calendar calendar = Calendar.getInstance();

    public static List<String> getNextHours(){
        List<String> hourList = new ArrayList<String>();

        int h = calendar.get(Calendar.HOUR_OF_DAY) ;
        int m = calendar.get(Calendar.MINUTE);

        int i = 6;
        for (; i<=20;i++){
            hourList.add(i + ":00" );
            hourList.add(i + ":30");

        }
        hourList.add("21:00");
        return hourList;
    }

    public static List<DatePojo> getNextDays(){
        List<DatePojo> dateList = new ArrayList<DatePojo>();

        int i ;
        if(calendar.getTime().getHours() > 21){
            i = 1;
        }else{
            i = 0;
        }
        for (; i < 7; i++) {
            Calendar c = new GregorianCalendar();
            c.add(Calendar.DATE, i);
            String[] date = dateFm.format(c.getTime()).split("-",2);
            dateList.add(new DatePojo(date[0],date[1]));
        }
        return dateList;
    }
}
