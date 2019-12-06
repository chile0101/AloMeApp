package com.thesis.alome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thesis.alome.R;
import com.thesis.alome.activity.LandingActivity;
import com.thesis.alome.activity.SettingActivity;
import com.thesis.alome.config.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingFragment extends Fragment {

    @BindView(R.id.layoutLang) LinearLayout layoutLang;
    @BindView(R.id.layoutNoti) LinearLayout layoutNoti;
    @BindView(R.id.layoutLogout) LinearLayout layoutLogout;
    private FragmentTransaction ft;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SettingActivity)getActivity()).setToolBarTitle(getString(R.string.setting));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanguageFragment langFragment = new LanguageFragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.contentContainer,langFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        layoutNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationSettingFragment notiFragment =new NotificationSettingFragment();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.contentContainer,notiFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PrefUtils.clearAll(getActivity())){
                    Intent intent = new Intent(getActivity(), LandingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}
