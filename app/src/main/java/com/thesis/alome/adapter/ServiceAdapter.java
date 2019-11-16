package com.thesis.alome.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.thesis.alome.R;
import com.thesis.alome.activity.ServiceDetailActivity;
import com.thesis.alome.model.Service;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private Context context;
    List<Service> data;
    private IClickListener listener;
    private boolean mHorizontal;

    public ServiceAdapter(boolean horizontal,List<Service> data,Context context) {
        mHorizontal = horizontal;
        this.data = data;
        this.context=context;
    }

    public void setData(List<Service>data){
        this.data=data;
        notifyDataSetChanged();
    }

    public List<Service> getData(){
        return  data;
    }

        public void setListener(IClickListener listener) {
            this.listener = listener;
        }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_service,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Service service=data.get(position);
        if(service!=null){
            viewHolder.tvName.setText(service.getServiceName());
            viewHolder.tvFullPrice.setText(service.getFullPrice() + " VND");
            viewHolder.tvSalePrice.setText(service.getSalePrice() + " - " );
            if (service.getImageUrl()!=null && context!=null)
                 Glide.with(context).load(service.getImageUrl()).into(viewHolder.imgService);
            viewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(context.getApplicationContext(), ServiceDetailActivity.class);
                    intent.putExtra("serviceId", service.getId());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvFullPrice, tvSalePrice;
        ImageView imgService;
        CardView cv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            tvFullPrice=itemView.findViewById(R.id.tvFullPrice);
            tvSalePrice=itemView.findViewById(R.id.tvSalePrice);
            imgService=itemView.findViewById(R.id.imgService);
            cv= itemView.findViewById(R.id.cv);

        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface IClickListener {
        void onItemClick(Service service);
    }
}
