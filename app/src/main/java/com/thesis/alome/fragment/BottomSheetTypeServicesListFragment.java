package com.thesis.alome.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.adapter.DateListRcvAdapter;
import com.thesis.alome.adapter.TypeListRcvAdapter;
import com.thesis.alome.config.ApiClient;
import com.thesis.alome.config.ApiServices;
import com.thesis.alome.model.RespBase;
import com.thesis.alome.model.ServiceType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomSheetTypeServicesListFragment extends BottomSheetDialogFragment {
    RecyclerView recyclerView;
    TypeListRcvAdapter adapter;
    List<ServiceType> typesList;

    public BottomSheetTypeServicesListFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet_type_services,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.rvTypeList);

        ApiServices apiServices = ApiClient.getClient(getActivity()).create(ApiServices.class);
        Call<RespBase<List<ServiceType>>> call = apiServices.getMainData();
        call.enqueue(new Callback<RespBase<List<ServiceType>>>() {
            @Override
            public void onResponse(Call<RespBase<List<ServiceType>>> call, Response<RespBase<List<ServiceType>>> response) {
                typesList = response.body().getData();
                adapter = new TypeListRcvAdapter(typesList, getActivity(), getDialog());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RespBase<List<ServiceType>>> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.please_check_the_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
