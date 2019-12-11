package com.example.teacherhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {

    List<AttendanceModel> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    String date = "";

    Spinner spinner;
    Button loadButton, buttonSaveAttendance;

    FirebaseFirestore db;
    AttendanceAdapter adapter;
    ProgressDialog pd;
    CheckBox checkBox;

    static String name;

    public static ArrayList<String> sections;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.attendance_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.viewAttendance:
                finish();
                startActivity(new Intent(this, CheckActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getSupportActionBar().setTitle("Attendance");

        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        sections = new ArrayList<>();
        sections.add("S1 MORNING CLASS");
        sections.add("S2 MID CLASS");
        sections.add("S3 NOON CLASS");
        name="";

        //check box
        checkBox = findViewById(R.id.attMarker);

        spinner = (Spinner) findViewById(R.id.viewSpinner);
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

                ArrayList<String> dataAtt = new ArrayList<>();
                dataAtt.clear();
                for(AttendanceModel att: AttendanceAdapter.checkedmodelList){
                    dataAtt.add(att.firstName);
                }
                Map<String, Object> attendanceData = new HashMap<>();
                attendanceData.put("data", dataAtt);

                db.collection("Attendance").document(spinner.getSelectedItem().toString()+"@"+date)
                        .set(attendanceData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Done saving attendance.",Toast.LENGTH_LONG*3).show();
                                Log.d("", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("", "Error writing document", e);
                            }
                        });
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
                    if(doc.getString("section").equals(spinner.getSelectedItem().toString())){
                         AttendanceModel model = new AttendanceModel(doc.getString("id"), doc.getString("firstName"));

                        modelList.add(model);

                    }
                }

                adapter = new AttendanceAdapter(AttendanceActivity.this, modelList,spinner.getSelectedItem().toString());
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

    public String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();
        return (dtf.format(localDate)); //2016/11/16
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(year).append("-")
               .append(month).append("-").append(day));
        date = (new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day)).toString();
    }
}
