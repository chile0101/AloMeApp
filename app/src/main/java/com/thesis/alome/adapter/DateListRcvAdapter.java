package com.thesis.alome.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thesis.alome.R;
import com.thesis.alome.dao.DatePojo;
import com.thesis.alome.dao.Service;

import org.w3c.dom.Text;

import java.util.List;

public class DateListRcvAdapter extends RecyclerView.Adapter<DateListRcvAdapter.ViewHolder> {

    List<DatePojo> dateList;
    Context context;

    public DateListRcvAdapter(List<DatePojo> dateList){
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_available_date_list_type_date,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DatePojo datePojo = dateList.get(i);
        viewHolder.tvDateOfWeek.setText(datePojo.getDayofweek());
        viewHolder.tvDate.setText(datePojo.getDateFormat());
        viewHolder.tvDateStatus.setText("Choose");
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
            cvDate = (CardView) itemView.findViewById(R.id.cvServiceMaxWidth);
        }
    }
}
