package com.example.teacherhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterStudentActivity extends AppCompatActivity {

    Spinner spinner;
    EditText mFirstName, mLastName, mContact;
    Button mSaveBtn;
    ProgressDialog pd;
    FirebaseFirestore db;


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
        setContentView(R.layout.activity_register_student);

        mFirstName = findViewById(R.id.first_name);
        mLastName = findViewById(R.id.last_name);
        mContact = findViewById(R.id.contact);
        mSaveBtn = findViewById(R.id.buttonSAVE);



        sections = new ArrayList<>();
        sections.add("S1 MORNING CLASS");
        sections.add("S2 MID CLASS");
        sections.add("S3 NOON CLASS");

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sections);
        spinner.setAdapter(adapter);

        pd = new ProgressDialog(this);
        db = FirebaseFirestore.getInstance();
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = mFirstName.getText().toString().trim();
                String lastName = mLastName.getText().toString().trim();
                String contact = mContact.getText().toString().trim();
                String section = spinner.getSelectedItem().toString();


                uploadData(firstName, lastName, contact, section);
            }
        });
    }

    private void uploadData(String firstName, String lastName, String contact, String section) {

        pd.setTitle("Adding data");
        pd.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id",id);
        doc.put("firstName", firstName);
        doc.put("lastName", lastName);
        doc.put("contact", contact);
        doc.put("section", section);

        db.collection("Students").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(RegisterStudentActivity.this,"Added..",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(RegisterStudentActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });
    }


}
