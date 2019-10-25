package com.thesis.alome.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.adapter.DateListRcvAdapter;
import com.thesis.alome.config.DateTime;
import com.thesis.alome.dao.DatePojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class BottomSheetDateFragment extends BottomSheetDialogFragment {


    RecyclerView rvDateList;

    DateListRcvAdapter dateListRcvAdapter;

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.d("check", "onAttach");
//    }


    public BottomSheetDateFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet_date,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        rvDateList = (RecyclerView) view.findViewById(R.id.rvDateList);


        dateListRcvAdapter = new DateListRcvAdapter(DateTime.getNextDays(),getActivity(),getDialog());
        rvDateList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDateList.setItemAnimator(new DefaultItemAnimator());
        rvDateList.setAdapter(dateListRcvAdapter);



    }
}
