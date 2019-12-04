package com.example.teacherhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    List<AttendanceModel> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;

    Spinner spinner;
    Button loadButton, buttonSaveAttendance;

    FirebaseFirestore db;
    AttendanceAdapter adapter;
    ProgressDialog pd;

    public static ArrayList<String> sections;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        sections = new ArrayList<>();
        sections.add("S1 MORNING CLASS");
        sections.add("S2 MID CLASS");
        sections.add("S3 NOON CLASS");

        spinner = (Spinner) findViewById(R.id.attendanceSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sections);
        spinner.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.recycler_view);
        loadButton = findViewById(R.id.loadButton);
        buttonSaveAttendance = findViewById(R.id.buttonSaveAttendance);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);


        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData();
            }
        });


        buttonSaveAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            pd.setTitle("Saving Data");
            pd.show();
            Toast.makeText(getApplicationContext(), "Attendance Marked", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

            }
        });


    }

    private void showData() {
        pd.setTitle("Loading Data...");
        pd.show();
        db.collection("Students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                modelList.clear();
                pd.dismiss();
                for (DocumentSnapshot doc: task.getResult()){
                    AttendanceModel model = new AttendanceModel(doc.getString("id"), doc.getString("firstName"));
                    modelList.add(model);
                }

                adapter = new AttendanceAdapter(AttendanceActivity.this, modelList);
                mRecyclerView.setAdapter(adapter);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AttendanceActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
