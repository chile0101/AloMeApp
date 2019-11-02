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
import com.thesis.alome.model.ServiceType;

import java.util.List;

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.ViewHolder> {

    List<ServiceType> serviceTypeList;
    Context context;


    public MainRecycleAdapter(List<ServiceType> serviceTypeList){
        this.serviceTypeList = serviceTypeList;
    }

    @NonNull
    @Override
    public MainRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_services_type_promotion,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MainRecycleAdapter.ViewHolder viewHolder, final int i) {

        final ServiceType serviceType = (ServiceType) serviceTypeList.get(i);
        if(serviceType != null){
            viewHolder.tvName.setText(serviceType.getTypeName());
            if(serviceType.getServices().size() != 0){
                Picasso.get().load(serviceType.getServices().get(0).getImageUrl()).into(viewHolder.img1);
                viewHolder.tvName1.setText(serviceType.getServices().get(0).getServiceName());
                viewHolder.tvSalePrice1.setText("$" + serviceType.getServices().get(0).getSalePrice());
                viewHolder.tvFullPrice1.setText("$" + serviceType.getServices().get(0).getFullPrice());
                viewHolder.tvFullPrice1.setPaintFlags(viewHolder.tvFullPrice1.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.cv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =  new Intent(context.getApplicationContext(), ServiceDetailActivity.class);
                        intent.putExtra("serviceId", serviceType.getServices().get(0).getId());
                        context.startActivity(intent);
                             }
                });
            }

            if(serviceType.getServices().size() != 1){
                Picasso.get().load(serviceType.getServices().get(1).getImageUrl()).into(viewHolder.img2) ;
                viewHolder.tvName2.setText(serviceType.getServices().get(1).getServiceName());
                viewHolder.tvSalePrice2.setText("$" + serviceType.getServices().get(1).getSalePrice());
                viewHolder.tvFullPrice2.setText("$" + serviceType.getServices().get(1).getFullPrice());
                viewHolder.tvFullPrice2.setPaintFlags(viewHolder.tvFullPrice2.getPaintFlags()
                        | Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.cv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =  new Intent(context.getApplicationContext(), ServiceDetailActivity.class);
                        intent.putExtra("serviceId", serviceType.getServices().get(1).getId());
                        context.startActivity(intent);
                    }
                });
            }
            viewHolder.tvLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), LoadMoreServices.class);
                    intent.putExtra("typeId", serviceType.getId());
                    intent.putExtra("titleService", serviceType.getTypeName());
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return serviceTypeList != null ? serviceTypeList.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img1,img2;
        TextView tvName, tvName1,tvName2,tvSalePrice1,tvSalePrice2,tvFullPrice1,tvFullPrice2,tvLoadMore;
        CardView cv1,cv2;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvPromotionsTitle);
            img1 = (ImageView) view.findViewById(R.id.imgService1);
            img2 = (ImageView) view.findViewById(R.id.imgService2);
            tvName1 = (TextView) view.findViewById(R.id.tvNameService1);
            tvName2 = (TextView) view.findViewById(R.id.tvNameService2);
            tvSalePrice1 = (TextView) view.findViewById(R.id.tvSalePrice1);
            tvSalePrice2 = (TextView) view.findViewById(R.id.tvSalePrice2);
            tvFullPrice1 = (TextView) view.findViewById(R.id.tvFullPrice1);
            tvFullPrice2 = (TextView) view.findViewById(R.id.tvFullPrice2);
            tvLoadMore = (TextView) view.findViewById(R.id.tvLoadMore);
            cv1 = (CardView) view.findViewById(R.id.cv1);
            cv2 = (CardView) view.findViewById(R.id.cv2);
        }
    }
}
