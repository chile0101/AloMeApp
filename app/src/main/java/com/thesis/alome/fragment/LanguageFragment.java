package com.thesis.alome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thesis.alome.R;
import com.thesis.alome.activity.MainActivity;
import com.thesis.alome.activity.SettingActivity;
import com.thesis.alome.config.LocaleManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageFragment extends Fragment {

    @BindView(R.id.layoutEnglish) LinearLayout layoutEnglish;
    @BindView(R.id.layoutTiengViet) LinearLayout layoutTiengViet;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SettingActivity)getActivity()).setToolBarTitle("Language");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewLocale((AppCompatActivity) getActivity(),LocaleManager.ENGLISH);
            }
        });
        layoutTiengViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewLocale((AppCompatActivity) getActivity(),LocaleManager.VIETNAMESE);
            }
        });
    }

    private void setNewLocale(AppCompatActivity mContext, @LocaleManager.LocaleDef String language) {
        LocaleManager.setNewLocale(getActivity(), language);
        Intent intent = new Intent(mContext,MainActivity.class);
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

}
