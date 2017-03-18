package com.fasylgh.fasylgse.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasylgh.fasylgse.R;
import com.fasylgh.fasylgse.model.Stork;

import java.util.Collections;
import java.util.List;

/**
 * Created by edem on 18/03/17.
 */

public class StorksRecyclerAdapter extends RecyclerView.Adapter<StorksRecyclerAdapter.StorksViewHolder> {


    List<Stork> list = Collections.EMPTY_LIST;

    public StorksRecyclerAdapter(List<Stork> list) {
        this.list = list;
    }

    @Override
    public StorksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stork_item, parent, false);

        return new StorksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StorksViewHolder holder, int position) {
        Stork item = list.get(position);
        StorksViewHolder vh = holder;
        vh.storkNameTV.setText(item.getName());
        vh.storkPriceTV.setText(item.getPrice());
        vh.storkChangeValueTV.setText(item.getChange().toString());
        vh.storkVolumeTV.setText(item.getVolume());

        if (item.getChange()<0)
             vh.changeImg.setImageResource(R.drawable.negative);
        else if (item.getChange()==0)
            vh.changeImg.setImageResource(R.drawable.no_change);
        else
            vh.changeImg.setImageResource(R.drawable.positve);


//         holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StorksViewHolder extends RecyclerView.ViewHolder {

        public TextView storkNameTV;
        public TextView storkPriceTV;
        public TextView storkVolumeTV;
        public TextView storkChangeValueTV;
        public ImageView changeImg;
        public StorksViewHolder(View itemView) {
            super(itemView);

            storkNameTV = (TextView) itemView.findViewById(R.id.stork_name_textview);
            storkPriceTV = (TextView) itemView.findViewById(R.id.stork_price_textview);
            storkVolumeTV = (TextView) itemView.findViewById(R.id.stork_volume_textview);
            storkChangeValueTV = (TextView) itemView.findViewById(R.id.change_value_textview);
            changeImg = (ImageView) itemView.findViewById(R.id.stork_change_imageview);




    }

//
//        public void bind(Stork stork) {
//            storkNameTV.setText(stork.getName());
//            storkNameTV.setText(stork.getName());
//            storkNameTV.setText(stork.getName());
//            storkNameTV.setText(stork.getName());
//
//        }
    }
}
