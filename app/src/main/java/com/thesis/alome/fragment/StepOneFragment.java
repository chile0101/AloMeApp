package com.thesis.alome.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.activity.StepActivity;
import com.thesis.alome.config.FragmentLifecycle;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.viewmodel.StepViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepOneFragment extends Fragment  implements FragmentLifecycle {

    @BindView(R.id.edtDate) EditText edtDate;
    @BindView(R.id.edtTime) EditText edtTime;
    @BindView(R.id.edtPhone) EditText edtPhone;
    @BindView(R.id.edtAddress) EditText edtAddress;
    @BindView(R.id.edtType) EditText edtType;
    @BindView(R.id.linearLayoutType) LinearLayout linearLayoutType;

    @BindView(R.id.tvErrorDate) TextView tvErrorDate;
    @BindView(R.id.tvErrorTime) TextView tvErrorTime;
    @BindView(R.id.tvErrorPhone) TextView tvErrorPhone;
    @BindView(R.id.tvErrorAddress) TextView tvErrorAddress;
    @BindView(R.id.tvErrorType) TextView tvErrorType;

    private StepViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(StepViewModel.class);
        model.setAddress(PrefUtils.getAddress(getActivity()));
        model.setAddressLatLng(PrefUtils.getLatitude(getActivity()) + ";" + PrefUtils.getLongitude(getActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_one,container,false);
        ButterKnife.bind(this,view);

        Intent intent = getActivity().getIntent();
        if(intent != null){
            String fromActivity = intent.getExtras().getString("Uniqid");
            if(fromActivity.equals("From_Activity_Main")){
                linearLayoutType.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

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
        model.getPhone().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                edtPhone.setText(s);
                edtPhone.setSelection(edtPhone.getText().length());
            }
        });
        model.getAddress().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                edtAddress.setText(s);
            }
        });

        model.getTypeStr().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String str) {
                edtType.setText(str);
            }
        });

        model.getDateErr().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvErrorDate.setText(s);
            }
        });

        model.getTimeErr().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvErrorTime.setText(s);
            }
        });

        model.getPhoneErr().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvErrorPhone.setText(s);
            }
        });

        model.getAddressErr().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvErrorAddress.setText(s);
            }
        });

        edtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvErrorDate.setText("");
            }
        });

        edtTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                tvErrorTime.setText("");
            }
        });

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                //model.setPhone(s.toString());
                if(!s.toString().equals("")){
                    tvErrorPhone.setText("");
                }
            }
        });

        edtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                tvErrorAddress.setText("");
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

       edtType.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               BottomSheetTypeServicesListFragment bs = new BottomSheetTypeServicesListFragment();
               bs.show(getFragmentManager(), bs.getTag());
           }
       });

    }

    @Override
    public void onPauseFragment() {
        model.setPhone(edtPhone.getText().toString());
    }

    @Override
    public void onResumeFragment() { }
}
