package com.example.teacherhelper;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceViewHolder extends RecyclerView.ViewHolder {

    TextView mTitleTv;
    View mView;
    public AttendanceViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
                return true;
            }
        });

        mTitleTv = itemView.findViewById(R.id.rTitleTv);

    }
    private AttendanceViewHolder.ClickListener mClickListener;
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(AttendanceViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}

