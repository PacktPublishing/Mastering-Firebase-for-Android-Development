package com.ashok.packt.realtime.database.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashok.packt.realtime.database.R;
import com.ashok.packt.realtime.database.model.Donor;

import java.util.List;

/**
 * Created by ashok.kumar on 20/10/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.View_Holder>{

    private Context mContext;
    private List<Donor> ItemList;

    public RecyclerViewAdapter(Context mContext, List<Donor> itemList) {
        this.mContext = mContext;
        ItemList = itemList;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donor_list_row, parent, false);
        return new View_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {

        Donor Item = ItemList.get(position);
        holder.Name.setText(Item.getFullName());
        holder.City.setText(Item.getCity());
        holder.BloodGroup.setText(Item.getBloodGroup());
        holder.Email.setText(Item.getEmail());

    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        TextView Name;
        TextView City;
        TextView BloodGroup;
        TextView Phone;
        TextView Email;

        View_Holder(View itemView) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.donorName);
            City = (TextView) itemView.findViewById(R.id.donorCity);
            BloodGroup = (TextView) itemView.findViewById(R.id.donorBloodGroup);
            Email = (TextView) itemView.findViewById(R.id.donorEmail);
        }
    }

}
