package com.thesis.alome.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.thesis.alome.R;
import com.thesis.alome.model.RatingForProvider;

import java.util.List;

public class RatingsForProviderAdapter extends RecyclerView.Adapter<RatingsForProviderAdapter.ViewHolder> {

    List<RatingForProvider> list;
    Context context;

    public RatingsForProviderAdapter(List<RatingForProvider> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_comment_type_comment,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RatingForProvider rating = list.get(i);
        viewHolder.tvName.setText(rating.getNameAssessor());
        viewHolder.tvComment.setText(rating.getTextComment());
        viewHolder.ratingBar.setRating(rating.getNumOfStars());
        viewHolder.tvCreateAt.setText(rating.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvComment,tvCreateAt;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName) ;
            tvComment = (TextView) itemView.findViewById(R.id.tvComment);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            tvCreateAt = (TextView) itemView.findViewById(R.id.tvCreateAt);
        }
    }
}
