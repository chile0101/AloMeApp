package com.thesis.alome.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thesis.alome.R;
import com.thesis.alome.activity.ProviderDetailsActivity;
import com.thesis.alome.model.Provider;

import java.util.List;

public class ProviderListRcvAdapter extends RecyclerView.Adapter<ProviderListRcvAdapter.ViewHolder> {

    List<Provider> providersList;
    Context context;

    public ProviderListRcvAdapter(Context context, List<Provider> providersList){
        this.context = context;
        this.providersList = providersList;
    }

    @NonNull
    @Override
    public ProviderListRcvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_provider,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderListRcvAdapter.ViewHolder viewHolder, int i) {
        Provider provider = providersList.get(i);
        Picasso.get().load(provider.getAvatar()).into(viewHolder.imgAvatar);
        viewHolder.txtProviderName.setText(provider.getName());
        viewHolder.txtServiceName.setText(provider.getServiceName());
        viewHolder.txtNumOfRatings.setText("( " + provider.getNumOfRatings() + " đánh giá " + ")");
        viewHolder.ratingBar.setRating(3.67f);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProviderDetailsActivity.class);
                intent.putExtra("providerId",provider.getProviderId());
                intent.putExtra("providerName",provider.getName());
                intent.putExtra("providerAvatar",provider.getAvatar());
                intent.putExtra("providerStars",provider.getNumOfStars());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return providersList != null ? providersList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtProviderName;
        TextView txtServiceName;
        TextView txtNumOfRatings;
        RatingBar ratingBar;
        CardView cv ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            txtProviderName = (TextView) itemView.findViewById(R.id.txtName);
            txtServiceName = (TextView) itemView.findViewById(R.id.txtServiceName);
            txtNumOfRatings = (TextView) itemView.findViewById(R.id.txtNumOfRatings);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            cv = (CardView) itemView.findViewById(R.id.cvProvider);
        }
    }
}
