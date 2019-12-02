package com.thesis.alome.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thesis.alome.R;
import com.thesis.alome.activity.JobDetailsActivity;
import com.thesis.alome.activity.JobDetailsCompletedActivity;
import com.thesis.alome.model.Job;

import java.util.List;

public class JobListRcvAdapter extends RecyclerView.Adapter<JobListRcvAdapter.ViewHolder> {

    List<Job> jobList;
    Context context;
    String TAG;

    public JobListRcvAdapter(List<Job> jobList, Context context, String tag){
        this.jobList = jobList;
        this.context = context;
        this.TAG = tag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_job,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Job job = jobList.get(i);
        Picasso.get().load(job.getUrlImg()).into(viewHolder.img);
        viewHolder.txtNameService.setText(job.getServiceName());
        viewHolder.txtDate.setText(context.getString(R.string.appointment_date) + " " + job.getDate());
        TextView btnStatus = (TextView) viewHolder.txtStatus;
        switch (job.getStatus()){
            case 100:
                btnStatus.setText(context.getString(R.string.sent_request));
                break;
            case 200 :
                btnStatus.setText(context.getString(R.string.wait_for_confirmation));
                break;
            case 300:
                btnStatus.setText(context.getString(R.string.contact_success));
                break;
            case 400:
                btnStatus.setText(context.getString(R.string.completed));
                //btnStatus.setTextColor(ContextCompat.getColor(context,R.color.dark_transparent));
                //btnStatus.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_btn_job_status_expired));
                break;
            default:
                btnStatus.setVisibility(View.GONE);
        }

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (TAG) {
                    case "inProgress":
                        Intent progIntent = new Intent(context, JobDetailsActivity.class);
                        progIntent.putExtra("customerRequestId", String.valueOf(job.getId()));
                        progIntent.putExtra("status", job.getStatus().toString());
                        //                intent.putExtra("providerId","1");
                        progIntent.putExtra("serviceName", job.getServiceName());
                        context.startActivity(progIntent);
                        break;
                    case "inCompleted":
                        Intent compIntent = new Intent(context, JobDetailsCompletedActivity.class);
                        compIntent.putExtra("customerRequestId", String.valueOf(job.getId()));
                        compIntent.putExtra("status", job.getStatus().toString());
                        compIntent.putExtra("serviceName", job.getServiceName());
                        context.startActivity(compIntent);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList != null ? jobList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtNameService,txtDate,txtStatus;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            txtNameService = itemView.findViewById(R.id.tvServiceName);
            txtDate =  itemView.findViewById(R.id.tvDate);
            txtStatus = itemView.findViewById(R.id.tvStatus);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}
