package com.thesis.alome.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thesis.alome.R;
import com.thesis.alome.dao.DatePojo;
import com.thesis.alome.dao.Service;
import com.thesis.alome.fragment.BottomSheetTimeFragment;
import com.thesis.alome.viewmodel.StepViewModel;

import org.w3c.dom.Text;

import java.util.List;

public class DateListRcvAdapter extends RecyclerView.Adapter<DateListRcvAdapter.ViewHolder> {

    List<DatePojo> dateList;
    Context context;
    StepViewModel stepViewModel;
    Dialog dialog;

    public DateListRcvAdapter(List<DatePojo> dateList, Context context, Dialog dialog){
        this.dateList = dateList;
        this.context = context;
        this.dialog = dialog;
        stepViewModel = ViewModelProviders.of((AppCompatActivity)context).get(StepViewModel.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_available_date_list_type_date,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final DatePojo datePojo = dateList.get(i);
        viewHolder.tvDateOfWeek.setText(datePojo.getDayofweek());
        viewHolder.tvDate.setText(datePojo.getDateFormat());
        viewHolder.tvDateStatus.setText("Choose");

        viewHolder.cvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stepViewModel.setDateAvail(datePojo.getDayofweek() + " " + datePojo.getDateFormat());
                dialog.dismiss();
            }
        });

    }


    @Override
    public int getItemCount() {
        return dateList!= null ? dateList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDateOfWeek, tvDate, tvDateStatus;
        CardView cvDate;

        public ViewHolder(@NonNull View v) {
            super(v);

            tvDateOfWeek = (TextView) v.findViewById(R.id.tvDateOfWeek);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvDateStatus = (TextView) v.findViewById(R.id.tvDateStatus);
            cvDate = (CardView) v.findViewById(R.id.cvLayoutDate);
        }
    }
}
