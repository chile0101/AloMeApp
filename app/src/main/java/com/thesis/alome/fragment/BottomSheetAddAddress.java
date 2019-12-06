package com.thesis.alome.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.thesis.alome.R;
import com.thesis.alome.activity.MapsActivity;
import com.thesis.alome.database.AppDatabase;
import com.thesis.alome.model.Address;
import com.thesis.alome.viewmodel.AddressViewModel;
import com.thesis.alome.viewmodel.StepViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomSheetAddAddress extends BottomSheetDialogFragment {

    @BindView(R.id.btnEditMap) Button btnEditMap;
    @BindView(R.id.ivClose) ImageView ivClose;
    @BindView(R.id.btnSave) Button btnSave;
    @BindView(R.id.edtWard) EditText edtWard;
    @BindView(R.id.edtAlleyRoad) EditText edtAlleyRoad;
    @BindView(R.id.edtDistricArea) EditText edtDistricArea;
    @BindView(R.id.edtProvince) EditText edtProvince;
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
        stepViewModel = ViewModelProviders.of(getActivity()).get(StepViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        String address = stepViewModel.getAddress().getValue();
        String[] addressSlices = address.split(",",6);
        int len = addressSlices.length;

        if( addressSlices[len-2] != null){
            edtProvince.setText(addressSlices[len-2]);
        }
        if(addressSlices[len-3] != null){
            edtDistricArea.setText(addressSlices[len-3]);
        }
        if(addressSlices[len-4] != null){
            edtWard.setText(addressSlices[len-4]);
        }
        if(addressSlices[len-5] != null){
            edtAlleyRoad.setText(addressSlices[len-5]);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String alleyRoad = edtAlleyRoad.getText().toString();
                String ward = edtWard.getText().toString();
                String districArea = edtDistricArea.getText().toString();
                String province = edtProvince.getText().toString();

                if(  !validateAlleyRoad(alleyRoad)
                        | !validateDistricArea(districArea) | !validateProvince(province)){
                    return;
                }else {
                    String title = alleyRoad;
                    String addressStr =  alleyRoad + " " + ward + " " + districArea + " " + province;
                    String addressLatLng = stepViewModel.getAddressLatLng().getValue();
                    String[] slices = addressLatLng.split(";", 2);
                    Address address = new Address(title,addressStr,Double.parseDouble(slices[0]),Double.parseDouble(slices[1]));
                    AppDatabase appDb = AppDatabase.getInstance(getActivity());
                    addressViewModel.insert(address);
                    getDialog().dismiss();
                }
            }
        });

//        btnEditMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MapsActivity.class);
//                intent.putExtra("addressEdited","Hoang Hoa Tham");
//                startActivityForResult(intent,2);
//            }
//        });


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
