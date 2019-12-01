package com.thesis.alome.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.config.PrefUtils;
import com.thesis.alome.model.ProviderDetails;
import com.thesis.alome.model.RespBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderInfoFragment extends Fragment {

    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvGender) TextView tvGender;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.tvPhone) TextView tvPhone;
    @BindView(R.id.tvJob) TextView tvJob;
    @BindView(R.id.btnAloNow) Button btnAloNow;
    ProviderDetails provider;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_info, container, false);
        ButterKnife.bind(this,view);

        Long providerId = getArguments().getLong("providerId");
        String job = getArguments().getString("job");
        tvJob.setText(job);
        ApiServices apiServices = ApiClient.getClient(getActivity()).create(ApiServices.class);
        Call<RespBase<ProviderDetails>> call = apiServices.getProviderInfo(PrefUtils.getId(getActivity()),providerId,PrefUtils.getApiKey(getActivity()));
        call.enqueue(new Callback<RespBase<ProviderDetails>>() {
            @Override
            public void onResponse(Call<RespBase<ProviderDetails>> call, Response<RespBase<ProviderDetails>> response) {
                if(response.body() != null && response.body().getStatus() == true){
                    provider = response.body().getData();
                    tvName.setText(provider.getFullName());
                    tvAddress.setText(provider.getAddress());
                    tvPhone.setText(provider.getPhone());
                }
            }

            @Override
            public void onFailure(Call<RespBase<ProviderDetails>> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






        btnAloNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + provider.getPhone()));
                startActivity(intent);
            }
        });


    }
}
