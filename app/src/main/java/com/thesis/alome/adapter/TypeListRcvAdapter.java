package com.thesis.alome.adapter;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thesis.alome.R;
import com.thesis.alome.model.ServiceType;
import com.thesis.alome.viewmodel.StepViewModel;

import java.util.List;

public class TypeListRcvAdapter extends RecyclerView.Adapter<TypeListRcvAdapter.ViewHolder> {

    List<ServiceType> typeList;
    Context context;
    StepViewModel stepViewModel;
    Dialog dialog;

    public TypeListRcvAdapter(List<ServiceType> typeList, Context context, Dialog dialog) {
        this.typeList = typeList;
        this.context = context;
        stepViewModel = ViewModelProviders.of((AppCompatActivity)context).get(StepViewModel.class);
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_type_list_service,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ServiceType serviceType = typeList.get(i);
        viewHolder.txtType.setText(serviceType.getTypeName());
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepViewModel.setTypeId((Long) serviceType.getId());
                stepViewModel.setTypeStr(serviceType.getTypeName());
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeList != null ? typeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtType;
        CardView cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtType = itemView.findViewById(R.id.txtType);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}
