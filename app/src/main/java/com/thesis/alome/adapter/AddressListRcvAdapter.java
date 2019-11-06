package com.thesis.alome.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
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
    private AddressViewModel addressViewModel;
    private RadioButton lastCheckedRB = null;
    public Address addressSelected = null;


    public AddressListRcvAdapter(Context context, AddressViewModel addressViewModel) {
        mInflater = LayoutInflater.from(context);
        this.addressViewModel = addressViewModel;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.item_address_list_type_address,viewGroup,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Address current = addressList.get(i);
        viewHolder.tvTitle.setText(current.getTypeOfArea());
        viewHolder.tvFullAddress.setText(current.getAddressStr());
        viewHolder.ivDeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(current, i);
            }
        });

        viewHolder.layoutAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton checkedRB =  viewHolder.rbPropertyType;
                if(lastCheckedRB != null){
                    lastCheckedRB.setChecked(false);
                }
                lastCheckedRB = checkedRB;
                lastCheckedRB.setChecked(true);
                addressSelected = current;
            }
        });
    }

    public void setAddressList(List<Address> addressList){
        this.addressList = addressList;
        notifyDataSetChanged();
    }

    public void removeItem(Address address,int i){
        this.addressList.remove(address);
        addressViewModel.delete(address);
        notifyItemRemoved(i);
    }

    @Override
    public int getItemCount() {
        return addressList!= null ? addressList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rbPropertyType) RadioButton rbPropertyType;
        @BindView(R.id.layoutAddress) ConstraintLayout layoutAddress;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvFullAddress) TextView tvFullAddress;
        @BindView(R.id.ivDeleteAddress) ImageView ivDeleteAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
