package com.thesis.alome.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.activity.MapsActivity;
import com.thesis.alome.adapter.AddressListRcvAdapter;
import com.thesis.alome.model.Address;
import com.thesis.alome.viewmodel.AddressViewModel;
import com.thesis.alome.viewmodel.StepViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class BottomSheetAddressList extends BottomSheetDialogFragment {

    @BindView(R.id.btnAddAddress) Button btnAddAddress;
    @BindView(R.id.btnMyLocation) Button btnMyLocation;
    @BindView(R.id.addressListRcv) RecyclerView addressListRcv;
    @BindView(R.id.btnSelect) Button btnSelect;
    private AddressViewModel addressViewModel;
    private StepViewModel stepViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet_address_list,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addressViewModel = ViewModelProviders.of(this).get(AddressViewModel.class);
        stepViewModel = ViewModelProviders.of(getActivity()).get(StepViewModel.class);
        final AddressListRcvAdapter adapter = new AddressListRcvAdapter(getActivity(),addressViewModel);
        addressListRcv.setAdapter(adapter);
        addressListRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        addressViewModel.getAllAddress().observe(this, new Observer<List<Address>>() {
            @Override
            public void onChanged(@Nullable List<Address> addresses) {
                adapter.setAddressList(addresses);
            }
        });

        btnAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivityForResult(intent,1);
            }
        });

//        btnMyLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BottomSheetAddAddress bottomSheetAddAddress = new BottomSheetAddAddress();
//                bottomSheetAddAddress.show(getFragmentManager(),bottomSheetAddAddress.getTag());
//            }
//        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.addressSelected != null){
                    stepViewModel.setAddress(adapter.addressSelected.getAddressStr());
                    stepViewModel.setAddressLatLng(adapter.addressSelected.getLat()+";"+adapter.addressSelected.getLng());
                    dismiss();
                }else {
                    Toast.makeText(getActivity(), "Vui lòng chọn địa chỉ của bạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                stepViewModel.setAddress(data.getStringExtra("addressStr"));
                stepViewModel.setAddressLatLng(data.getStringExtra("addressLatLng"));
                BottomSheetAddAddress bsAddAddress = new BottomSheetAddAddress();
                bsAddAddress.show(getFragmentManager(),bsAddAddress.getTag());
                //dismiss();
            }
        }
    }

}
