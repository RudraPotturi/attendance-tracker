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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class CheckActivity extends AppCompatActivity {
    List<AttendanceModel> modelList = new ArrayList<>();
    Button cancelBtn, loadButton;
    Spinner spinner;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    Map doc;
    private int year, month, day;
    String date = "";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    CheckBox cb ;

    FirebaseFirestore db;
    AttendanceAdapter adapter;
    ProgressDialog pd;
    public static ArrayList<String> sections;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        getSupportActionBar().setTitle("View Attendance");
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





        spinner = (Spinner) findViewById(R.id.viewSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sections);
        spinner.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        loadButton = findViewById(R.id.loadButton);
        cancelBtn = findViewById(R.id.buttonSaveAttendance);
        cb = findViewById(R.id.attMarker);



        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView tv=findViewById(R.id.viewtv);

                DocumentReference docRef = db.collection("Attendance").document(spinner.getSelectedItem().toString()+"@"+date);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("", "DocumentSnapshot data: " + document.getData().values());
                                String temp="";
                                for(int i=0;i<(document.getData().values().toArray().length);i++){
                                    for(Object obj:(ArrayList)document.getData().values().toArray()[i]){
                                        temp=temp+obj.toString()+"\n";
                                    }
                                }
                                tv.setText(temp);
                            } else {
                                Log.d("", "No such document");
                            }
                        } else {
                            Log.d("", "get failed with ", task.getException());
                        }
                    }
                });

//
//                List<AttendanceModel> a=AttendanceAdapter.checkedmodelList;
//                String saveval="";
//                for(AttendanceModel ab:a){
//                    AttendanceModel demo=ab;
//                    saveval+=demo.toString();
//                }

//                tv.setText(doc.toString());
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });



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
