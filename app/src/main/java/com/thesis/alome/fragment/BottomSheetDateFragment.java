package com.thesis.alome.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thesis.alome.R;
import com.thesis.alome.adapter.DateListRcvAdapter;
import com.thesis.alome.config.DateTime;

public class BottomSheetDateFragment extends BottomSheetDialogFragment {

    RecyclerView rvDateList;
    DateListRcvAdapter dateListRcvAdapter;

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
