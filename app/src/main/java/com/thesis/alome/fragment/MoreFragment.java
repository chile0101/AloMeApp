package com.thesis.alome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thesis.alome.activity.ProfileActivity;
import com.thesis.alome.R;
import com.thesis.alome.activity.MainActivity;
import com.thesis.alome.activity.SettingActivity;
import com.thesis.alome.config.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreFragment extends Fragment {

    @BindView(R.id.imgAvatar) ImageView imgAvatar;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.editProfileWrapper) LinearLayout editProfileWrapper;
    @BindView(R.id.layoutSetting) LinearLayout layoutSetting;
    @BindView(R.id.layoutAboutus) LinearLayout layoutAboutus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity)getActivity()).setTitleToolBar(getString(R.string.more));
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more,container,false);
        ButterKnife.bind(this,view);
        String imgUrl = PrefUtils.getAvatar(getActivity());
        if(!imgUrl.equals("")){
            Picasso.get().load(imgUrl).into(imgAvatar);
        }
        tvName.setText(PrefUtils.getFullName(getActivity()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editProfileWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}

