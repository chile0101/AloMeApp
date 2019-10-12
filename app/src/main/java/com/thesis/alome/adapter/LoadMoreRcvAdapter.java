package com.thesis.alome.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thesis.alome.R;
import com.thesis.alome.activity.LoadMoreServices;
import com.thesis.alome.activity.ServiceDetailActivity;
import com.thesis.alome.dao.Service;

import java.util.List;

public class LoadMoreRcvAdapter extends RecyclerView.Adapter<LoadMoreRcvAdapter.ViewHolder> {

    List<Service> serviceList;
    Context context;

    public LoadMoreRcvAdapter(List<Service> serviceList){
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.item_services_fullwidth,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Service service = serviceList.get(i);
        Picasso.get().load(service.getImageUrl()).into(viewHolder.imgService);
        viewHolder.tvName.setText(service.getServiceName());
        viewHolder.tvSalePrice.setText("$" + service.getSalePrice());
        viewHolder.tvFullPrice.setText("$" + service.getFullPrice());
        viewHolder.tvFullPrice.setPaintFlags(viewHolder.tvFullPrice.getPaintFlags()
                | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(context.getApplicationContext(), ServiceDetailActivity.class);
                intent.putExtra("serviceId",service.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList != null ? serviceList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgService;
        TextView tvName,tvFullPrice,tvSalePrice;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgService = (ImageView) itemView.findViewById(R.id.imgService);
            tvName = (TextView) itemView.findViewById(R.id.tvNameService);
            tvFullPrice = (TextView) itemView.findViewById(R.id.tvFullPrice);
            tvSalePrice = (TextView) itemView.findViewById(R.id.tvSalePrice);
            cv = (CardView) itemView.findViewById(R.id.cvServiceMaxWidth);
        }
    }
}
