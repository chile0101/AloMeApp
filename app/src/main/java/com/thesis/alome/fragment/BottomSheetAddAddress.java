package com.thesis.alome.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    @BindView(R.id.edtWard) EditText edtWard;
    @BindView(R.id.edtHouseNo) EditText edtHouseNo;
    @BindView(R.id.edtAlleyRoad) EditText edtAlleyRoad;
    @BindView(R.id.edtDistricArea) EditText edtDistricArea;
    @BindView(R.id.edtProvince) EditText edtProvince;
    @BindView(R.id.tvErrorHouseNo) TextView tvErrorHouseNo;
    @BindView(R.id.tvErrorAlleyRoad) TextView tvErrorAlleyRoad;
    @BindView(R.id.tvErrorWard) TextView tvErrorWard;
    @BindView(R.id.tvErrorDistricArea) TextView tvErrorDistricArea;
    @BindView(R.id.tvErrorProvince) TextView tvErrorProvince;

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

                String houseNo = edtHouseNo.getText().toString();
                String alleyRoad = edtAlleyRoad.getText().toString();
                String ward = edtWard.getText().toString();
                String districArea = edtDistricArea.getText().toString();
                String province = edtProvince.getText().toString();

                if( !validateHouseNo(houseNo) | !validateAlleyRoad(alleyRoad)
                        | !validateDistricArea(districArea) | !validateProvince(province)){
                    return;
                }else {
                    String title = houseNo + " " + alleyRoad;
                    String addressStr = houseNo + " " + alleyRoad + " " + districArea + " " + province;
                    Address address = new Address(title,addressStr);
                    AppDatabase appDb = AppDatabase.getInstance(getActivity());
                    addressViewModel.insert(address);
                    getDialog().dismiss();
                }
            }
        });

        edtHouseNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvErrorHouseNo.setText("");
            }
        });

        edtAlleyRoad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvErrorAlleyRoad.setText("");
            }
        });

        edtDistricArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvErrorDistricArea.setText("");
            }
        });

        edtProvince.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvErrorProvince.setText("");
            }
        });
    }

    private boolean validateHouseNo(String houseNo){
        if(houseNo.isEmpty()){
            tvErrorHouseNo.setText(R.string.validate_house_no_text);
            return false;
        }
        return true;
    }
    private boolean validateAlleyRoad(String alleyRoad){
        if(alleyRoad.isEmpty()){
            tvErrorAlleyRoad.setText(R.string.validate_alley_road_text);
            return false;
        }
        return true;
    }
    private boolean validateWard(String ward){
        if(ward.isEmpty()){
            tvErrorWard.setText(R.string.validate_ward_text);
            return false;
        }
        return true;
    }
    private boolean validateDistricArea(String districArea){
        if(districArea.isEmpty()){
            tvErrorDistricArea.setText(R.string.validate_district_area_text);
            return false;
        }
        return true;
    }
    private boolean validateProvince(String province){
        if(province.isEmpty()){
            tvErrorProvince.setText(R.string.validate_province_text);
            return false;
        }
        return true;
    }


}
