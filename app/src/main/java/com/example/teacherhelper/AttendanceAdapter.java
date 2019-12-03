package com.example.teacherhelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceViewHolder> {

    AttendanceActivity listActivity;
    List<AttendanceModel> modelList;
    Context context;

    public AttendanceAdapter(AttendanceActivity listActivity, List<AttendanceModel> modelList) {
        this.listActivity = listActivity;
        this.modelList = modelList;

    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model_attendance, viewGroup, false);

        AttendanceViewHolder viewHolder = new AttendanceViewHolder(itemView);
        viewHolder.setOnClickListener(new AttendanceViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String firstName = modelList.get(position).getFirstName();

                Toast.makeText(listActivity, firstName, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(View view, final int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(listActivity);
                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which == 0){
                            String id = modelList.get(position).getId();
                            String title = modelList.get(position).getFirstName();


                            Intent intent = new Intent(listActivity, RegisterStudentActivity.class);
                            intent.putExtra("pId", id);
                            intent.putExtra("pTitle", title);


                            listActivity.startActivity(intent);
                        }

                    }
                }).create().show();

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder viewHolder, int i) {
        viewHolder.mTitleTv.setText(modelList.get(i).getFirstName());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


}
