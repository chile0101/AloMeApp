package com.thesis.alome.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.thesis.alome.R;
import com.thesis.alome.viewmodel.StepViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepOneFragment extends Fragment {

    @BindView(R.id.edtDate) EditText edtDate;
    @BindView(R.id.edtAddress) EditText edtAddress;
    @BindView(R.id.edtTime) EditText edtTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_one,container,false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        StepViewModel model = ViewModelProviders.of(getActivity()).get(StepViewModel.class);
        model.getDateAvail().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                edtDate.setText(s);
            }
        });
        model.getTimeAvail().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                edtTime.setText(s);
            }
        });
        model.getAddress().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                edtAddress.setText(s);
            }
        });

       edtDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               BottomSheetDateFragment botSheetDateFragment = new BottomSheetDateFragment();
               botSheetDateFragment.show(getFragmentManager(), botSheetDateFragment.getTag());
           }
       });

       edtTime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               BottomSheetTimeFragment bottomSheetTimeFragment = new BottomSheetTimeFragment();
               bottomSheetTimeFragment.show(getFragmentManager(), bottomSheetTimeFragment.getTag());
           }
       });

       edtAddress.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               BottomSheetAddressList bottomSheetAddressList = new BottomSheetAddressList();
               bottomSheetAddressList.show(getFragmentManager(), bottomSheetAddressList.getTag());
           }
       });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date",edtDate.getText().toString());
        outState.putString("time",edtTime.getText().toString());
        outState.putString("address",edtAddress.getText().toString());
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            Log.d("instance","ok??");
            edtDate.setText(savedInstanceState.getString("date"));
        }
    }
}
