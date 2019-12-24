package com.thesis.alome.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.thesis.alome.R;
import com.thesis.alome.activity.LoadMoreServices;
import com.thesis.alome.model.ServiceType;
import java.util.ArrayList;

public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.ViewHolder> implements GravitySnapHelper.SnapListener {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private ArrayList<ServiceType> mSnaps = new ArrayList<>();
    private Context context;
    // Disable touch detection for parent recyclerView if we use vertical nested recyclerViews
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    };

    public SnapAdapter(Context context) {
        this.context = context;
    }

    public SnapAdapter() {
        mSnaps = new ArrayList<>();
    }

    public void addSnap(ServiceType snap) {
        mSnaps.add(snap);
    }

    @Override
    public int getItemViewType(int position) {
        return HORIZONTAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_snap, parent, false);

        if (viewType == VERTICAL) {
            view.findViewById(R.id.recyclerView).setOnTouchListener(mTouchListener);
        }

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ServiceType snap = mSnaps.get(position);
        holder.snapTextView.setText(snap.getTypeName());
        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), LoadMoreServices.class);
                intent.putExtra("typeId", snap.getId());
                intent.putExtra("titleService", snap.getTypeName());
                context.startActivity(intent);
            }
        });

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setOnFlingListener(null);
        new LinearSnapHelper().attachToRecyclerView(holder.recyclerView);
        ServiceAdapter serviceAdapter = new ServiceAdapter(true ,snap.getServices(),context);

        holder.recyclerView.setAdapter(serviceAdapter);
    }

    @Override
    public int getItemCount() {
        return mSnaps.size();
    }

    @Override
    public void onSnap(int position) {
        //Log.d("Snapped: ", position + "");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView snapTextView;
        public RecyclerView recyclerView;
        public ImageView imgMore;

        public ViewHolder(View itemView) {
            super(itemView);
            snapTextView = (TextView) itemView.findViewById(R.id.snapTextView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
            imgMore = itemView.findViewById(R.id.imgMore);
        }

    }

    public ArrayList<ServiceType> getmSnaps() {
        return mSnaps;
    }

    public void setmSnaps(ArrayList<ServiceType> mSnaps) {
        this.mSnaps = mSnaps;
    }
}


