package com.thesis.alome.adapter;

import android.content.Context;
import android.media.Image;
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
import com.thesis.alome.model.Provider;

import java.util.List;

public class ProviderListRcvAdapter extends RecyclerView.Adapter<ProviderListRcvAdapter.ViewHolder> {

    List<Provider> providersList;
    Context context;

    public ProviderListRcvAdapter(List<Provider> providersList){
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
        Picasso.get().load(provider.getImageUrl()).into(viewHolder.imgAvatar);
        viewHolder.txtName.setText(provider.getNameProvider());
        viewHolder.txtRating.setText("( " + provider.getNumOfRatings() + " đánh giá " + ")");
        viewHolder.ratingBar.setRating(provider.getNumOfStars());
    }

    @Override
    public int getItemCount() {
        return providersList != null ? providersList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtName;
        TextView txtRating;
        RatingBar ratingBar;
        CardView cv ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtRating = (TextView) itemView.findViewById(R.id.txtRating);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            cv = (CardView) itemView.findViewById(R.id.cvProvider);
        }
    }
}
