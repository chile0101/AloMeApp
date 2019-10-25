package com.thesis.alome.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.config.DateTime;
import com.thesis.alome.viewmodel.StepViewModel;

public class BottomSheetTimeFragment extends BottomSheetDialogFragment {

    GridView gridView;

    public BottomSheetTimeFragment(){}

    @Override
    public void onStart() {
        super.onStart();
//        if (getDialog() != null) {
//            getDialog().getWindow().setWindowAnimations(R.style.BottomSheetSlideRightStyle);
//        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_bottom_sheet_time,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final StepViewModel model = ViewModelProviders.of(getActivity()).get(StepViewModel.class);

        gridView = (GridView) view.findViewById(R.id.gridview);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.item_time_for_gridview,R.id.txt_item_time, DateTime.getNextHours());
        gridView.setAdapter(arrayAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = gridView.getItemAtPosition(position);
                String hour = (String) o;
               // Toast.makeText(getActivity(), "Selected" + hour, Toast.LENGTH_SHORT).show();

                view.setBackgroundResource(R.drawable.item_time_selected);
                model.setTimeAvail(hour);
                getDialog().dismiss();
            }
        });
    }
}
