package com.thesis.alome.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.thesis.alome.R;
import com.thesis.alome.database.AppDatabase;
import com.thesis.alome.model.Address;
import com.thesis.alome.viewmodel.AddressViewModel;
import com.thesis.alome.viewmodel.StepViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetAddAddress extends BottomSheetDialogFragment {

    @BindView(R.id.btnSave) Button btnSave;
    @BindView(R.id.btnEditMap) Button btnEditMap;
    @BindView(R.id.edtTypeOfArea) EditText edtTypeOfArea;
    @BindView(R.id.edtHouseNo) EditText edtHouseNo;
    @BindView(R.id.edtAlleyRoad) EditText edtAlleyRoad;
    @BindView(R.id.edtDistricArea) EditText edtDistricArea;
    @BindView(R.id.edtProvince) EditText edtProvince;
    private AddressViewModel addressViewModel;
    private StepViewModel stepViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_add_address,container,false);
        ButterKnife.bind(this,view);
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);
        stepViewModel = ViewModelProviders.of(this).get(StepViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        stepViewModel.getAddress().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                edtProvince.setText(s);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typeOfArea = edtTypeOfArea.getText().toString();
                String houseNo = edtHouseNo.getText().toString();
                String alleyRoad = edtAlleyRoad.getText().toString();
                String districArea = edtDistricArea.getText().toString();
                String province = edtProvince.getText().toString();
                String addressStr = houseNo + " " + alleyRoad + " " + districArea + " " + province;

                Address address = new Address(typeOfArea,addressStr);
                AppDatabase appDb = AppDatabase.getInstance(getActivity());
                addressViewModel.insert(address);
                getDialog().dismiss();
            }
        });
    }

}
