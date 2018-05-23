package com.example.phiiphiroberts.uearn;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.RecylerViewHolder> {

    private ArrayList<RecylerItem> mRecylerList;

    public static class RecylerViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView;

        public RecylerViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.recylerImageView);
            mTextView = itemView.findViewById(R.id.recylerText);
        }
    }

    public RecylerAdapter(ArrayList<RecylerItem> recyclerlist){
        mRecylerList = recyclerlist;
    }

    @NonNull
    @Override
    public RecylerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
       RecylerViewHolder rvh = new RecylerViewHolder(v);
       return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewHolder holder, int position) {
        RecylerItem currentItem = mRecylerList.get(position);

        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView.setText(currentItem.getmText1());

    }

    @Override
    public int getItemCount() {
        return mRecylerList.size();
    }
}
