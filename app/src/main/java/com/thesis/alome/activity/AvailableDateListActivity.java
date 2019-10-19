package com.thesis.alome.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thesis.alome.R;
import com.thesis.alome.adapter.DateListRcvAdapter;
import com.thesis.alome.dao.DatePojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AvailableDateListActivity extends AppCompatActivity {

    RecyclerView rvDateList;
    List<DatePojo> dateList = new ArrayList<DatePojo>();
    DateListRcvAdapter dateListRcvAdapter;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_date_list);

        rvDateList = findViewById(R.id.rvDateList);
        dateList.add(new DatePojo("Flexible","Flexible"));

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy", Locale.ENGLISH);
        for (int i = 0; i < 7; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String[] date = sdf.format(calendar.getTime()).split(" ",2);
            dateList.add(new DatePojo(date[0],date[1]));
        }

        dateListRcvAdapter = new DateListRcvAdapter(dateList);
        rvDateList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvDateList.setItemAnimator(new DefaultItemAnimator());
        rvDateList.setAdapter(dateListRcvAdapter);
    }
}
