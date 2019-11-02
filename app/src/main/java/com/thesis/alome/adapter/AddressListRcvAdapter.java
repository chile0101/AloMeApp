package com.thesis.alome.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.thesis.alome.R;
import com.thesis.alome.model.Address;
import com.thesis.alome.viewmodel.AddressViewModel;
import com.thesis.alome.viewmodel.StepViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressListRcvAdapter extends RecyclerView.Adapter<AddressListRcvAdapter.ViewHolder> {


    private List<Address> addressList;
    private final LayoutInflater mInflater;


    public AddressListRcvAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.item_address_list_type_address,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Address current = addressList.get(i);
        viewHolder.tvTitle.setText(current.getTypeOfArea());
        viewHolder.tvFullAddress.setText(current.getAddressStr());
    }

    public void setAddressList(List<Address> addressList){
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return addressList!= null ? addressList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rbPropertyType)
        RadioButton rbPropertyType;
        @BindView(R.id.layoutAddress)
        LinearLayout layoutAddress;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvFullAddress)
        TextView tvFullAddress;
        @BindView(R.id.ivDeleteAddress)
        ImageView ivDeleteAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
