package com.example.teacherhelper;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    TextView mTitleTv;
    CheckBox checkBox;
    View mView;
    ItemClickListener itemClickListener;
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
        checkBox = itemView.findViewById(R.id.attMarker);
        checkBox.setOnClickListener(this);

    }
    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener =ic;
    }
    private AttendanceViewHolder.ClickListener mClickListener;

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view,getLayoutPosition());

    }

    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(AttendanceViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View v, int pos);
    }
}

