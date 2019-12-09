package com.thesis.alome.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.thesis.alome.R;

import java.util.List;

public class TypeJobRcvAdapter extends RecyclerView.Adapter<TypeJobRcvAdapter.ViewHolder> {

    private List<String> typeList;
    private Context context;
    private RadioButton lastCheckedRB = null;

    public TypeJobRcvAdapter(Context context,List<String> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @NonNull
    @Override
    public TypeJobRcvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View itemView = mInflater.inflate(R.layout.item_type_job_status,viewGroup,false);
        return new TypeJobRcvAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final String current = typeList.get(i);
        viewHolder.tvName.setText(current);

        viewHolder.layoutWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton checkedRB =  viewHolder.rb;
                if(lastCheckedRB != null){
                    lastCheckedRB.setChecked(false);
                }
                lastCheckedRB = checkedRB;
                lastCheckedRB.setChecked(true);
                Integer typeJobValue;
                switch (i){
                    case 1:
                        typeJobValue = 100; break;
                    case 2:
                        typeJobValue = 200; break;
                    case 3:
                        typeJobValue = 300; break;
                    case 4:
                        typeJobValue = 401; break;
                    default: typeJobValue = 0;
                }

                Intent returnIntent = new Intent();
                returnIntent.putExtra("typeJobValue",typeJobValue);
                returnIntent.putExtra("typeJobName",current);
                ((AppCompatActivity)context).setResult(Activity.RESULT_OK,returnIntent);
                ((AppCompatActivity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeList != null ? typeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private RadioButton rb;
        private LinearLayout layoutWrapper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            rb = (RadioButton) itemView.findViewById(R.id.rb);
            layoutWrapper= (LinearLayout) itemView.findViewById(R.id.layoutWrapper);
        }
    }
}
